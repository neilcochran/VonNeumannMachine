package com.neilcochran.component;

import com.neilcochran.component.register.RegisterFile;

/**
 * Represents the Central Processing Unit (CPU) which extends the Thread class in order to be able to run in a non-blocking manner
 */
public class CPU extends Thread {

    private final RegisterFile registerFile;
    private final ControlUnit controlUnit;
    private boolean halted = true;

    /**
     * Constructs a new CPU (Central Processing Unit) to be run in a new thread
     * @param registerFile A reference to the machine's RegisterFile
     * @param controlUnit A reference to the machine's ControlUnit
     */
    public CPU(
            RegisterFile registerFile,
            ControlUnit controlUnit
    ) {
        super("CPU_Thread");
        this.registerFile = registerFile;
        this.controlUnit = controlUnit;
    }

    /**
     * Start the Fetch -> Decode -> Execute loop in a new thread
     * @throws IndexOutOfBoundsException if the Program Counter reaches the end of memory
     */
    @Override
    public void run() {
        this.halted = false;
        //Continue running the Fetch -> Decode -> Execute loop until the HALT command is executed
        while(!this.halted) {
            try {
                //fetch instruction
                controlUnit.loadInstructionRegister();
                //increment PC
                registerFile.incrementProgramCounter();
                //decode & execute instruction
                controlUnit.decodeAndExecuteInstruction();
            } catch(Exception ex) {
                this.halted = true;
                System.out.println("CPU Error: " + ex);
            }
        }
    }

    /**
     * Send a signal to the CPU to halt execution at the beginning of its next cycle. Once halted the CPU thread will finish.
     */
    public void halt() {
        this.halted = true;
    }
}
