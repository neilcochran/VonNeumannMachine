package com.neilcochran.component;

import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Instruction;
import com.neilcochran.util.DataSize;
import lombok.Getter;
import lombok.Setter;

import static com.neilcochran.component.ControlUnit.decodeInstruction;

/**
 * Represents the Central Processing Unit (CPU) which extends the Thread class in order to be able to run in a non-blocking manner
 */
@Getter
@Setter //Don't use @Data since we want to call super() ourselves with a specific value (the thread name)
public class CPU extends Thread {

    private final RegisterFile registerFile;
    private final ControlUnit controlUnit;
    private final ALU alu;
    private final RAM ram;
    private boolean halted = true;

    /**
     * Creates a new CPU
     * @param registerFile A reference to the registers
     * @param controlUnit A reference to the Control Unit
     * @param alu A reference to the Arithmetic Logic Unit
     */
    public CPU(
            RegisterFile registerFile,
            ControlUnit controlUnit,
            ALU alu,
            RAM ram
    ) {
        super("CPU_Thread");
        this.registerFile = registerFile;
        this.controlUnit = controlUnit;
        this.alu = alu;
        this.ram = ram;
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
            //TODO disallow direct memory access and make PC load it's value into a Memory Address Register (MAR) and then have the actual instruction loaded into Memory Data Register (MDR)?
            var rawInstruction = ram.loadData(registerFile.getPCRegister().getData(), DataSize.WORD);
            registerFile.incrementProgramCounter();
            Instruction instruction = decodeInstruction(rawInstruction);
            controlUnit.executeInstruction(instruction);
            if(registerFile.getPCRegister().getData() >= ram.getTotalBytes()) {
                this.halted = true;
                throw new IndexOutOfBoundsException(String.format("The Program Counter has reached the end of memory: %d", registerFile.getPCRegister().getData()));
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
