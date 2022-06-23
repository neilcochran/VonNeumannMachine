package com.neilcochran.component;

import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.formatGroup.B.InstructionB;
import com.neilcochran.instruction.formatGroup.B.command.BRC;
import com.neilcochran.instruction.formatGroup.D.InstructionD;
import com.neilcochran.instruction.formatGroup.I.InstructionI;
import com.neilcochran.instruction.formatGroup.R.InstructionR;
import com.neilcochran.instruction.formatGroup.X.InstructionX;
import com.neilcochran.util.BitUtils;

/**
 * Represents a ControlUnit which orchestrates the decoding and execution steps
 * @param registerFile A reference to the registers
 * @param alu A reference to the Arithmetic Logic Unit

 */
public record ControlUnit(RegisterFile registerFile, ALU alu) {

    /**
     * Decode the current instruction into the correct Instruction type.
     * @param instruction The input instruction
     * @return An Instruction object representing the decoded instruction
     * @throws IllegalArgumentException if the instruction is invalid
     */
    public static Instruction decodeInstruction(int instruction) {
        if(!BitUtils.validateBitLength(instruction, VonNeumannMachine.WORD_SIZE)) {
            throw new IllegalArgumentException("Invalid instruction bit length: " + BitUtils.getBitLength(instruction));
        }
        var instructionFormat = InstructionFormat.fromFormatBits(BitUtils.getBitRange(instruction, Instruction.FORMAT_RANGE));
        return switch(instructionFormat) {
            case R -> new InstructionR(instruction);
            case I -> new InstructionI(instruction);
            case D -> new InstructionD(instruction);
            case X -> new InstructionX(instruction);
            case B -> new InstructionB(instruction);
        };
    }

    //Determine if CU or ALU will execute the instruction
    public void executeInstruction(Instruction instruction) {
        switch(instruction.getInstructionFormat()) {
            case R, I -> alu.executeInstruction((OpCodeInstruction) instruction);
            case D, X -> executeInstruction((LoadStoreInstruction) instruction);
            case B -> executeInstruction((InstructionB) instruction);
        }
    }

    private void executeInstruction(LoadStoreInstruction instruction) {
        //todo get command and execute in CU (here)
    }

    private void executeInstruction(InstructionB instruction) {
        BRC brc = new BRC(instruction, registerFile);
        brc.executeCommand();
    }
}
