package com.neilcochran.components;

import com.neilcochran.components.registers.*;
import com.neilcochran.instruction.Instruction;

/**
 * Represents a ControlUnit which orchestrates the decoding and execution steps
 * @param programCounter A reference to the Program Counter Register
 * @param instructionRegister A reference to the Instruction Register
 * @param memoryAddressRegister A reference to the Memory Address Register
 * @param memoryDataRegister A reference to the Memory Data Register
 * @param alu A reference to the Arithmetic Logic Unit
 * @param r0 A reference to the zeroth general purpose Register
 * @param r1 A reference to the first general purpose Register
 * @param r2 A reference to the second general purpose Register
 * @param r3 A reference to the third general purpose Register
 */
public record ControlUnit(
        ProgramCounter programCounter,
        InstructionRegister instructionRegister,
        MemoryAddressRegister memoryAddressRegister,
        MemoryDataRegister memoryDataRegister,
        ALU alu,
        Register r0,
        Register r1,
        Register r2,
        Register r3
) {
    /**
     * Execute the current instruction, which is located in the instruction register
     */
    public void executeCurrentInstruction() {
        //get the current instruction from the current instruction register
        var instruction = new Instruction(instructionRegister.getData());
    }
}
