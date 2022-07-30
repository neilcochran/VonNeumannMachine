package com.neilcochran.instruction.field;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * An enum representing all supported 4 bit OpCodes
 */
@AllArgsConstructor
@Getter
public enum OpCode {

    //TODO add/confirm all OpCode javadoc names

    /**
     * And
     */
    AND(0b0000),

    /**
     * Exclusive Or ?
     */
    EOR(0b0001),

    /**
     * Subtract
     */
    SUB(0b0010),

    /**
     * Reverse subtract ?
     */
    RSB(0b0011),

    /**
     * Add
     */
    ADD(0b0100),

    /**
     * ?
     */
    ADC(0b0101),

    /**
     * ?
     */
    SBC(0b0110),

    /**
     * ?
     */
    RSC(0b0111),

    /**
     * ?
     */
    TST(0b1000),

    /**
     * ?
     */
    TEQ(0b1001),

    /**
     * Compare (subtract)
     */
    CMP(0b1010),

    /**
     * Compare (add)
     */
    CMN(0b1011),

    /**
     * Or ?
     */
    ORR(0b1100),

    /**
     * Move data to a register
     */
    MOV(0b1101),

    /**
     * ?
     */
    BIC(0b1110),

    /**
     * Move negated data to a register
     */
    MVN(0b1111);

    private final int bits;

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
}