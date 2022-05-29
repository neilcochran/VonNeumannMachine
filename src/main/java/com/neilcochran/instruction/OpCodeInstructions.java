package com.neilcochran.instruction;

import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.OpCode;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpCodeInstructions extends Instruction {
    public static final BitRange OPCODE_RANGE = new BitRange(21, 24);
    protected final OpCode opCode;

    public OpCodeInstructions(long instruction, InstructionFormat instructionFormat) {
        super(instruction, instructionFormat);
        //make sure we got a valid instruction format for a OpCodeInstruction (super() has to be first call otherwise validation would come first)
        if(instructionFormat != InstructionFormat.R && instructionFormat != InstructionFormat.I) {
            throw new IllegalArgumentException("OpCodeInstruction received an invalid InstructionFormat of: %s. InstructionFormat must be R or I");
        }
        this.opCode = new OpCode(BitUtils.getBitRange(instruction, OPCODE_RANGE));
    }
}
