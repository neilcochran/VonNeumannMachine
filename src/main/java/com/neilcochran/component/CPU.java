package com.neilcochran.component;

import com.neilcochran.component.register.Registers;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Central Processing Unit (CPU) which extends the Thread class in order to be able to run in a non-blocking manner
 */
@Getter
@Setter //Don't use @Data since we want to call super() ourselves with a specific value (the thread name)
public class CPU extends Thread {

    private final Registers registers;
    private final ControlUnit controlUnit;
    private final ALU alu;
    private boolean halted = true;

    /**
     * Creates a new CPU
     * @param registers A reference to the registers
     * @param controlUnit A reference to the Control Unit
     * @param alu A reference to the Arithmetic Logic Unit
     */
    public CPU(
            Registers registers,
            ControlUnit controlUnit,
            ALU alu
    ) {
        super("CPU_Thread");
        this.registers = registers;
        this.controlUnit = controlUnit;
        this.alu = alu;

    }

    /**
     * Start the Fetch -> Decode -> Execute loop in a new thread
     */
    @Override
    public void run() {
        this.halted = false;
        //Continue running the Fetch -> Decode -> Execute loop until the HALT command is executed
        while(!this.halted) {
            //TODO
        }
    }

    /**
     * Send a signal to the CPU to halt execution at the beginning of its next cycle. Once halted the thread
     * created by calling run() will finish.
     */
    public void halt() {
        this.halted = true;
    }
}
