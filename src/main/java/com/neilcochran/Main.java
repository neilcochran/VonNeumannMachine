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
                //MVNS R1, #1
                0b11100011111100000001000000000001
        };
        var machine = new VonNeumannMachine();
        machine.loadProgram(programData);
        machine.run();
    }
}
