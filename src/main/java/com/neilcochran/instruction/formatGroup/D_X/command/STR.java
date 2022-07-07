package com.neilcochran.instruction.formatGroup.D_X.command;

import com.neilcochran.component.ControlUnit;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.LoadStoreInstruction;

public class STR extends CommandDX {

    public STR(LoadStoreInstruction instruction, RegisterFile registerFile, ControlUnit controlUnit) {
        super(instruction, registerFile, controlUnit);
    }

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
