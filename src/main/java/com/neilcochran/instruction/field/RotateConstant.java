package com.neilcochran.instruction.field;

import com.neilcochran.util.BitUtils;
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
        this.result = BitUtils.calculateRotate(rotate, immediate);
    }
}
