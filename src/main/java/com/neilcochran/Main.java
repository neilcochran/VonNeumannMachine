package com.neilcochran;

import com.neilcochran.component.VonNeumannMachine;

/**
 * Create and run a VonNeumannMachine
 */
public class Main {

    /**
     * Create and run a VonNeumannMachine
     * @param args Main args are currently not used
     */
    public static void main(String[] args) {
        int[] programData = {
            //Format D
            //LDR R1 R0 + #256
            //  AL    D  S U DS L RN   RD   Imm12
            //0b1110 010 1 0 11 1 0000 0001 000000001000,
            0b11100101011100000001000000001000,
            0b00000000000000000000000000011111
        };
        var machine = new VonNeumannMachine();
        machine.loadProgram(programData);
        machine.run();
    }
}
