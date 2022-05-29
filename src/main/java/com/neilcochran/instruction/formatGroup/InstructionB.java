package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.Instruction;
import com.neilcochran.instruction.field.InstructionFormat;
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
        //When this value is used it will be right shifted by 2 (so multiplied by 4) and then added to the PC
        //This means that although it is a 24bit constant is will be used as a 26bit constant (where the 2 LSBs are always 0 due to our right shifts)
        imm24 = BitUtils.getBitRange(instruction, IMM12_RANGE);
    }

    @Override
    public String toString() {
        return String.format("InstructionB(instruction=%s condition=%s linkRegisterFlagBit=%d, imm24=%d)", getBinaryString(), condition, linkRegisterFlagBit, imm24);
    }
}
