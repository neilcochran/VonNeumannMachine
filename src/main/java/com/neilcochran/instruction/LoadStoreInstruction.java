package com.neilcochran.instruction;

import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadStoreInstruction extends Instruction {

    private static final int SIGN_BIT_INDEX = 24;
    private static final int U_BIT_INDEX = 23;
    private static final BitRange DATA_SIZE_RANGE = new BitRange(21, 22);
    private static final int L_BIT_INDEX = 20;
    private static final BitRange RN_RANGE = new BitRange(16, 19);
    private static final BitRange RD_RANGE = new BitRange(12, 15);

    //indicate if we are loading (1) or storing (0)
    private final int loadStoreBit;
    //the data size to be loaded/stored
    private final DataSize dataSize;
    //indicate if the offset should be added (1) or subtracted (0)
    private final int offsetAddSubBit;
    //indicate signed/unsigned: sign extended (1) or zero extended (0)
    private final int signBit;

    protected final int RN;
    protected final int RD;

    public LoadStoreInstruction(int instruction, InstructionFormat instructionFormat) {
        super(instruction, instructionFormat);
        //make sure we got a valid instruction format for a LoadStoreInstruction (super() has to be first call otherwise validation would come first)
        if(instructionFormat != InstructionFormat.D && instructionFormat != InstructionFormat.X) {
            throw new IllegalArgumentException("LoadStoreInstruction received an invalid InstructionFormat of: %s. InstructionFormat must be D or X");
        }
        loadStoreBit = BitUtils.getKthBit(instruction, L_BIT_INDEX);
        dataSize = switch (BitUtils.getBitRange(instruction, DATA_SIZE_RANGE)) {
            case 0b00 -> DataSize.WORD;
            case 0b01 -> DataSize.HALF_WORD;
            case 0b11 -> DataSize.BYTE;
            default -> throw new IllegalArgumentException("Invalid LoadStoreInstruction DataSize bits: " + Integer.toBinaryString(BitUtils.getBitRange(instruction, DATA_SIZE_RANGE)));
        };
        offsetAddSubBit = BitUtils.getKthBit(instruction, U_BIT_INDEX);
        signBit = BitUtils.getKthBit(instruction, SIGN_BIT_INDEX);
        RN = BitUtils.getBitRange(instruction, RN_RANGE);
        RD = BitUtils.getBitRange(instruction, RD_RANGE);
    }
}
