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
            0b00001011100000000000000000000001,
            0b00000000000000000000000000000000,
            0b00000001010000000000000000000001,
        };
        var machine = new VonNeumannMachine();
        machine.loadProgram(programData);
        machine.run();
    }
}
