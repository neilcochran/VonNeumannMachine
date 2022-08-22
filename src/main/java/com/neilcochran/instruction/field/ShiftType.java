package com.neilcochran.instruction.field;

/**
 * An enum representing the supported types of bit shifts
 */
public enum ShiftType {

    /**
     * Logical left bit shift
     */
    LOGICAL_LEFT(0b00),

    /**
     * Logical right bit shift
     */
    LOGICAL_RIGHT(0b01),

    /**
     * Arithmetic right bit shift
     */
    ARITHMETIC_RIGHT(0b10),

    /**
     * Right rotate bit shift
     */
    ROTATE_RIGHT(0b11);

    private final int bits;

    /**
     * Constructs a new ShiftType enum
     * @param bits The integer representing the ShiftType
     */
    ShiftType(int bits) {
        this.bits = bits;
    }

    /**
     * Get the integer bits that correspond to the ShiftType
     * @return The integer bits that correspond to the ShiftType
     */
    public int getBits() {
        return bits;
    }

    /**
     * Return the ShiftType corresponding to the given bit
     * @param bits The integer representing the ShiftType
     * @return The integer representing the ShiftType
     * @throws IllegalArgumentException if the given bits don't map to a valid ShiftType
     */
    public static ShiftType fromBits(int bits) {
        for(ShiftType shiftType : values()) {
            if(shiftType.bits == bits) {
                return shiftType;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid ShiftType bits: %s did not match any known ShiftType", Integer.toBinaryString(bits)));
    }
}
