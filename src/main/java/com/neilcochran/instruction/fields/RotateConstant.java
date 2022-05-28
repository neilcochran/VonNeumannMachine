package com.neilcochran.instruction.fields;

import lombok.Data;

//rotate constants are encoded in two parts: a 4 bit Rotate and an 8 bit constant value
@Data
public class RotateConstant {
    private final int rotate;
    private final int immediate;
    private final int result;

    public RotateConstant(int rotate, int immediateConstant) {
        this.rotate = rotate;
        this.immediate = immediateConstant;
        //algorithm for taking an 8 bit constant and turning it into a 32 bit constant using rotation
        //imm32 = (imm8 >> (2 * rotate)) | (imm8 << (32 - (2 * rotate)))
        this.result = Integer.rotateRight(immediate, 2 * rotate) | Integer.rotateLeft(immediate, 32 - (2 * rotate));
    }
}
