package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.util.CommandUtil;

public class CMN extends Command {

    private final RegisterFile registerFile;
    private final ProgramStatusRegister PSR;

    public CMN(OpCodeInstruction instruction, RegisterFile registerFile, ProgramStatusRegister PSR) {
        super(instruction);
        this.registerFile = registerFile;
        this.PSR = PSR;
    }

    @Override
    public void executeCommand() {
        OpCodeInstruction cmnInstruction = (OpCodeInstruction) instruction;
        int operand1 = registerFile.getRegister(cmnInstruction.getRN()).getData();
        int operand2 = CommandUtil.getOpCodeInstructionOperand2(cmnInstruction, registerFile);
        //perform addition so PSR flags are set, but discard the result
        ALU.add(operand1, operand2, PSR);
    }
}
