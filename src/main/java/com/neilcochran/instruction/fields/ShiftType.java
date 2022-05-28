package com.neilcochran.instruction.fields;

public enum ShiftType {
    LOGICAL_LEFT(0b00),
    LOGICAL_RIGHT(0b01),
    ARITHMETIC_RIGHT(0b10),
    ROTATE_RIGHT(0b11);

    private final int bits;

    ShiftType(int bits) {
        this.bits = bits;
    }

    public int getBits() {
        return bits;
    }

    public static ShiftType fromBits(int bits) {
        for(ShiftType shiftType : values()) {
            if(shiftType.bits == bits) {
                return shiftType;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid ShiftType bits: %s did not match any known ShiftType", Integer.toBinaryString(bits)));
    }
}
