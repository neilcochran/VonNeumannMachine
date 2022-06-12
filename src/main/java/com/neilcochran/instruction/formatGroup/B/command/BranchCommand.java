package com.neilcochran.instruction.formatGroup.B.command;

import com.neilcochran.component.register.Registers;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.formatGroup.B.InstructionB;

public class BranchCommand extends Command {
    private final Registers registers;

    public BranchCommand(InstructionB instructionB, Registers registers) {
        super(instructionB);
        this.registers = registers;
    }
    @Override
    public void executeCommand() {
        InstructionB instructionB = (InstructionB) instruction;
        //L bit indicates if we should store PC+4 (already incremented in LR
        if(instructionB.getLinkRegisterFlagBit() == 1) {
            registers.getLinkRegister().setData(registers.getPCRegister().getData());
        }
        //bit shift right 2 (so lower 2 LSBs always 0s) and add to PC
        var imm26 = (instructionB.getImm24() << 2);
        registers.getPCRegister().setData(imm26 + registers.getPCRegister().getData());
    }
}
