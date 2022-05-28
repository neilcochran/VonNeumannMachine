package com.neilcochran.instruction.fields;

import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Data;

//immediate constants are encoded in two parts: 4 bit Rotate and 8 bit for constant value
@Data
public class ImmediateConstant {
    public static final BitRange ROTATE_RANGE = new BitRange(8, 11);
    public static final BitRange IMMEDIATE_RANGE = new BitRange(0, 7);
    private static  final ShiftType SHIFT_TYPE = ShiftType.ROTATE_RIGHT;
    private final int rotate;
    private final int immediate;
    private final int result;

    public ImmediateConstant(int immediateConstant) {
        this.rotate = BitUtils.getBitRange(immediateConstant, ROTATE_RANGE);
        this.immediate = BitUtils.getBitRange(immediateConstant, IMMEDIATE_RANGE);
        //algorithm for taking an 8 bit constant and turning it into a 32 bit constant using rotation
        //imm32 = (imm8 >> (2 * rotate)) | (imm8 << (32 - (2 * rotate)))
        this.result = Integer.rotateRight(immediate, 2 * rotate) | Integer.rotateLeft(immediate, 32 - (2 * rotate));
    }
}
