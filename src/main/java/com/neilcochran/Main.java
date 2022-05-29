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
        long[] programData = {
            0b11100000100000010000000000000011,
            0b11100000100000010000000000000111,
            0b11100010011010100111000000110001,
            0b11100010011010100111110100011110
        };
        var machine = new VonNeumannMachine();
        machine.loadProgram(programData);
        machine.run();
    }
}
