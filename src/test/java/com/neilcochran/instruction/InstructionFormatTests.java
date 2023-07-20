package com.neilcochran.instruction;

import com.neilcochran.instruction.field.Condition;
import com.neilcochran.instruction.field.InstructionFormat;
import com.neilcochran.instruction.field.OpCode;
import com.neilcochran.instruction.field.ShiftType;
import com.neilcochran.instruction.formatGroup.B.InstructionB;
import com.neilcochran.instruction.formatGroup.D_X.InstructionD;
import com.neilcochran.instruction.formatGroup.D_X.InstructionX;
import com.neilcochran.instruction.formatGroup.R_I.InstructionI;
import com.neilcochran.instruction.formatGroup.R_I.InstructionR;
import com.neilcochran.util.DataSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests for parsing instructions into each of the supported instruction formats (R,I,D,X,B)
 */
public class InstructionFormatTests {

    /**
     * Parse an R format instruction (in this case: CMP) and check that all fields are correctly populated
     */
    @Test
    public void instructionRFormatTest() {
        //AL, R, CMP, 0, 0b111, 0, 0b111, LOG-Right, 0b1001
        var rawCMPInstructionR = 0b11100001010001110000001110101001;
        var instructionR = new InstructionR(rawCMPInstructionR);

        assertEquals(Condition.AL, instructionR.getCondition());
        assertEquals(InstructionFormat.R, instructionR.getInstructionFormat());
        assertEquals(OpCode.CMP, instructionR.getOpCode());
        assertEquals(0, instructionR.getStateFlagBit());
        assertEquals(0b111, instructionR.getRN());
        assertEquals(0b0, instructionR.getRD());
        assertEquals(0b111, instructionR.getShift().getShiftAmountBits());
        assertEquals(ShiftType.LOGICAL_RIGHT, instructionR.getShift().getShiftType());
        assertEquals(0b1001, instructionR.getRM());
    }

    /**
     * Parse an I format instruction (in this case: MOV) and check that all fields are correctly populated
     */
    @Test
    public void instructionIFormatTest() {
        //GE, I, MOV, 0, 0b1010, 0, 0b1111, 0b101010
        var rawMOVInstructionI = 0b10100011101010100000111100101010;
        var instructionI = new InstructionI(rawMOVInstructionI);

        assertEquals(Condition.GE, instructionI.getCondition());
        assertEquals(InstructionFormat.I, instructionI.getInstructionFormat());
        assertEquals(OpCode.MOV, instructionI.getOpCode());
        assertEquals(0, instructionI.getStateFlagBit());
        assertEquals(0b1010, instructionI.getRN());
        assertEquals(0b0, instructionI.getRD());
        assertEquals(0b1111, instructionI.getRotateConstant().getRotate());
        assertEquals(0b101010, instructionI.getRotateConstant().getConstant());
    }

    /**
     * Parse an D format instruction (in this case: LDR) and check that all fields are correctly populated
     */
    @Test
    public void instructionDFormatTest() {
        //EQ, D, 1, 1, BYTE, 1, 0b1001, 0b101, 0b1000, 0b11001100
        var rawLDRInstructionD = 0b00000101110110010101100011001100;
        var instructionD = new InstructionD(rawLDRInstructionD);

        assertEquals(Condition.EQ, instructionD.getCondition());
        assertEquals(InstructionFormat.D, instructionD.getInstructionFormat());
        assertEquals(1, instructionD.getSignBit());
        assertEquals(1, instructionD.getOffsetAddSubBit());
        assertEquals(DataSize.BYTE, instructionD.getDataSize());
        assertEquals(1, instructionD.getLoadStoreBit());
        assertEquals(0b1001, instructionD.getRN());
        assertEquals(0b101, instructionD.getRD());
        assertEquals(0b1000, instructionD.getRotateConstant().getRotate());
        assertEquals(0b11001100, instructionD.getRotateConstant().getConstant());
    }

    /**
     * Parse an X format instruction (in this case: STR) and check that all fields are correctly populated
     */
    @Test
    public void instructionXFormatTest() {
        //NE, X, 1, 0, 0b10, 0b111, 0b10001, ARITH-RIGHT, 0b11
        var rawSTRInstructionX = 0b00010111000000100111100011000011;
        var instructionX = new InstructionX(rawSTRInstructionX);

        assertEquals(Condition.NE, instructionX.getCondition());
        assertEquals(InstructionFormat.X, instructionX.getInstructionFormat());
        assertEquals(1, instructionX.getSignBit());
        assertEquals(0, instructionX.getLoadStoreBit());
        assertEquals(0b10, instructionX.getRN());
        assertEquals(0b111, instructionX.getRD());
        assertEquals(0b10001, instructionX.getShift().getShiftAmountBits());
        assertEquals(ShiftType.ARITHMETIC_RIGHT, instructionX.getShift().getShiftType());
        assertEquals(0b11, instructionX.getRM());
    }

    /**
     * Parse an B format instruction (in this case: B) and check that all fields are correctly populated
     */
    @Test
    public void instructionBFormatTest() {
        //LE, B, 1, 0b100000000000000000000001
        var rawBInstructionB = 0b11011011100000000000000000000001;
        var instructionB = new InstructionB(rawBInstructionB);

        assertEquals(Condition.LE, instructionB.getCondition());
        assertEquals(InstructionFormat.B, instructionB.getInstructionFormat());
        assertEquals(1, instructionB.getLinkRegisterFlagBit());
        assertEquals(0b100000000000000000000001, instructionB.getImm24());
    }
}
