package com.neilcochran.component;

import com.neilcochran.component.register.RegisterFile;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Central Processing Unit (CPU) which extends the Thread class in order to be able to run in a non-blocking manner
 */
@Getter
@Setter //Don't use @Data since we want to call super() ourselves with a specific value (the thread name)
public class CPU extends Thread {

    private final RegisterFile registerFile;
    private final ControlUnit controlUnit;
    private boolean halted = true;

    /**
     * Creates a new CPU
     * @param registerFile A reference to the registers
     * @param controlUnit A reference to the Control Unit
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
                controlUnit.executeInstruction();
            } catch(Exception ex) {
                this.halted = true;
                System.out.println("CPU Error: " + ex);
            }
        }
    }

    /**
     * Send a signal to the CPU to halt execution at the beginning of its next cycle. Once halted the CPU thread
     * created by calling run() will finish.
     */
    public void halt() {
        this.halted = true;
    }
}
