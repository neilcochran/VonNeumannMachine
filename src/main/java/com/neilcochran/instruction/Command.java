package com.neilcochran.instruction;

import com.neilcochran.component.register.RegisterFile;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents `Instruction`'s abstract Command and a method to execute it
 */
@Data
@AllArgsConstructor
public abstract class Command {

    protected Instruction instruction;
    protected RegisterFile registerFile;

    /**
     * Execute the instruction's command
     */
    public abstract void executeCommand();
}
