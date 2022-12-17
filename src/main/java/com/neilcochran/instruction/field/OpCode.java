package com.neilcochran.instruction.field;

/**
 * An enum representing all supported 4 bit OpCodes
 */
public enum OpCode {

    /**
     * And
     */
    AND(0b0000),

    /**
     * Exclusive Or
     */
    EOR(0b0001),

    /**
     * Subtract
     */
    SUB(0b0010),

    /**
     * Reverse Subtract
     */
    RSB(0b0011),

    /**
     * Add
     */
    ADD(0b0100),

    /**
     * Add with Carry
     */
    ADC(0b0101),

    /**
     * Subtract with Carry
     */
    SBC(0b0110),

    /**
     * Reverse Subtract with Carry
     */
    RSC(0b0111),

    /**
     * Test
     */
    TST(0b1000),

    /**
     * Test Equivalence
     */
    TEQ(0b1001),

    /**
     * Compare
     */
    CMP(0b1010),

    /**
     * Compare Negative
     */
    CMN(0b1011),

    /**
     * Or
     */
    ORR(0b1100),

    /**
     * Move
     */
    MOV(0b1101),

    /**
     * Bit Clear
     */
    BIC(0b1110),

    /**
     * Move Not
     */
    MVN(0b1111);

    private final int bits;

    /**
     * Construct an OpCode enum with it's associated integer (bits)
     * @param bits The backing integer for the OpCode
     */
    OpCode(int bits) {
        this.bits = bits;
    }

    /**
     * Returns the OpCode corresponding to the given bits
     * @param bits The integer representing the OpCode
     * @return The OpCode corresponding to the given bits
     * @throws IllegalArgumentException If the bits do not map to a valid OpCode
     */
    public static OpCode fromBits(int bits) {
        for(OpCode opCode: values()) {
            if(opCode.getBits() == bits) {
                return opCode;
            }
        }
        throw new IllegalArgumentException(String.format("The bits: %s did not match any known OpCode", Integer.toBinaryString(bits)));
    }

    /**
     * Get the OpCode's backing integer
     * @return The OpCode's backing integer
     */
    public int getBits() {
        return bits;
    }
}