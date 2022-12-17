package com.neilcochran.instruction.field;

/**
 * An enum representing all supported 4 bit condition codes
 */
public enum Condition {

    /**
     * Equals
     */
    EQ(0b0000, "Equals"),

    /**
     * Not equals
     */
    NE(0b0001, "Not equals"),

    /**
     * Carry set
     */
    CS(0b0010, "Carry set"),

    /**
     * Carry clear
     */
    CC(0b0011, "Carry clear"),

    /**
     * Negative
     */
    MI(0b0100, "Negative"),

    /**
     * Positive or zero
     */
    PL(0b0101, "Positive or zero"),

    /**
     * Overflow
     */
    VS(0b0110, "Overflow"),

    /**
     * No overflow
     */
    VC(0b0111, "No overflow"),

    /**
     * Higher (unsigned)
     */
    HI(0b1000, "Higher (unsigned)"),

    /**
     * Lower or same (unsigned)
     */
    LS(0b1001, "Lower or same (unsigned)"),

    /**
     * Greater or equal (signed)
     */
    GE(0b1010, "Greater or equal (signed)"),

    /**
     * Less than (signed)
     */
    LT(0b1011, "Less than (signed)"),

    /**
     * Greater than (signed)
     */
    GT(0b1100, "Greater than (signed)"),

    /**
     * Less than or equal (signed)
     */
    LE(0b1101, "Less than or equal (signed)"),

    /**
     * Always
     */
    AL(0b1110, "Always");

    private final int formatBits;
    private final String description;

    /**
     * Constructs a Condition with a backing integer formatBits and a description
     * @param formatBits The integer that corresponds to the condition
     * @param description The condition description
     */
    Condition(int formatBits, String description) {
        this.formatBits = formatBits;
        this.description = description;
    }

    public int getFormatBits() {
        return formatBits;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Return the Condition corresponding to the given formatBits
     * @param formatBits The integer representing the condition
     * @return The Condition corresponding to the given formatBits
     * @throws IllegalArgumentException If the formatBits do not map to a valid Condition e
     */
    public static Condition fromFormatBits(int formatBits) {
        for(Condition condition : values()) {
            if(condition.formatBits == formatBits) {
                return condition;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid instruction condition: %s did not match any known Conditions", Integer.toBinaryString(formatBits)));
    }
}
