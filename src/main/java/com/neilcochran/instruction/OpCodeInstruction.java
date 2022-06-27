package com.neilcochran.instruction;

import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.OpCode;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpCodeInstruction extends Instruction {
    public static final BitRange OPCODE_RANGE = new BitRange(21, 24);
    private static final int STATE_FLAG_INDEX =  20;
    private static final BitRange RN_OPERAND_RANGE = new BitRange(16, 19);
    private static final BitRange RD_OPERAND_RANGE = new BitRange(12, 15);

    protected final OpCode opCode;
    protected final int stateFlagBit;
    protected final int RN;
    protected final int RD;

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
