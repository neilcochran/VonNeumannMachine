package com.neilcochran.components;

import com.neilcochran.components.registers.Register;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Central Processing Unit (CPU) which extends the Thread class in order to be able to run in a non-blocking manner
 */
@Getter
@Setter //Don't use @Data since we want to call super() ourselves with a specific value (the thread name)
public class CPU extends Thread {

    private final ControlUnit controlUnit;
    private final ALU alu;
    private final Register r0;
    private final Register r1;
    private final Register r2;
    private final Register r3;
    private boolean halted = true;

    /**
     * Creates a new CPU
     * @param controlUnit A reference to the Control Unit
     * @param alu A reference to the Arithmetic Logic Unit
     * @param r0 A reference to the zeroth Register
     * @param r1 A reference to the first Register
     * @param r2 A reference to the second Register
     * @param r3 A reference to the third Register
     */
    public CPU(
            ControlUnit controlUnit,
            ALU alu,
            Register r0,
            Register r1,
            Register r2,
            Register r3
    ) {
        super("CPU_Thread");
        this.controlUnit = controlUnit;
        this.alu = alu;
        this.r0 = r0;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
    }

    /**
     * Start the Fetch -> Decode -> Execute loop in a new thread
     */
    @Override
    public void run() {
        this.halted = false;
        //Continue running the Fetch -> Decode -> Execute loop until the HALT command is executed
        while(!this.halted) {
            //Get the instruction at the address the PC has. To do this, move the value from the PC to the MAR
            this.controlUnit.memoryAddressRegister().setData(this.controlUnit.programCounter().getData());
            //Get the instruction located at the address currently in the MAR and set it in the CIR
            this.controlUnit.instructionRegister().setData(this.controlUnit.memoryDataRegister().loadDataFromMemory());
            // PC incremented (note PC already points to next instruction before we've executed the current one)
            this.controlUnit.programCounter().increment();
            //CU takes over to decode the current instruction and delegate/execute the instruction
            this.controlUnit.executeCurrentInstruction();
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
