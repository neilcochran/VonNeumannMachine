package com.neilcochran.instruction.fields;

public enum Condition {
    EQ(0b0000, "Equals"),
    NE(0b0001, "Not equals"),
    CS(0b0010, "Carry set"),
    CC(0b0011, "Carry clear"),
    MI(0b0100, "Negative"),
    PL(0b0101, "Positive or zero"),
    VS(0b0110, "Overflow"),
    VC(0b0111, "No overflow"),
    HI(0b1000, "Higher (unsigned)"),
    LS(0b1001, "Lower or same (unsigned)"),
    GE(0b1010, "Greater or equal (signed)"),
    LT(0b1011, "Less than (signed)"),
    GT(0b1100, "Greater than (signed)"),
    LE(0b1101, "Less than or equal (signed)"),
    AL(0b1110, "Always"),
    NV(0b1111, "NO VALUE - RESERVED");

    private final int formatBits;
    private final String description;

    Condition(int formatBits, String description) {
        this.formatBits = formatBits;
        this.description = description;
    }

    public int getFormatBits() {
        return formatBits;
    }

    public static Condition fromFormatBits(int formatBits) {
        for(Condition condition : values()) {
            if(condition.formatBits == formatBits) {
                return condition;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid instruction condition: %s did not match any known Conditions", Integer.toBinaryString(formatBits)));
    }
}
