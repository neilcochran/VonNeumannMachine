package com.neilcochran.component;

import com.neilcochran.component.register.MemoryDataRegister;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.Register;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.field.Condition;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.formatGroup.B.InstructionB;
import com.neilcochran.instruction.formatGroup.B.command.BR;
import com.neilcochran.instruction.formatGroup.D_X.InstructionD;
import com.neilcochran.instruction.formatGroup.D_X.InstructionX;
import com.neilcochran.instruction.formatGroup.R_I.InstructionI;
import com.neilcochran.instruction.formatGroup.R_I.InstructionR;
import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;

public class ControlUnit {

    private final Register memoryAddressRegister;
    private final MemoryDataRegister memoryDataRegister;
    private final Register instructionRegister;
    private final RegisterFile registerFile;
    private final ALU alu;
    private final ProgramStatusRegister PSR;

    public ControlUnit(RegisterFile registerFile, ALU alu, ProgramStatusRegister PSR, Memory memory) {
        this.registerFile = registerFile;
        this.alu = alu;
        this.PSR = PSR;
        memoryAddressRegister = new Register("MAR", "MAR");
        memoryDataRegister = new MemoryDataRegister(memoryAddressRegister, memory);
        instructionRegister = new Register("IR", "IR");
    }

    public void loadInstructionRegister() {
        //PC -> MAR
        memoryAddressRegister.setData(registerFile.getPCRegister().getData());
        //MAR -> data -> MDR
        memoryDataRegister.loadData(DataSize.WORD);
        //MDR -> IR
        instructionRegister.setData(memoryDataRegister.getData());
    }

    //Determine if CU or ALU will execute the instruction
    public void executeInstruction() {
        var instruction = instructionRegister.getData();
        var instructionFormat = InstructionFormat.fromFormatBits(BitUtils.getBitRange(instruction, Instruction.FORMAT_RANGE));
        switch(instructionFormat) {
            case R -> {
                var instructionR = new InstructionR(instruction);
                if(evaluateInstructionCondition(instructionR.getCondition())){
                    alu.executeInstruction(instructionR);
                }
            }
            case I -> {
                var instructionI = new InstructionI(instruction);
                if(evaluateInstructionCondition(instructionI.getCondition())){
                    alu.executeInstruction(instructionI);
                }
            }
            case D -> {
                var instructionD = new InstructionD(instruction);
                if(evaluateInstructionCondition(instructionD.getCondition())) {
                    executeInstruction(instructionD);
                }
            }
            case X -> {
                var instructionX = new InstructionX(instruction);
                if(evaluateInstructionCondition(instructionX.getCondition())) {
                    executeInstruction(instructionX);
                }
            }
            case B -> {
                var instructionB = new InstructionB(instruction);
                if(evaluateInstructionCondition(instructionB.getCondition())) {
                    executeInstruction(instructionB);
                }
            }
        }
    }

    private boolean evaluateInstructionCondition(Condition condition) {
        return switch (condition) {
            case EQ -> PSR.getZBit() == 1;
            case NE -> PSR.getZBit() == 0;
            case CS -> PSR.getCBit() == 1;
            case CC -> PSR.getCBit() == 0;
            case MI -> PSR.getNBit() == 1;
            case PL -> PSR.getNBit() == 0;
            case VS -> PSR.getVBit() == 1;
            case VC -> PSR.getVBit() == 0;
            case HI -> (PSR.getCBit() == 1) && (PSR.getZBit() == 0);
            case LS -> (PSR.getCBit() == 0) || (PSR.getZBit() == 1);
            case GE -> PSR.getNBit() == PSR.getVBit();
            case LT -> PSR.getNBit() != PSR.getVBit();
            case GT -> (PSR.getZBit() == 0) && (PSR.getNBit() == PSR.getVBit());
            case LE -> (PSR.getZBit() == 1) || (PSR.getNBit() != PSR.getVBit());
            case AL -> true;
        };
    }

    private void executeInstruction(LoadStoreInstruction instruction) {
        //todo get command and execute in CU (here)
    }

    private void executeInstruction(InstructionB instruction) {
        BR br = new BR(instruction, registerFile);
        br.executeCommand();
    }

    public Register getMemoryAddressRegister() {
        return memoryAddressRegister;
    }

    public MemoryDataRegister getMemoryDataRegister() {
        return memoryDataRegister;
    }

    public Register getInstructionRegister() {
        return instructionRegister;
    }
}
