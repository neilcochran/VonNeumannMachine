package com.neilcochran.instruction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Command {
    protected Instruction instruction;
    public abstract void executeCommand();
}
