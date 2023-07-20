package com.neilcochran.instruction;

import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;

/**
 * Represents the common base for instructions that perform data loading or storing operations
 */
public class LoadStoreInstruction extends Instruction {

    /**
     * The bit index of the sign bit which denotes if sign extension should be performed, or zero padding
     * @see com.neilcochran.instruction.LoadStoreInstruction#signBit
     */
    private static final int SIGN_BIT_INDEX = 24;

    /**
     * The bit index of the add/subtract flag which denotes if the offset should be added or subtracted
     * @see com.neilcochran.instruction.LoadStoreInstruction#offsetAddSubBit
     */
    private static final int U_BIT_INDEX = 23;

    /**
     * The bit range that encodes the DataSize to be loaded/stored
     * @see com.neilcochran.instruction.LoadStoreInstruction#dataSize
     */
    private static final BitRange DATA_SIZE_RANGE = new BitRange(21, 22);

    /**
     * The bit index of the load/store flag which denotes if the instruction is loading or storing data
     * @see com.neilcochran.instruction.LoadStoreInstruction#loadStoreBit
     */
    private static final int L_BIT_INDEX = 20;

    /**
     * The bit range that represents the RN register number
     * @see com.neilcochran.instruction.LoadStoreInstruction#RN
     */
    private static final BitRange RN_RANGE = new BitRange(16, 19);

    /**
     * The bit range that represents the RD register number
     * @see com.neilcochran.instruction.LoadStoreInstruction#RD
     */
    private static final BitRange RD_RANGE = new BitRange(12, 15);

    //indicate if we are loading (1) or storing (0)
    private final int loadStoreBit;

    //the data size to be loaded/stored
    private final DataSize dataSize;

    //indicate if the offset should be added (1) or subtracted (0)
    private final int offsetAddSubBit;

    //indicate signed/unsigned: sign extended (1) or zero extended (0)
    private final int signBit;

    /**
     * The RN register number
     */
    protected final int RN;

    /**
     * The RD register number
     */
    protected final int RD;

    /**
     * Constructs a new LoadStoreInstruction of the given instructionFormat from the instruction integer
     * @param instruction The integer that encodes for the given Instruction
     * @param instructionFormat The InstructionFormat of the Instruction
     */
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
            case 0b10 -> DataSize.BYTE;
            default -> throw new IllegalArgumentException("Invalid LoadStoreInstruction DataSize bits: " + Integer.toBinaryString(BitUtils.getBitRange(instruction, DATA_SIZE_RANGE)));
        };
        offsetAddSubBit = BitUtils.getKthBit(instruction, U_BIT_INDEX);
        signBit = BitUtils.getKthBit(instruction, SIGN_BIT_INDEX);
        RN = BitUtils.getBitRange(instruction, RN_RANGE);
        RD = BitUtils.getBitRange(instruction, RD_RANGE);
    }

    public int getLoadStoreBit() {
        return loadStoreBit;
    }

    public DataSize getDataSize() {
        return dataSize;
    }

    public int getOffsetAddSubBit() {
        return offsetAddSubBit;
    }

    public int getSignBit() {
        return signBit;
    }

    public int getRN() {
        return RN;
    }

    public int getRD() {
        return RD;
    }
}
