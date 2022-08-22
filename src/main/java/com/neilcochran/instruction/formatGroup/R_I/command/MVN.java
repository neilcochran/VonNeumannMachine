package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.OpCodeInstruction;

/**
 * Represents the MVN command which is used to negate and then move data to a register
 */
public class MVN extends CommandRI {

    private final ProgramStatusRegister PSR;

    /**
     * Constructs a MVN command from the given OpCodeInstruction
     * @param instruction The OpCodeInstruction to construct the command from
     * @param registerFile A reference to the machine's RegisterFile
     * @param PSR A reference the machine's Program Status Register (PSR)
     */
    public MVN(OpCodeInstruction instruction, RegisterFile registerFile, ProgramStatusRegister PSR) {
        super(instruction, registerFile);
        this.PSR = PSR;
    }

    /**
     * Execute the MVN command which will first negate operand2 before moving it into RD
     * If the state flag (S) is set then the relevant Program Status Register (PSR) flags will be updated
     */
    @Override
    public void executeCommand() {
        var mvnInstruction = (OpCodeInstruction) instruction;
        //TODO enforce register Rd restrictions (mostly around PC/LR)
        var RD = registerFile.getRegister(mvnInstruction.getRD());
        //negate (~) operand2
        var operand2 = ~calculateOperand2();
        RD.setData(operand2);
        //if S flag set, update N & Z flags (C flag may have been set during barrel shift)
        if(mvnInstruction.getStateFlagBit() == 1) {
            ALU.setNZFlags(operand2, PSR);
        }
    }
}
