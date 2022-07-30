package com.neilcochran.component;

import com.neilcochran.component.register.MemoryDataRegister;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.Register;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.field.Condition;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.Shift;
import com.neilcochran.instruction.formatGroup.B.InstructionB;
import com.neilcochran.instruction.formatGroup.B.command.BR;
import com.neilcochran.instruction.formatGroup.D_X.InstructionD;
import com.neilcochran.instruction.formatGroup.D_X.InstructionX;
import com.neilcochran.instruction.formatGroup.D_X.command.LDR;
import com.neilcochran.instruction.formatGroup.D_X.command.STR;
import com.neilcochran.instruction.formatGroup.R_I.InstructionI;
import com.neilcochran.instruction.formatGroup.R_I.InstructionR;
import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;

/**
 * Represents the Control Unit which orchestrates the bulk of the fetch -> decode -> execute cycle
 */
public class ControlUnit {

    private final Register memoryAddressRegister;
    private final MemoryDataRegister memoryDataRegister;
    private final Register instructionRegister;
    private final RegisterFile registerFile;
    private final ALU alu;
    private final ProgramStatusRegister PSR;

    /**
     * Constructs a ControlUnit
     * @param registerFile A reference to the machine's RegisterFile
     * @param alu A reference to the machine's ALU
     * @param PSR A reference to the machine's PSR
     * @param memory A reference to the machine's Memory
     */
    public ControlUnit(RegisterFile registerFile, ALU alu, ProgramStatusRegister PSR, Memory memory) {
        this.registerFile = registerFile;
        this.alu = alu;
        this.PSR = PSR;
        memoryAddressRegister = new Register("MAR", "MAR");
        memoryDataRegister = new MemoryDataRegister(memoryAddressRegister, memory);
        instructionRegister = new Register("IR", "IR");
    }

    /**
     * Calculates the barrel shift for a given InstructionR's second operand
     * @param instructionR The instruction to perform the barrel shift on
     * @param registerFile A reference to the machine's RegisterFile
     * @return The calculated result of the barrel shift
     */
    public static int calculateBarrelShift(InstructionR instructionR, RegisterFile registerFile) {
        return calculateBarrelShift(instructionR.getRM(), instructionR.getShift(), registerFile);
    }

    /**
     * Calculates the barrel shift for a given InstructionX's second operand
     * @param instructionX The instruction to perform the barrel shift on
     * @param registerFile A reference to the machine's RegisterFile
     * @return The calculated result of the barrel shift
     */
    public static int calculateBarrelShift(InstructionX instructionX, RegisterFile registerFile) {
        return calculateBarrelShift(instructionX.getRM(), instructionX.getShift(), registerFile);
    }

    /**
     * Calculate a barrel shift on the value held in the register pointed to in RM
     * @param RM The register that holds the value to perform the barrel shift on
     * @param shift The shift to be performed
     * @param registerFile A reference to the machine's RegisterFile
     * @return The calculated result of performing the indicated shift on the value held in the register pointed to by RM
     */
    private static int calculateBarrelShift(int RM, Shift shift, RegisterFile registerFile) {
        //get the value held in the register pointed to by RM
        int regData = registerFile.getRegister(RM).getData();
        //TODO Can update the C flag during the shift calculation
        return switch (shift.getShiftType()) {
            case LOGICAL_LEFT -> regData << shift.getShiftAmountBits();
            case LOGICAL_RIGHT -> regData >> shift.getShiftAmountBits();
            case ARITHMETIC_RIGHT -> regData >>> shift.getShiftAmountBits();
            case ROTATE_RIGHT -> BitUtils.calculateRotate(shift.getShiftAmountBits(), regData);
        };
    }

    /**
     * Loads the next instruction into the instruction register.
     * First the value of the program counter is moved into the Memory Address Register (MAR).
     * Next, the Memory Data Register (MDR) fetches the data from the memory pointed to by the address held in the MAR
     */
    public void loadInstructionRegister() {
        //PC -> MAR
        memoryAddressRegister.setData(registerFile.getPCRegister().getData());
        //MAR -> data -> MDR
        memoryDataRegister.loadData(DataSize.WORD, false);
        //MDR -> IR
        instructionRegister.setData(memoryDataRegister.getData());
    }

    /**
     * Decode the instruction currently held in the instructionRegister. Mathematics and logical instructions will be passed to the ALU for execution,
     * while all other instructions will be executed here by the ControlUnit
     */
    public void decodeAndExecuteInstruction() {
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
                    decodeAndExecuteInstruction(instructionD);
                }
            }
            case X -> {
                var instructionX = new InstructionX(instruction);
                if(evaluateInstructionCondition(instructionX.getCondition())) {
                    decodeAndExecuteInstruction(instructionX);
                }
            }
            case B -> {
                var instructionB = new InstructionB(instruction);
                if(evaluateInstructionCondition(instructionB.getCondition())) {
                    decodeAndExecuteInstruction(instructionB);
                }
            }
        }
    }

    /**
     * Evaluate the given condition based on the current bit flag values held in the ProgramStatusRegister
     * @param condition The logical condition to be evaluated
     * @return The boolean result of evaluating the condition against the current bit flag values held in the ProgramStatusRegister
     */
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
            //TODO use bit boolean logic here (like & vs &&)
            case HI -> (PSR.getCBit() == 1) && (PSR.getZBit() == 0);
            case LS -> (PSR.getCBit() == 0) || (PSR.getZBit() == 1);
            case GE -> PSR.getNBit() == PSR.getVBit();
            case LT -> PSR.getNBit() != PSR.getVBit();
            case GT -> (PSR.getZBit() == 0) && (PSR.getNBit() == PSR.getVBit());
            case LE -> (PSR.getZBit() == 1) || (PSR.getNBit() != PSR.getVBit());
            case AL -> true;
        };
    }

    /**
     * Decode and execute the given LoadStoreInstruction
     * @param instruction The LoadStoreInstruction to be decoded and executed
     */
    private void decodeAndExecuteInstruction(LoadStoreInstruction instruction) {
        //check if loading (L==1) or storing (L==0)
        (BitUtils.bitToBool(instruction.getLoadStoreBit())
                ? new LDR(instruction, registerFile, this)
                : new STR(instruction, registerFile, this)
        ).executeCommand();
    }

    /**
     * Decode and execute the given InstructionB
     * @param instruction The InstructionB to be decoded and executed
     */
    private void decodeAndExecuteInstruction(InstructionB instruction) {
        new BR(instruction, registerFile).executeCommand();
    }

    /**
     * Returns a reference to the memoryAddressRegister
     * @return A reference to the memoryAddressRegister
     */
    public Register getMemoryAddressRegister() {
        return memoryAddressRegister;
    }

    /**
     * Returns a reference to the memoryDataRegister
     * @return A reference to the memoryDataRegister
     */
    public MemoryDataRegister getMemoryDataRegister() {
        return memoryDataRegister;
    }

    /**
     * Returns a reference to the instructionRegister
     * @return A reference to the instructionRegister
     */
    public Register getInstructionRegister() {
        return instructionRegister;
    }
}
