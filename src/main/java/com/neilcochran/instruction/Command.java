package com.neilcochran.instruction;

import com.neilcochran.component.register.RegisterFile;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents Instruction's abstract Command and a method to execute it
 */
@Data
@AllArgsConstructor
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
     * Execute the instruction's command
     */
    public abstract void executeCommand();
}
