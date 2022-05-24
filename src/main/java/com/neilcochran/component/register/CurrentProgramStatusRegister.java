package com.neilcochran.component.register;

import com.neilcochran.util.BitUtils;

/**
 * Represents the Current Program Status Register (CPSR) which holds various flags/states/statuses
 */
public class CurrentProgramStatusRegister extends Register {

    /**
     * The bit index of the Negative Condition (N) flag
     */
    private static final int N_BIT_INDEX = 31;

    /**
     * The bit index of the Zero Condition (Z) flag
     */
    private static final int Z_BIT_INDEX = 30;

    /**
     * The bit index of the Carry Condition (C) flag
     */
    private static final int C_BIT_INDEX = 29;

    /**
     * The bit index of the Overflow (V) flag
     */
    private static final int V_BIT_INDEX = 28;

    /**
     * The bit index of the 'sticky' Overflow (Q) flag
     */
    private static final int Q_BIT_INDEX = 27;

    /**
     * Constructs a new CurrentProgramStatusRegister which holds various flags/states/statuses
     */
    public CurrentProgramStatusRegister() {
        super("CPSR");
    }

    /**
     * Return the Negative Condition (N) bit flag which is the result of the last flag-setting instruction.
     * If the result is regarded as a two's complement signed integer, then N is set to 1 if the result was negative, and N is set to 0 if the result was positive or zero.
     * @return The N flag bit value
     */
    public int getNBit() {
        return BitUtils.getKthBit(data, N_BIT_INDEX);
    }

    /**
     * Return the Zero Condition (Z) bit flag which is set to 1 if the result of the last flag-setting instruction was zero, and to 0 otherwise.
     * @return The Z flag bit value
     */
    public int getZBit() {
        return BitUtils.getKthBit(data, Z_BIT_INDEX);
    }

    /**
     * Returns the Carry Condition (C) bit flag.
     * A carry occurs when:
     *  if the result of an addition is greater than or equal to 2^32
     *  if the result of a subtraction is positive or zero
     *  as the result of an inline barrel shifter operation in a move or logical instruction.
     * @return The C flag bit value
     */
    public int getCBit() {
        return BitUtils.getKthBit(data, C_BIT_INDEX);
    }

    /**
     * Returns the Overflow Condition (V) bit flag
     * An overflow occurs if the result of an add, subtract, or compare is greater than or equal to 2^31, or less than 2^31
     * @return The V flag bit value
     */
    public int getVBit() {
        return BitUtils.getKthBit(data, V_BIT_INDEX);
    }

    /**
     * Returns the 'sticky' Overflow (Q) bit flag
     * A 'sticky' version of overflow is caused by instructions that generate multiple results
     * @return The Q flag bit value
     */
    public int getQBit() {
        return BitUtils.getKthBit(data, Q_BIT_INDEX);
    }
}
