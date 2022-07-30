package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.OpCodeInstruction;

/**
 * Represents the `MOV` command which is used to move data to a register
 */
public class MOV extends CommandRI {

    private final ProgramStatusRegister PSR;

    /**
     * Constructs a MOV command from the given `OpCodeInstruction`
     * @param instruction The `OpCodeInstruction` to construct the command from
     * @param registerFile A reference to the machine's `RegisterFile`
     * @param PSR A reference the machine's Program Status Register (PSR)
     */
    public MOV(OpCodeInstruction instruction, RegisterFile registerFile, ProgramStatusRegister PSR) {
        super(instruction, registerFile);
        this.PSR = PSR;
    }

    /**
     * Execute the `MOV` command which will move operand2 into `RD`.
     * If the state flag (S) is set then the relevant Program Status Register (PSR) flags will be updated
     */
    @Override
    public void executeCommand() {
        var movInstruction = (OpCodeInstruction) instruction;
        //TODO enforce register Rd restrictions (mostly around PC/LR)
        var RD = registerFile.getRegister(movInstruction.getRD());
        var operand2 = calculateOperand2();
        RD.setData(operand2);
        //if S flag set, update N & Z flags (C flag may have been set during barrel shift)
        if(movInstruction.getStateFlagBit() == 1) {
            ALU.setNZFlags(operand2, PSR);
        }
    }
}
