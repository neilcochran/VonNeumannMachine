package com.neilcochran.instruction.formatGroup.R_I.command;

import com.neilcochran.component.ALU;
import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.OpCodeInstruction;

/**
 * Represents the `CMP` command which is used to compare two operands via subtraction and setting any relevant Program Status Register (PSR) flags
 */
public class CMP extends CommandRI {

    private final ProgramStatusRegister PSR;

    /**
     * Constructs a CMP command from the given `OpCodeInstruction`
     * @param instruction The `OpCodeInstruction` to construct the command from
     * @param registerFile A reference to the machine's `RegisterFile`
     * @param PSR A reference the machine's Program Status Register (PSR)
     */
    public CMP(OpCodeInstruction instruction, RegisterFile registerFile, ProgramStatusRegister PSR) {
        super(instruction, registerFile);
        this.PSR = PSR;
    }

    /**
     * Execute the CMP command which will subtract operand2 from operand1 and set any relevant Program Status Register (PSR) flags
     * Note: This is the same as performing an `SUBSS` (the SUB command with the state flag set)
     */
    @Override
    public void executeCommand() {
        var cmpInstruction = (OpCodeInstruction) instruction;
        var operand1 = registerFile.getRegister(cmpInstruction.getRN()).getData();
        var operand2 = calculateOperand2();
        //perform addition so PSR flags are set, but discard the result
        ALU.add(operand1, operand2, PSR);
    }
}
