package com.neilcochran.components.registers;

/**
 * Represents a Program Counter Register
 */
public class ProgramCounter extends Register {

    /**
     * Creates a new Program Counter Register (PC)
     */
    public ProgramCounter() {
        super("PC");
    }

    /**
     * Increments the current PC's value by 1
     */
    public void increment() {
        this.data += 0b1;
    }
}
