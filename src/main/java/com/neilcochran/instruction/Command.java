package com.neilcochran.instruction;

import com.neilcochran.component.register.RegisterFile;

/**
 * Represents Instruction's abstract Command and a method to execute it
 */
public abstract class Command {

    /**
     * The instruction for the Command
     */
    protected Instruction instruction;

    /**
     * A reference to the machine's RegisterFile
     */
    protected RegisterFile registerFile;

    /**
     * Constructs an executable Command for the given instruction
     * @param instruction The backing instruction
     * @param registerFile A reference to the machine's RegisterFile
     */
    public Command(Instruction instruction, RegisterFile registerFile) {
        this.instruction = instruction;
        this.registerFile = registerFile;
    }

    /**
     * Execute the instruction's command
     */
    public abstract void executeCommand();
}
