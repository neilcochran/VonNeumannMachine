package com.neilcochran.instruction;

import com.neilcochran.util.BitUtils;
import lombok.Data;

/**
 * Represents an Operand
 */
@Data
public class Operand {
    private static final int OPERAND_BIT_LENGTH = 12;
    private final int data;

    /**
     * Creates a new Operand from a given operand integer
     * @param operand The operand integer
     * @throws IllegalArgumentException if the operand bit length exceeds {@link Operand#OPERAND_BIT_LENGTH OPERAND_BIT_LENGTH}
     */
    public Operand(int operand) {
        if (!BitUtils.validateBitLength(operand, OPERAND_BIT_LENGTH)) {
            throw new IllegalArgumentException(String.format("Operand: %d's bit length: %d exceeded the limit of: %d bits", operand, BitUtils.getBitLength(operand), OPERAND_BIT_LENGTH));
        }
        this.data = operand;
    }
}
