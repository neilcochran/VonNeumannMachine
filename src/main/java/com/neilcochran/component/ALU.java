package com.neilcochran.component;

import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.field.Shift;
import com.neilcochran.instruction.formatGroup.R_I.InstructionR;
import com.neilcochran.instruction.formatGroup.R_I.command.CMN;
import com.neilcochran.instruction.formatGroup.R_I.command.CMP;
import com.neilcochran.instruction.formatGroup.R_I.command.MOV;
import com.neilcochran.instruction.formatGroup.R_I.command.MVN;
import com.neilcochran.util.BitUtils;
import lombok.Data;

/**
 * Represents an Arithmetic Logic Unit (ALU) which performs all math and logical operations
 */
@Data
public class ALU {
    private final RegisterFile registerFile;

    public void executeInstruction(OpCodeInstruction instruction) {
        getCommand(instruction).executeCommand();
    }

    public static int calculateBarrelShift(InstructionR instructionR, RegisterFile registerFile) {
        int regNum = instructionR.getRM();
        //get the value held in the register pointed to by RM
        int regData = registerFile.getRegister(regNum).getData();
        Shift shift = instructionR.getShift();
        //TODO Can update the C flag during the shift calculation
        return switch (shift.getShiftType()) {
            case LOGICAL_LEFT -> regData << shift.getShiftAmountBits();
            case LOGICAL_RIGHT -> regData >> shift.getShiftAmountBits();
            case ARITHMETIC_RIGHT -> regData >>> shift.getShiftAmountBits();
            case ROTATE_RIGHT -> BitUtils.calculateRotate(shift.getShiftAmountBits(), regData);
        };
    }

    public static int add(int x, int y, ProgramStatusRegister PSR) {
        int res = x + y;
        if(PSR != null) {
            //when both x & y have the opposite sign of the result then an overflow has occurred
            if (((x ^ res) & (y ^ res)) < 0) {
                //set overflow flag
                PSR.setData(BitUtils.setKthBit(PSR.getData(), ProgramStatusRegister.V_BIT_INDEX));
                //set carry flag
                PSR.setData(BitUtils.setKthBit(PSR.getData(), ProgramStatusRegister.C_BIT_INDEX));
            }
            else {
                //clear overflow flag
                PSR.setData(BitUtils.clearKthBit(PSR.getData(), ProgramStatusRegister.V_BIT_INDEX));
                //clear carry flag
                PSR.setData(BitUtils.clearKthBit(PSR.getData(), ProgramStatusRegister.C_BIT_INDEX));
            }
            setNZFlags(res, PSR);
        }
        return res;
    }

    //Like ARM use inverted carry flag for subtraction C is set to 0 if the subtraction produced unsigned underflow, and to 1 otherwise
    public static int subtract(int x, int y, ProgramStatusRegister PSR) {
        int res = x - y;
        if(PSR != null) {
            //when x & y have different signs and the sign of the result is different from the sign of x then an
            //overflow has occurred
            if (((x ^ y) & (x ^ res)) < 0) {
                PSR.setVBit();
                //sub uses inverted C bit: set to 0 if overflow
                PSR.clearCBit();
            }
            else {
                PSR.clearVBit();
                //sub uses inverted C bit: set to 1 when no overflow
                PSR.setCBit();
            }
            setNZFlags(res, PSR);
        }
        return res;
    }

    //Set the N and Z flags
    public static void setNZFlags(int data, ProgramStatusRegister PSR) {
        if(data < 0) {
            //set N flag
            PSR.setNBit();
        }
        else {
            //clear N flag
            PSR.clearNBit();
            if(data == 0) {
                //set Z flag
                PSR.setZBit();
            }
            else {
                //clear Z flag
                PSR.clearZBit();
            }
        }
    }

    private Command getCommand(OpCodeInstruction instruction) {
        return switch (instruction.getOpCode()) {
            case CMP -> new CMP(instruction, registerFile);
            case CMN -> new CMN(instruction, registerFile);
            case MOV -> new MOV(instruction, registerFile);
            case MVN -> new MVN(instruction, registerFile);
            default -> throw new IllegalArgumentException("Invalid OpCode name for R Command: " + instruction.getOpCode());
        };
    }
}
