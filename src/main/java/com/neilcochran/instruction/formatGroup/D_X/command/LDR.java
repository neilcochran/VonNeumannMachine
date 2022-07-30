package com.neilcochran.instruction.formatGroup.D_X.command;

import com.neilcochran.component.ControlUnit;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.util.BitUtils;

/**
 * Represents the `LDR` command which is used to load data from memory into registers
 */
public class LDR extends CommandDX {

    /**
     * Constructs an LDR command from the given `LoadStoreInstruction`
     * @param instruction The `LoadStoreInstruction` to construct the command from
     * @param registerFile A reference to the machine's `RegisterFile`
     * @param controlUnit A reference to the machine's `ControlUnit`
     */
    public LDR(LoadStoreInstruction instruction, RegisterFile registerFile, ControlUnit controlUnit) {
        super(instruction, registerFile, controlUnit);
    }

    /**
     * Execute the `LDR` command to load the indicated data from memory into the target register
     */
    @Override
    public void executeCommand() {
        var ldrInstruction = (LoadStoreInstruction) instruction;
        var MAR = controlUnit.getMemoryAddressRegister();
        var MDR = controlUnit.getMemoryDataRegister();
        var RD = registerFile.getRegister(ldrInstruction.getRD());
        var operand2 = calculateOperand2();
        //set the calculated address in the MAR
        MAR.setData(operand2);
        //MDR loads the data pointed to by MAR
        MDR.loadData(ldrInstruction.getDataSize(), BitUtils.bitToBool(ldrInstruction.getSignBit()));
        //set result in RD
        RD.setData(MDR.getData());
    }
}
