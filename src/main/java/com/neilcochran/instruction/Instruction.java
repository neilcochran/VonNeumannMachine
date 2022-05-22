package com.neilcochran.instruction;

import com.neilcochran.component.VonNeumannMachine;
import com.neilcochran.util.BitUtils;
import lombok.Data;

/**
 * Represents an Instruction which contains an OpCode and Operand
 */
@Data
public class Instruction {
    private final int fullInstruction;
    private final OpCode opCode;
    private final Operand operand;

    /**
     * Takes in an instruction (int of binary data) and decodes it into the appropriate OpCode and Operand
     * @param instruction The instruction input
     * @throws IllegalArgumentException if the instruction exceeds the word size
     */
    public Instruction(int instruction) {
        //Ensure the instruction does not exceed our word size
        if(!BitUtils.validateBitLength(instruction, VonNeumannMachine.WORD_SIZE)) {
            throw new IllegalArgumentException(String.format("Invalid instruction bit length. The value: %d exceeds the bit length: %d", instruction, VonNeumannMachine.WORD_SIZE));
        }
        this.fullInstruction = instruction;
        this.opCode = new OpCode(BitUtils.getBitRange(this.fullInstruction, 0, 4));
        this.operand = new Operand(BitUtils.getBitRange(this.fullInstruction, 5, BitUtils.getBitLength(this.fullInstruction)));
    }
}