package com.neilcochran.component;

import com.neilcochran.component.register.MemoryDataRegister;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.Register;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.field.Condition;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.formatGroup.B.InstructionB;
import com.neilcochran.instruction.formatGroup.B.command.BRC;
import com.neilcochran.instruction.formatGroup.D.InstructionD;
import com.neilcochran.instruction.formatGroup.R_I.InstructionI;
import com.neilcochran.instruction.formatGroup.R_I.InstructionR;
import com.neilcochran.instruction.formatGroup.X.InstructionX;
import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;

public class ControlUnit {

    public final Register memoryAddressRegister;
    public final MemoryDataRegister memoryDataRegister;
    public final Register instructionRegister;
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

    /**
     * Decode the current instruction into the correct Instruction type.
     * @param instruction The input instruction
     * @return An Instruction object representing the decoded instruction
     * @throws IllegalArgumentException if the instruction is invalid
     */
    public static Instruction decodeInstruction(int instruction) {
        if(!BitUtils.validateBitLength(instruction, VonNeumannMachine.WORD_SIZE)) {
            throw new IllegalArgumentException("Invalid instruction bit length: " + BitUtils.getBitLength(instruction));
        }
        var instructionFormat = InstructionFormat.fromFormatBits(BitUtils.getBitRange(instruction, Instruction.FORMAT_RANGE));
        return switch(instructionFormat) {
            case R -> new InstructionR(instruction);
            case I -> new InstructionI(instruction);
            case D -> new InstructionD(instruction);
            case X -> new InstructionX(instruction);
            case B -> new InstructionB(instruction);
        };
    }

    //Determine if CU or ALU will execute the instruction
    public void executeInstruction() {
        //decode the instruction
        var instruction = decodeInstruction(instructionRegister.getData());
        //Check the instruction condition against the current PSR flags to determine if it should be executed or not
        if(evaluateCondition(instruction.getCondition(), PSR)) {
            switch(instruction.getInstructionFormat()) {
                case R, I -> alu.executeInstruction((OpCodeInstruction) instruction);
                case D, X -> executeInstruction((LoadStoreInstruction) instruction);
                case B -> executeInstruction((InstructionB) instruction);
            }
        }
    }

    private static boolean evaluateCondition(Condition condition, ProgramStatusRegister PSR) {
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
        BRC brc = new BRC(instruction, registerFile);
        brc.executeCommand();
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
