package com.neilcochran.instruction;

import com.neilcochran.component.VonNeumannMachine;
import com.neilcochran.instruction.field.Condition;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;
import lombok.Data;

/**
 * Represents the base of an Instruction from which specific InstructionFormats will extend
 */
@Data
public class Instruction {

    /**
     * The bit range that encodes for the instruction condition
     * @see com.neilcochran.instruction.field.Condition
     */
    public static final BitRange CONDITION_RANGE = new BitRange(28, 31);

    /**
     * The bit range that encodes for the instruction format
     * @see com.neilcochran.instruction.field.InstructionFormat
     */
    public static final BitRange FORMAT_RANGE = new BitRange(25, 27);

    protected final int instruction;
    protected final Condition condition;
    protected final InstructionFormat instructionFormat;


    /**
     * Constructs an Instruction of the given `instructionFormat` from the `instruction` integer
     * @param instruction The integer that encodes for the given `Instruction`
     * @param instructionFormat The `InstructionFormat` of the `Instruction`
     * @throws IllegalArgumentException if the instruction is encoding is invalid
     */
    public Instruction(int instruction, InstructionFormat instructionFormat) {
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
     * Parse the instruction and determine its `InstructionFormat`
     * @param instruction The instruction to determine the `InstructionFormat` of
     * @return The `InstructionFormat` field of the given `Instruction`
     */
    public static InstructionFormat parseInstructionFormat(int instruction) {
        return InstructionFormat.fromFormatBits(BitUtils.getBitRange(instruction, FORMAT_RANGE));
    }

    /**
     * Parse the instruction and determine its `Condition` field
     * @param instruction The instruction to determine the `Condition` field of
     * @return The `Condition` field of the given instruction
     */
    public static Condition parseInstructionCondition(int instruction) {
        return Condition.fromFormatBits(BitUtils.getBitRange(instruction, CONDITION_RANGE));
    }

    /**
     * Get a binary string representation of the instruction
     * @return A binary string representation of the instruction
     */
    public String getBinaryString() {
        return BitUtils.getBinaryString(instruction, DataSize.WORD, true);
    }
}