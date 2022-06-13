package com.neilcochran;

import com.neilcochran.component.VonNeumannMachine;
import com.neilcochran.util.BitUtils;
import com.neilcochran.util.DataSize;

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
            0b00001011100000000000000000000100,
            0b00000000000000000000000000000001,
            0b00000000000000000000000000000010,
            0b00000000000000000000000000000011,
            0b00000000000000000000000000000100,
            0b00000000000000000000000000000101,
            0b00000000000000000000000000000110,
            0b11100000100000010000000000000011
        };
        var machine = new VonNeumannMachine();
        machine.loadProgram(programData);
        machine.run();
    }
}
