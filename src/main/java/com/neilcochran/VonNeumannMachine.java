package com.neilcochran;

import com.neilcochran.components.ALU;
import com.neilcochran.components.CPU;
import com.neilcochran.components.ControlUnit;
import com.neilcochran.components.RAM;
import com.neilcochran.components.registers.*;
import lombok.Data;

/**
 * Represents a simple 16 bit Von Neumann Machine with 4 general purpose registers
 */
@Data
public class VonNeumannMachine {
    /**
     * 16 bit word size
     */
    public static final int WORD_SIZE = 16;

    private final ProgramCounter programCounter;

    private final InstructionRegister instructionRegister;

    private final MemoryAddressRegister memoryAddressRegister;

    private final MemoryDataRegister memoryDataRegister;

    private final Accumulator accumulator;

    private final ControlUnit controlUnit;

    private final ALU alu;

    private final CPU cpu;

    private final RAM ram;

    //General purpose registers
    private final Register r0 = new Register("R0");
    private final Register r1 = new Register("R1");
    private final Register r2 = new Register("R2");
    private final Register r3 = new Register("R3");

    /**
     * Creates a 16 bit Von Neumann Machine with 65536 bytes (64k) of RAM and 4 general purpose registers
     */
    public VonNeumannMachine() {
        this.ram = new RAM(65536, WORD_SIZE);
        this.programCounter = new ProgramCounter();
        this.instructionRegister = new InstructionRegister();
        this.memoryAddressRegister = new MemoryAddressRegister();
        //The only path for reads/writes to/from RAM is via the MemoryDataRegister
        this.memoryDataRegister = new MemoryDataRegister(memoryAddressRegister, ram);
        this.accumulator = new Accumulator();
        this.alu = new ALU(accumulator);
        this.controlUnit = new ControlUnit(programCounter, instructionRegister, memoryAddressRegister, memoryDataRegister, alu, r0, r1, r2, r3);
        this.cpu = new CPU(controlUnit, alu, r0, r1, r2, r3);
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
     * @see com.neilcochran.components.CPU#halt()
     */
    public void stop() {
        this.cpu.halt();
    }
}
