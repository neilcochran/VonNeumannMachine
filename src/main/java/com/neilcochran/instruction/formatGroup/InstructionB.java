package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.fields.InstructionFormat;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructionB extends Instruction {
    private static final int L_FLAG_INDEX = 24;
    private static final BitRange IMM12_RANGE = new BitRange(0, 11);

    private final int linkRegisterFlagBit;
    private final int imm24;

    public InstructionB(long instruction) {
        super(instruction, InstructionFormat.B);
        linkRegisterFlagBit = BitUtils.getKthBit(instruction, L_FLAG_INDEX);
        imm24 = BitUtils.getBitRange(instruction, IMM12_RANGE);
    }
}
