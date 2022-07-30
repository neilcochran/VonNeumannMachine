package com.neilcochran.component;

import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.OpCodeInstruction;
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
    private final ProgramStatusRegister PSR;

    /**
     * Executes the given instruction command
     * @param instruction The OpCodeInstruction whose command is to be executed
     */
    public void executeInstruction(OpCodeInstruction instruction) {
        getCommand(instruction).executeCommand();
    }

    /**
     * Add x and y. If the PSR parameter is not null, any relevant condition flags will be set
     * @param x The first integer to be added
     * @param y The second integer to be added
     * @param PSR The ProgramStatusRegister which will have relevant condition flags set (if it is provided)
     * @return The result of x + y
     */
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

    /**
     * Subtract x and y. If the PSR parameter is not null, any relevant condition flags will be set.
     * Note: Like ARM, this uses an "inverted carry flag" for subtraction. This means C is set to 0 if the subtraction produced unsigned underflow, and to 1 otherwise
     * @param x The integer to be subtracted from
     * @param y The integer to be subtracted
     * @param PSR The ProgramStatusRegister which will have relevant condition flags set (if it is provided)
     * @return The result of x - y
     */
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

    /**
     * For the given integer data, set the PSR N and Z flags
     * @param data The integer to be evaluated
     * @param PSR A reference to the PSR (Program Status Register)
     */
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

    /**
     * For a given OpCodeInstruction retrieve its Command
     * @param instruction The instruction whose Command is to be retrieved
     * @return The Command associated with the given instruction
     */
    private Command getCommand(OpCodeInstruction instruction) {
        return switch (instruction.getOpCode()) {
            case CMP -> new CMP(instruction, registerFile, PSR);
            case CMN -> new CMN(instruction, registerFile, PSR);
            case MOV -> new MOV(instruction, registerFile, PSR);
            case MVN -> new MVN(instruction, registerFile, PSR);
            default -> throw new IllegalArgumentException("Invalid OpCode name for R Command: " + instruction.getOpCode());
        };
    }
}
