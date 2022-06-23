package com.neilcochran.instruction.formatGroup.B.command;

import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.formatGroup.B.InstructionB;

public class BRC extends Command {
    private final RegisterFile registerFile;

    public BRC(InstructionB instructionB, RegisterFile registerFile) {
        super(instructionB);
        this.registerFile = registerFile;
    }
    @Override
    public void executeCommand() {
        InstructionB instructionB = (InstructionB) instruction;
        //L bit indicates if we should store PC+4 (already incremented in LR
        if(instructionB.getLinkRegisterFlagBit() == 1) {
            registerFile.getLinkRegister().setData(registerFile.getPCRegister().getData());
        }
        //bit shift right 2 (so lower 2 LSBs always 0s) and add to PC
        var imm26 = (instructionB.getImm24() << 2);
        registerFile.getPCRegister().setData(imm26 + registerFile.getPCRegister().getData());
    }
}
