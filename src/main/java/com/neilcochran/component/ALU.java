package com.neilcochran.component;

import com.neilcochran.component.register.ProgramStatusRegister;
import com.neilcochran.component.register.RegisterFile;
import com.neilcochran.instruction.Command;
import com.neilcochran.instruction.OpCodeInstruction;
import com.neilcochran.instruction.field.Shift;
import com.neilcochran.instruction.formatGroup.I.InstructionI;
import com.neilcochran.instruction.formatGroup.R.InstructionR;
import com.neilcochran.instruction.formatGroup.R.command.CMP;
import com.neilcochran.util.BitUtils;
import lombok.Data;

/**
 * Represents an Arithmetic Logic Unit (ALU) which performs all math and logical operations
 */
@Data
public class ALU {
    private final RegisterFile registerFile;

    public void executeInstruction(OpCodeInstruction instruction) {
        Command command = switch (instruction.getInstructionFormat()) {
            case R -> getRCommand((InstructionR) instruction);
            case I -> getICommand((InstructionI) instruction);
            default -> throw new IllegalArgumentException("Invalid instruction format: " + instruction.getInstructionFormat());
        };
        if(command == null) {
            throw new IllegalArgumentException("ALU produced an invalid null command");
        }
        command.executeCommand();
    }

    public static int calculateBarrelShift(InstructionR instructionR, RegisterFile registerFile) {
        int regNum = instructionR.getRM();
        //get the value held in the register pointed to by RM
        int regData = registerFile.getRegister(regNum).getData();
        Shift shift = instructionR.getShift();
        //TODO (?) detect if shift produces a Carry (overflow) and set C flag if it does, otherwise clear C flag
        //OR is the range on the available shift bits always within range? if so no check needed.
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
                PSR.setData(BitUtils.setKthBit(PSR.getData(), ProgramStatusRegister.V_BIT_INDEX));
                //sub uses inverted C bit: set to 0 if overflow
                PSR.setData(BitUtils.clearKthBit(PSR.getData(), ProgramStatusRegister.C_BIT_INDEX));
            }
            else {
                PSR.setData(BitUtils.clearKthBit(PSR.getData(), ProgramStatusRegister.V_BIT_INDEX));
                //sub uses inverted C bit: set to 1 when no overflow
                PSR.setData(BitUtils.setKthBit(PSR.getData(), ProgramStatusRegister.C_BIT_INDEX));
            }
            setNZFlags(res, PSR);
        }
        return res;
    }

    //Set the N and Z flags
    private static void setNZFlags(int data, ProgramStatusRegister PSR) {
        if(data < 0) {
            //set N flag
            PSR.setData(BitUtils.setKthBit(PSR.getData(), ProgramStatusRegister.N_BIT_INDEX));
        }
        else {
            //clear N flag
            PSR.setData(BitUtils.clearKthBit(PSR.getData(), ProgramStatusRegister.N_BIT_INDEX));
            if(data == 0) {
                //set Z flag
                PSR.setData(BitUtils.setKthBit(PSR.getData(), ProgramStatusRegister.Z_BIT_INDEX));
            }
            else {
                //clear Z flag
                PSR.setData(BitUtils.clearKthBit(PSR.getData(), ProgramStatusRegister.Z_BIT_INDEX));
            }
        }
    }

    private Command getRCommand(InstructionR instruction) {
        return switch (instruction.getOpCode()) {
            case CMP -> new CMP(instruction, registerFile);
            default -> throw new IllegalArgumentException("Invalid OpCode name for R Command: " + instruction.getOpCode());
        };
    }

    private Command getICommand(InstructionI instruction) {
        return null;
    }

}
