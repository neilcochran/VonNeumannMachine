package com.neilcochran.instruction;

import com.neilcochran.component.register.RegisterFile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Command {

    protected Instruction instruction;
    protected RegisterFile registerFile;

    public abstract void executeCommand();
}
