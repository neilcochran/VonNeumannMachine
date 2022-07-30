package com.neilcochran.instruction;

import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.OpCode;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the common base for data processing instructions that contain OpCodes
 */
@Getter
@Setter
public class OpCodeInstruction extends Instruction {

    /**
     * The bit range the encodes the instruction OpCode
     * @see com.neilcochran.instruction.field.OpCode
     */
    public static final BitRange OPCODE_RANGE = new BitRange(21, 24);

    /**
     * The bit index of the state flag which normally denotes if the Program Status Register should be updated during execution
     * @see com.neilcochran.instruction.OpCodeInstruction#stateFlagBit
     */
    private static final int STATE_FLAG_INDEX =  20;

    /**
     * The bit range that denotes the RN operand
     * @see com.neilcochran.instruction.OpCodeInstruction#RN
     */
    private static final BitRange RN_OPERAND_RANGE = new BitRange(16, 19);

    /**
     * The bit range that denotes the RD operand
     * @see com.neilcochran.instruction.OpCodeInstruction#RD
     */
    private static final BitRange RD_OPERAND_RANGE = new BitRange(12, 15);

    /**
     * The instruction's OpCode
     */
    protected final OpCode opCode;

    /**
     * The state flag (S) bit
     */
    protected final int stateFlagBit;

    /**
     * The RN register number
     */
    protected final int RN;

    /**
     * The RD register number
     */
    protected final int RD;

    /**
     * Constructs a new OpCodeInstruction of the given instructionFormat from the instruction integer
     * @param instruction The integer that encodes for the given Instruction
     * @param instructionFormat The InstructionFormat of the Instruction
     */
    public OpCodeInstruction(int instruction, InstructionFormat instructionFormat) {
        super(instruction, instructionFormat);
        //make sure we got a valid instruction format for a OpCodeInstruction (super() has to be first call otherwise validation would come first)
        if(instructionFormat != InstructionFormat.R && instructionFormat != InstructionFormat.I) {
            throw new IllegalArgumentException("OpCodeInstruction received an invalid InstructionFormat of: %s. InstructionFormat must be R or I");
        }
        this.opCode = OpCode.fromBits(BitUtils.getBitRange(instruction, OPCODE_RANGE));
        this.stateFlagBit = BitUtils.getKthBit(instruction, STATE_FLAG_INDEX);
        this.RN = BitUtils.getBitRange(instruction, RN_OPERAND_RANGE);
        this.RD = BitUtils.getBitRange(instruction, RD_OPERAND_RANGE);
    }
}
