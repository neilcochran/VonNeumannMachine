package com.neilcochran.instruction.formatGroup.B.command;

import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.formatGroup.B.InstructionB;

/**
 * Represents the BR command which is used to branch by updating the program counter's value
 */
public class BR extends Command {

    /**
     * Constructs an BR command from the given InstructionB
     * @param instruction The InstructionB to construct the command from
     * @param registerFile A reference to the machine's RegisterFile
     */
    public BR(InstructionB instruction, RegisterFile registerFile) {
        super(instruction, registerFile);
    }

    /**
     * Execute the BR command to update the program counter (PC) value, thus changing the point of execution
     * If the link register (L) bit is set, the current PC value will be saved in the link register (LR)
     */
    @Override
    public void executeCommand() {
        InstructionB instructionB = (InstructionB) instruction;
        //L bit indicates if we should store PC+4 (already incremented in LR
        if(instructionB.getLinkRegisterFlagBit() == 1) {
            registerFile.getLinkRegister().setData(registerFile.getPCRegister().getData());
        }
        //bit shift right 2 (so lower 2 LSBs always 0s) and add to PC
        var imm26 = (instructionB.getImm24() << 2);
        registerFile.getPCRegister().setData(imm26 + registerFile.getPCRegister().getData());
    }
}
