package com.neilcochran.instruction.formatGroup;

import com.neilcochran.instruction.LoadStoreInstruction;
import com.neilcochran.instruction.fields.InstructionFormat;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a D-Instruction
 */
@Getter
@Setter
public class InstructionD extends LoadStoreInstruction {
    private static final BitRange IMMEDIATE_CONSTANT_RANGE = new BitRange(0, 11);

    private final int imm12;

    /**
     * Takes in an D-Instruction and decodes it
     * @param instruction The full instruction
     * @throws IllegalArgumentException if the instruction is not a valid D-Instruction
     */
    public InstructionD(long instruction) {
        super(instruction, InstructionFormat.D);
        imm12 = BitUtils.getBitRange(instruction, IMMEDIATE_CONSTANT_RANGE);
    }

    @Override
    public String toString() {
        return String.format("InstructionD(instruction=%s condition=%s U=%d L=%d RN=%d RD=%d imm12=%d)", getBinaryString(), condition, loadStore.getLoadStoreBit(), loadStore.getOffsetAddSubBit(), RN, RD, imm12);
    }
}
