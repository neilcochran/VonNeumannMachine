package com.neilcochran.instruction;

import com.neilcochran.component.VonNeumannMachine;
import com.neilcochran.instruction.fields.Condition;
import com.neilcochran.instruction.fields.InstructionFormat;
import com.neilcochran.instruction.fields.OpCode;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Data;

/**
 * Represents the base of an Instruction from which specific InstructionFormats will extend
 */
@Data
public class Instruction {
    protected String name;
    protected InstructionFormat instructionFormat;
    protected Condition condition;
    protected final int instruction;
    protected final OpCode opCode;
    public static final BitRange CONDITION_RANGE = new BitRange(28, 31);
    public static final BitRange FORMAT_RANGE = new BitRange(25, 27);
    public static final BitRange OPCODE_RANGE = new BitRange(20, 24);

    /**
     * Takes in an instruction (int of binary data) and decodes it into the appropriate OpCode and Operand
     * @throws IllegalArgumentException if the instruction exceeds the word size
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
        this.opCode = new OpCode(BitUtils.getBitRange(this.instruction, OPCODE_RANGE));
    }

    /**
     * Parse the instruction and determine its InstructionFormat enum
     * @param instruction The instruction to determine the InstructionFormat of
     * @return The InstructionFormat field of the given instruction
     */
    public static InstructionFormat parseInstructionFormat(int instruction) {
        return InstructionFormat.fromFormatBits(BitUtils.getBitRange(instruction, FORMAT_RANGE));
    }

    /**
     * Parse the instruction and determine its Condition field
     * @param instruction The instruction to determine the Condition field of
     * @return The Condition field of the given instruction
     */
    public static Condition parseInstructionCondition(int instruction) {
        return Condition.fromFormatBits(BitUtils.getBitRange(instruction, CONDITION_RANGE));
    }
}