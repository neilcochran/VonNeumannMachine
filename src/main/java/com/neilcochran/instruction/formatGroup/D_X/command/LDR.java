package com.neilcochran.instruction.formatGroup.D_X.command;

import com.neilcochran.component.ControlUnit;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.LoadStoreInstruction;

public class LDR extends CommandDX {

    public LDR(LoadStoreInstruction instruction, RegisterFile registerFile, ControlUnit controlUnit) {
        super(instruction, registerFile, controlUnit);
    }

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
        MDR.loadData(ldrInstruction.getLoadStore().getDataSize());
        //set result in RD
        RD.setData(MDR.getData());
    }
}
