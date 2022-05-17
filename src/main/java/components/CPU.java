package components;

import components.registers.*;
import lombok.Data;

@Data
public class CPU {
    //RAM (Random access memory)
    private RAM ram;

    //CU (Control unit)
    private final ControlUnit cu = new ControlUnit();

    //ALU (Arithmetic logic unit)
    private final ALU alu = new ALU();

    //PC (Program Counter) register
    private final ProgramCounter pc = new ProgramCounter();

    //ACC (Accumulator) register
    private final Accumulator acc = new Accumulator();

    //CIR (Current instruction register)
    private final CurrentInstructionRegister cir = new CurrentInstructionRegister();

    //MAR (Memory address register)
    private final MemoryAddressRegister mar = new MemoryAddressRegister();

    //MDR (Memory data register)
    private final MemoryDataRegister mdr = new MemoryDataRegister();

    //4 general purpose registers
    private final Register r0 = new Register("R0");
    private final Register r1 = new Register("R1");
    private final Register r2 = new Register("R2");
    private final Register r3 = new Register("R3");
}
