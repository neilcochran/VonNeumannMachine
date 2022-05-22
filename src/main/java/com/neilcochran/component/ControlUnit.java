package com.neilcochran.component;

import com.neilcochran.component.register.Registers;

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
}
