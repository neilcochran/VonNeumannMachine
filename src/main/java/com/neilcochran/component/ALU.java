package com.neilcochran.component;

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

    public static int calculateBarrelShift(InstructionR instructionR) {
        int RM = instructionR.getRM();
        Shift shift = instructionR.getShift();
        return switch (shift.getShiftType()) {
            case LOGICAL_LEFT -> RM << shift.getShiftAmountBits();
            case LOGICAL_RIGHT -> RM >> shift.getShiftAmountBits();
            case ARITHMETIC_RIGHT -> RM >>> shift.getShiftAmountBits();
            case ROTATE_RIGHT -> BitUtils.calculateRotate(shift.getShiftAmountBits(), RM);
        };
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
