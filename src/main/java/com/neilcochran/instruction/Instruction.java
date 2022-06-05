package com.neilcochran.instruction;

import com.neilcochran.component.VonNeumannMachine;
import com.neilcochran.instruction.field.Condition;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Data;

/**
 * Represents the base of an Instruction from which specific InstructionFormats will extend
 */
@Data
public class Instruction {
    protected final long instruction; //must be long since we're using 32 bit instructions (unsigned) which is out of 32 bit int's signed range
    protected final Condition condition;
    protected final InstructionFormat instructionFormat;
    public static final BitRange CONDITION_RANGE = new BitRange(28, 31);
    public static final BitRange FORMAT_RANGE = new BitRange(25, 27);

    /**
     * Takes in an instruction (int of binary data) and decodes it into the appropriate OpCode and Operand
     * @throws IllegalArgumentException if the instruction exceeds the word size
     */
    public Instruction(long instruction, InstructionFormat instructionFormat) {
        //Ensure the instruction does not exceed our word size
        if(!BitUtils.validateBitLength(instruction, VonNeumannMachine.WORD_SIZE)) {
            throw new IllegalArgumentException(String.format("Invalid instruction bit length. The value: %d exceeds the bit length: %d", instruction, VonNeumannMachine.WORD_SIZE));
        }
        //Ensure the InstructionFormat is correct for the instruction
        if(instructionFormat != parseInstructionFormat(instruction)) {
            throw new IllegalArgumentException(
                    String.format("The given instruction format: %s (bit pattern: %s) does not match the instruction: %d",
                        instructionFormat,
                        Integer.toBinaryString(instructionFormat.getFormatBits()), instruction
                    )
            );
        }
        this.instruction = instruction;
        this.condition = parseInstructionCondition(this.instruction);
        this.instructionFormat = instructionFormat;
    }

    /**
     * Parse the instruction and determine its InstructionFormat enum
     * @param instruction The instruction to determine the InstructionFormat of
     * @return The InstructionFormat field of the given instruction
     */
    public static InstructionFormat parseInstructionFormat(long instruction) {
        return InstructionFormat.fromFormatBits(BitUtils.getBitRange(instruction, FORMAT_RANGE));
    }

    /**
     * Parse the instruction and determine its Condition field
     * @param instruction The instruction to determine the Condition field of
     * @return The Condition field of the given instruction
     */
    public static Condition parseInstructionCondition(long instruction) {
        return Condition.fromFormatBits(BitUtils.getBitRange(instruction, CONDITION_RANGE));
    }

    public String getBinaryString() {
        var padLen = 32 - BitUtils.getBitLength(instruction);
        if(padLen <= 0) {
            return Long.toBinaryString(instruction).substring(32);
        }
        return "0".repeat(padLen) + Long.toBinaryString(instruction);
    }
}