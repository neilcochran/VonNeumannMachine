package com.neilcochran.component;

import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.util.DataSize;

/**
 * Represents a simple 32 bit Von Neumann Machine with 16 registers
 */
public class VonNeumannMachine {

    /**
     * The word size (bits) of the machine
     */
    public static final int WORD_SIZE = DataSize.WORD.getBitLength();

    /**
     * The default amount (bytes) of memory provided for the machine to use
     */
    private static final int DEFAULT_RAM_BYTES = 65536;

    private final RegisterFile registerFile;
    private final ControlUnit controlUnit;
    private final ALU alu;
    private final CPU cpu;
    private final Memory memory;

    /**
     * Creates a 32 bit Von Neumann Machine with 65536 bytes (64k) of memory and 16 registers
     */
    public VonNeumannMachine() {
        memory = new Memory(DEFAULT_RAM_BYTES);
        registerFile = new RegisterFile();
        var PSR = new ProgramStatusRegister();
        alu = new ALU(registerFile, PSR);
        controlUnit = new ControlUnit(registerFile, alu, PSR, memory);
        cpu = new CPU(registerFile, controlUnit);
    }


    /**
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

    /**
     * Load an array of program data into memory
     * @param programData The array of program data to be loaded into memory
     * @see com.neilcochran.component.Memory#loadProgramData(int[])
     */
    public void loadProgram(int[] programData) {
        memory.loadProgramData(programData);
    }
}
