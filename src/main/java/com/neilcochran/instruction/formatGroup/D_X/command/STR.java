package com.neilcochran.instruction.formatGroup.D_X.command;

import com.neilcochran.component.ControlUnit;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.LoadStoreInstruction;

/**
 * Represents the `STR` command which is used to store data from a register into memory
 */
public class STR extends CommandDX {

    /**
     * Constructs an STR command from the given `LoadStoreInstruction`
     * @param instruction The `LoadStoreInstruction` to construct the command from
     * @param registerFile A reference to the machine's `RegisterFile`
     * @param controlUnit A reference to the machine's `ControlUnit`
     */
    public STR(LoadStoreInstruction instruction, RegisterFile registerFile, ControlUnit controlUnit) {
        super(instruction, registerFile, controlUnit);
    }

    /**
     * Execute the `STR` command to store the indicated register data in memory
     */
    @Override
    public void executeCommand() {
        var strInstruction = (LoadStoreInstruction) instruction;
        var MAR = controlUnit.getMemoryAddressRegister();
        var MDR = controlUnit.getMemoryDataRegister();
        var RD = registerFile.getRegister(strInstruction.getRD());
        var operand2 = calculateOperand2();
        //set the calculated target address in MAR
        MAR.setData(operand2);
        //set the value to store in MDR
        MDR.setData(RD.getData());
        //MDR stores the value in memory
        MDR.storeData(strInstruction.getDataSize());
    }
}
