package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.OpCodeInstruction;

/**
 * Represents the `CMN` command which is used to compare two operands via addition and setting any relevant Program Status Register (PSR) flags
 */
public class CMN extends CommandRI {

    private final ProgramStatusRegister PSR;

    /**
     * Constructs a CMN command from the given `OpCodeInstruction`
     * @param instruction The `OpCodeInstruction` to construct the command from
     * @param registerFile A reference to the machine's `RegisterFile`
     * @param PSR A reference the machine's Program Status Register (PSR)
     */
    public CMN(OpCodeInstruction instruction, RegisterFile registerFile, ProgramStatusRegister PSR) {
        super(instruction, registerFile);
        this.PSR = PSR;
    }

    /**
     * Execute the CMN command which will add both operands and set any relevant Program Status Register (PSR) flags
     * Note: This is the same as performing an `ADDS` (the ADD command with the state flag set)
     */
    @Override
    public void executeCommand() {
        OpCodeInstruction cmnInstruction = (OpCodeInstruction) instruction;
        var operand1 = registerFile.getRegister(cmnInstruction.getRN()).getData();
        var operand2 = calculateOperand2();
        //perform addition so PSR flags are set, but discard the result
        ALU.add(operand1, operand2, PSR);
    }
}
