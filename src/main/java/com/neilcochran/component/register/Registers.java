package com.neilcochran.component.register;

import lombok.Data;

/**
 * Represents all (16) of the Register objects available to the machine as well as the CPSR
 * This includes special use registers (Like the PC, LR, FP) and all general use registers
 */
@Data
public class Registers {

    /**
     * The total amount of registers (Note: this excludes the CPSR)
     * @see Registers#CPSR
     */
    private static final int TOTAL_GENERAL_REGISTERS = 16;

    /**
     * The index of the Frame Pointer (FP) register
     */
    private static final int FP_REG_INDEX = 11;

    /**
     * The index of the Intra Procedural Call (IP) register
     */
    private static final int IP_REG_INDEX = 12;

    /**
     * The index of the Stack Pointer (SP) register
     */
    private static final int SP_REG_INDEX = 13;

    /**
     * The index of the Link Register (LR)
     */
    private static final int LR_REG_INDEX = 14;

    /**
     * The index of the Program Counter (PC) register
     */
    private static final int PC_REG_INDEX = 15;

    /**
     * Current Program State Register CPSR is a special (non-indexed) register for holding different state/status flags
     */
    private static final Register CPSR = new CurrentProgramStatusRegister();

    private final Register[] registers;

    /**
     * Constructs a new Registers object that holds all the Register objects available to the machine
     * This includes special use registers and general use registers
     */
    public Registers() {
        registers = new Register[TOTAL_GENERAL_REGISTERS];
        for(var i = 0; i < TOTAL_GENERAL_REGISTERS; i++) {
            String alias = null;
            //Determine if we are creating a known "special use" register and if so, give it the appropriate alias
            switch (i) {
                case FP_REG_INDEX -> alias = "FP";
                case IP_REG_INDEX -> alias = "IP";
                case SP_REG_INDEX -> alias = "SP";
                case LR_REG_INDEX -> alias = "LR";
                case PC_REG_INDEX -> alias = "PC";
            }
            registers[i] = new Register("R" + i, alias);
        }
    }

    /**
     * A convenience method for getting a reference to the Frame Pointer (FP) register
     * @return A reference to the Frame Pointer register
     * @see Registers#FP_REG_INDEX
     */
    public Register getFramePointerRegister() {
        return getRegister(FP_REG_INDEX);
    }

    /**
     * A convenience method for getting a reference to the Intra Procedural Call (IP) register
     * @return A reference to the Intra Procedural Call register
     * @see Registers#IP_REG_INDEX
     */
    public Register getIntraProceduralCallRegister() {
        return getRegister(IP_REG_INDEX);
    }

    /**
     * A convenience method for getting a reference to the Stack Pointer (SP) register
     * @return A reference to the Stack Pointer register
     * @see Registers#SP_REG_INDEX
     */
    public Register getStackPointerRegister() {
        return getRegister(SP_REG_INDEX);
    }

    /**
     * A convenience method for getting a reference to the Link Register (LR)
     * @return A reference to the Link Register
     * @see Registers#LR_REG_INDEX
     */
    public Register getLinkRegister() {
        return getRegister(LR_REG_INDEX);
    }


    /**
     * A convenience method for getting a reference to the Program Counter (PC) register
     * @return A reference to the Program Counter register
     * @see Registers#PC_REG_INDEX
     */
    public Register getPCRegister() {
        return getRegister(PC_REG_INDEX);
    }

    /**
     * Get a reference to the register at the given index
     * @param registerNumber The index of the register to retrieve
     * @return The register at the given index
     * @throws IllegalArgumentException if the index requested is invalid
     */
    public Register getRegister(int registerNumber) {
        if(registerNumber < 0 || registerNumber >= TOTAL_GENERAL_REGISTERS) {
            throw new IllegalArgumentException(String.format("Invalid register number: %d requested", registerNumber));
        }
        return registers[registerNumber];
    }

    /**
     * Increments the current value in the Program Counter (PC) by one full word (8 bytes)
     * @return The new (post increment) address value of the Program Counter
     */
    public int incrementProgramCounter() {
        var incremented = getPCRegister().getData() + 0b1000;
        getPCRegister().setData(incremented);
        return incremented;
    }

    /**
     * Returns a JSON string representation of the Registers
     * @return A JSON string representation of the Registers
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{\n");
        builder.append("\t\"registers\": [\n");
        for(var i = 0; i < registers.length; i++) {
            builder.append("\t\t")
                    .append(registers[i].toString())
                    //fence post - avoid trailing comma
                    .append(i == registers.length - 1 ? "\n" : ",\n");
        }
        builder.append("\t]\n}");
        return builder.toString();
    }
}
