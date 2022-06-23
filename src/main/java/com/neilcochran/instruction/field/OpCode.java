package com.neilcochran.instruction.field;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum OpCode {
    AND(0b0000),
    EOR(0b0001),
    SUB(0b0010),
    RSB(0b0011),
    ADD(0b0100),
    ADC(0b0101),
    SBC(0b0110),
    RSC(0b0111),
    TST(0b1000),
    TEQ(0b1001),
    CMP(0b1010),
    CMN(0b1011),
    ORR(0b1100),
    MOV(0b1101),
    BIC(0b1110),
    MVN(0b1111);
    private final int bits;

    public static OpCode fromBits(int bits) {
        for(OpCode opCode: values()) {
            if(opCode.getBits() == bits) {
                return opCode;
            }
        }
        throw new IllegalArgumentException(String.format("The bits: %s did not match any known OpCode", Integer.toBinaryString(bits)));
    }
}