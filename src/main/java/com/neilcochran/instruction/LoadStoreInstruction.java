package com.neilcochran.instruction;

import com.neilcochran.instruction.fields.InstructionFormat;
import com.neilcochran.instruction.fields.LoadStore;
import com.neilcochran.util.BitRange;
import com.neilcochran.util.BitUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadStoreInstruction extends Instruction {
    private static final BitRange LOAD_STORE_RANGE = new BitRange(20, 24);
    private static final BitRange RN_RANGE = new BitRange(16, 19);
    private static final BitRange RD_RANGE = new BitRange(12, 15);

    protected final LoadStore loadStore;
    protected final int RN;
    protected final int RD;

    public LoadStoreInstruction(long instruction, InstructionFormat instructionFormat) {
        super(instruction, instructionFormat);
        //make sure we got a valid instruction format for a LoadStoreInstruction (super() has to be first call otherwise validation would come first)
        if(instructionFormat != InstructionFormat.D && instructionFormat != InstructionFormat.X) {
            throw new IllegalArgumentException("LoadStoreInstruction received an invalid InstructionFormat of: %s. InstructionFormat must be D or X");
        }
        this.loadStore = new LoadStore(BitUtils.getBitRange(instruction, LOAD_STORE_RANGE));
        RN = BitUtils.getBitRange(instruction, RN_RANGE);
        RD = BitUtils.getBitRange(instruction, RD_RANGE);
    }
}
