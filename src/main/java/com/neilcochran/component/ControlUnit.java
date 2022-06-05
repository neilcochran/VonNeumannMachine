package com.neilcochran.component;

import com.neilcochran.component.register.Registers;
import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.OpCodeInstructions;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.formatGroup.B.InstructionB;
import com.neilcochran.instruction.formatGroup.InstructionD;
import com.neilcochran.instruction.formatGroup.InstructionI;
import com.neilcochran.instruction.formatGroup.InstructionR;
import com.neilcochran.instruction.formatGroup.InstructionX;
import com.neilcochran.util.BitUtils;

/**
 * Represents a ControlUnit which orchestrates the decoding and execution steps
 * @param registers A reference to the registers
 * @param alu A reference to the Arithmetic Logic Unit

 */
public record ControlUnit(Registers registers, ALU alu) {

    /**
     * Execute the current instruction, which is located in the instruction register
     */
    public void executeInstruction(Instruction instruction) {
        switch (instruction.getInstructionFormat()) {
            case R -> executeInstructionR((InstructionR) instruction);
            case I -> executeInstructionI((InstructionI) instruction);
            case D -> executeInstructionD((InstructionD) instruction);
            case X -> executeInstructionX((InstructionX) instruction);
            case B -> executeInstructionB((InstructionB) instruction);
        }
    }

    /**
     * Decode the current instruction into the correct Instruction type.
     * @param instruction The input instruction
     * @return An Instruction object representing the decoded instruction
     * @throws IllegalArgumentException if the instruction is invalid
     */
    public static Instruction decodeInstruction(long instruction) {
        if(!BitUtils.validateBitLength(instruction, VonNeumannMachine.WORD_SIZE)) {
            throw new IllegalArgumentException("Invalid instruction bit length: " + BitUtils.getBitLength(instruction));
        }
        var instructionFormat = InstructionFormat.fromFormatBits(BitUtils.getBitRange(instruction, Instruction.FORMAT_RANGE));
        return switch(instructionFormat) {
            case R -> (InstructionR) decodeInstructionOpCode(new InstructionR(instruction));
            case I -> (InstructionI) decodeInstructionOpCode(new InstructionI(instruction));
            case D -> new InstructionD(instruction);
            case X -> new InstructionX(instruction);
            case B -> new InstructionB(instruction);
        };
    }

    public static OpCodeInstructions decodeInstructionOpCode(OpCodeInstructions opCodeInstructions) {
        opCodeInstructions.getOpCode().setName(getOpCodeName(opCodeInstructions.getOpCode().getBits()));
        return opCodeInstructions;
    }

    private static String getOpCodeName(int opCode) {
        return switch(opCode) {
            case 0b0000 -> "AND";
            case 0b0001 -> "EOR";
            case 0b0010 -> "SUB";
            case 0b0011 -> "RSB";
            case 0b0100 -> "ADD";
            case 0b0101 -> "ADC";
            case 0b0110 -> "SBC";
            case 0b0111 -> "RSC";
            case 0b1000 -> "TST";
            case 0b1001 -> "TEQ";
            case 0b1010 -> "CMP";
            case 0b1011 -> "CMN";
            case 0b1100 -> "ORR";
            case 0b1101 -> "MOV";
            case 0b1110 -> "BIC";
            case 0b1111 -> "MVN";
            default -> throw new IllegalArgumentException("Invalid OpCode: " + Integer.toBinaryString(opCode));
        };
    }

    private void executeInstructionR(InstructionR instructionR) {
        //TODO
        System.out.println("executing R: " + instructionR);
    }

    private void executeInstructionI(InstructionI instructionI) {
        //TODO
        System.out.println("executing I: " + instructionI);
    }

    private void executeInstructionD(InstructionD instructionD) {
        //TODO
        System.out.println("executing D: " + instructionD);
    }

    private void executeInstructionX(InstructionX instructionX) {
        //TODO
        System.out.println("executing X: " + instructionX);
    }

    //only 1 B format branch (there is an R type branch encoded as TEQ w State=1
    private void executeInstructionB(InstructionB instructionB) {
        System.out.println("executing B: " + instructionB);
        //L bit indicates if we should store PC+4 (already incremented in LR
        if(instructionB.getLinkRegisterFlagBit() == 1) {
            registers.getLinkRegister().setData(registers.getPCRegister().getData());
        }
        //bit shift right 2 (so lower 2 LSBs always 0s) and add to PC
        var imm26 = (instructionB.getImm24() << 2);
        registers.getPCRegister().setData(imm26 + registers.getPCRegister().getData());
    }
}
