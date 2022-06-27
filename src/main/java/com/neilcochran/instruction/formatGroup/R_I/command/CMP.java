package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.util.CommandUtil;

public class CMP extends Command {

    private final RegisterFile registerFile;

    public CMP(OpCodeInstruction instruction, RegisterFile registerFile) {
        super(instruction);
        this.registerFile = registerFile;
    }

    @Override
    public void executeCommand() {
        OpCodeInstruction cmpInstruction = (OpCodeInstruction) instruction;
        int operand1 = registerFile.getRegister(cmpInstruction.getRN()).getData();
        int operand2 = CommandUtil.getOpCodeInstructionOperand2(cmpInstruction, registerFile);
        //perform addition so PSR flags are set, but discard the result
        ALU.add(operand1, operand2, registerFile.getPSR());
    }
}