package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.Register;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.util.CommandUtil;

public class MOV extends Command {

    private final RegisterFile registerFile;
    private final ProgramStatusRegister PSR;

    public MOV(OpCodeInstruction instruction, RegisterFile registerFile, ProgramStatusRegister PSR) {
        super(instruction);
        this.registerFile = registerFile;
        this.PSR = PSR;
    }

    @Override
    public void executeCommand() {
        OpCodeInstruction movInstruction = (OpCodeInstruction) instruction;
        //TODO enforce register Rd restrictions (mostly around PC/LR)
        Register RD = registerFile.getRegister(movInstruction.getRD());
        int operand2 = CommandUtil.getOpCodeInstructionOperand2(movInstruction, registerFile);
        RD.setData(operand2);
        //if S flag set, update N & Z flags (C flag may have been set during barrel shift)
        if(movInstruction.getStateFlagBit() == 1) {
            ALU.setNZFlags(operand2, PSR);
        }
    }
}
