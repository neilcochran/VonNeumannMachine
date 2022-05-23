package com.neilcochran.component;

import com.neilcochran.component.register.Registers;
import com.neilcochran.instruction.Instruction;
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
    public void executeCurrentInstruction() {}

    /**
     * Decode the current instruction and return a full Instruction object
     * @param instruction The input instruction
     * @return An Instruction object representing the decoded instruction
     * @throws IllegalArgumentException if the instruction is invalid
     */
    public static Instruction decodeInstruction(int instruction) {
        if(!BitUtils.validateBitLength(instruction, VonNeumannMachine.WORD_SIZE)) {
            throw new IllegalArgumentException("Invalid instruction bit length: " + BitUtils.getBitLength(instruction));
        }
        //TODO impl
        return null;
    }
}
