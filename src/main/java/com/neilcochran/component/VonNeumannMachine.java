package com.neilcochran.component;

import com.neilcochran.component.register.Registers;
import com.neilcochran.util.DataSize;
import lombok.Data;

/**
 * Represents a simple 32 bit Von Neumann Machine with 16 registers
 */
@Data
public class VonNeumannMachine {

    /**
     * The word size (bits) of the machine
     */
    public static final int WORD_SIZE = DataSize.WORD.getBitLength();

    /**
     * The default amount (bytes) of RAM provided for the machine to use
     */
    private static final int DEFAULT_RAM_BYTES = 65536;

    private final Registers registers;
    private final ControlUnit controlUnit;
    private final ALU alu;
    private final CPU cpu;
    private final RAM ram;

    /**
     * Creates a 32 bit Von Neumann Machine with 65536 bytes (64k) of RAM and 16 registers
     */
    public VonNeumannMachine() {
        ram = new RAM(DEFAULT_RAM_BYTES);
        registers = new Registers();
        alu = new ALU();
        controlUnit = new ControlUnit(registers, alu);
        cpu = new CPU(registers, controlUnit, alu, ram);
    }


    /**w
     * Starts the CPU's Fetch -> Decode -> Execute loop thread
     */
    public void run() {
        //Start the main execution CPU thread
        this.cpu.start();
    }

    /**
     * Sends the Halt signal to the CPU
     * @see com.neilcochran.component.CPU#halt()
     */
    public void halt() {
        this.cpu.halt();
    }

    public void loadProgram(int[] programData) {
        ram.loadProgramData(programData);
    }
}
