package com.neilcochran.instruction.field;


/**
 * An enum representing the different instruction formats and the bit patterns that define them
 */
public enum InstructionFormat {

    /**
     * R Instruction format group
     */
    R(0b000),

    /**
     * I Instruction format group
     */
    I(0b001),

    /**
     * D Instruction format group
     */
    D(0b010),

    /**
     * X Instruction format group
     */
    X(0b011),

    /**
     * B Instruction format group
     */
    B(0b101);

    private final int formatBits;

    /**
     * Constructs an InstructionFormat enum with its associated bit pattern (int)
     * @param matchingFormat The number (bits) that matches the instruction format
     */
    InstructionFormat(int matchingFormat) {
        this.formatBits = matchingFormat;
    }

    public int getFormatBits() {
        return formatBits;
    }

    /**
     * Look up an InstructionFormat by its matchingFormat bits (int)
     * @param formatBits The number (bits) to find the InstructionFormat of
     * @return The matching InstructionFormat
     * @throws IllegalArgumentException If the given matchingFormat is not associated with any InstructionFormat
     */
    public static InstructionFormat fromFormatBits(int formatBits) {
        for(InstructionFormat instructionFormat : values()) {
            if(instructionFormat.formatBits == formatBits) {
                return instructionFormat;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid instruction format: %s did not match any known instruction format", Integer.toBinaryString(formatBits)));
    }
}
