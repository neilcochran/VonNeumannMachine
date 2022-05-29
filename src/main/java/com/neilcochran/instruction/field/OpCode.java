package com.neilcochran.instruction.field;

import lombok.Data;

@Data
public class OpCode {
    private String name;
    private final int bits;

    public OpCode(int bits) {
        this.bits = bits;
    }
}