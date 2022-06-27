package com.neilcochran.util;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.formatGroup.R_I.InstructionI;
import com.neilcochran.instruction.formatGroup.R_I.InstructionR;

public class CommandUtil {

    public static int getOpCodeInstructionOperand2(OpCodeInstruction instruction, RegisterFile registerFile) {
        return switch (instruction.getInstructionFormat()) {
            case R -> ALU.calculateBarrelShift(((InstructionR) instruction), registerFile);
            case I ->  ((InstructionI) instruction).getRotateConstant().getResult();
            default -> throw new IllegalArgumentException("Invalid instruction format: " + instruction.getInstructionFormat());
        };
    }
}
