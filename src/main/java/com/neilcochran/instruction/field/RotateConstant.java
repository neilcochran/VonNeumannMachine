package com.neilcochran.instruction.field;

import com.neilcochran.util.BitUtils;

/**
 * A RotateConstant consists of two parts: a rotate value and a constant value.
 * The result is calculated by rotating the constant by the amount indicated by rotate
 */
public class RotateConstant {
    private final int rotate;
    private final int constant;
    private final int result;

    /**
     * Constructs a new RotateConstant
     * @param rotate The amount to rotate the constant
     * @param constant The constant to be rotated
     */
    public RotateConstant(int rotate, int constant) {
        this.rotate = rotate;
        this.constant = constant;
        this.result = BitUtils.calculateRotate(rotate, this.constant);
    }

    /**
     * Get the rotate portion of the RotateConstant
     * @return The rotate portion of the RotateConstant
     */
    public int getRotate() {
        return rotate;
    }

    /**
     * Get the constant portion of the RotateConstant
     * @return The constant portion of the RotateConstant
     */
    public int getConstant() {
        return constant;
    }

    /**
     * Get the result of performing the rotation on the constant
     * @return The result of performing the rotation on the constant
     */
    public int getResult() {
        return result;
    }
}
