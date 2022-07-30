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
         //   AL    D  S U DS L RN   RD   Imm12
         // 0b1110 010 1 0 11 1 0000 0111 000000000111, //LDRB R7 R0 + 7
            0b11100101011000000111000000000101, //STRB R7 R0 + 7
            0b00000000000000000000001100000000
            //0b00000100000000110000001000000001 //1@4 2@5 3@6 4@7
        };
        var machine = new VonNeumannMachine();
        machine.loadProgram(programData);
        machine.run();
    }
}
