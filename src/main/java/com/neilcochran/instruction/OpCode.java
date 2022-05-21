package com.neilcochran.instruction;

import com.neilcochran.utils.BitUtils;
import lombok.Data;

/**
 * Represents an OpCode
 */
@Data
public class OpCode {
    static final int OPCODE_BIT_LENGTH = 4;

    private final int data;
    private final String name;

    /**
     * Create a new OpCode object for the given opCode input
     * @param opCode The opCode input
     * @throws IllegalArgumentException if the opCode input is invalid
     */
    public OpCode(int opCode) {
        if(!BitUtils.validateBitLength(opCode, OPCODE_BIT_LENGTH)) {
            throw new IllegalArgumentException(String.format("Invalid OpCode length: %d. Expected: %d", opCode, OPCODE_BIT_LENGTH));
        }
        this.data = opCode;
        this.name = getOpCodeName(this.data);
    }

    /**
     * Map a given OpCode to it's name
     * @param opCode The OpCode input
     * @return The OpCode's corresponding name
     * @throws IllegalArgumentException if the opCode is invalid
     */
    public static String getOpCodeName(int opCode) {
        return switch (opCode) {
            case 0b0 -> "TODO_0";
            case 0b1 -> "TODO_1";
            case 0b10 -> "TODO_2";
            default -> "TODO FAKE";//throw new IllegalArgumentException("Invalid OpCode: " + opCode);
        };
    }
}
