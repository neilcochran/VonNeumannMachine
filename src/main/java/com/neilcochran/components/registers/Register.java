package com.neilcochran.components.registers;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Represents a Register with a given name
 */
@Data
@RequiredArgsConstructor
public class Register {

    /**
     * The name of the Register
     */
    protected final String name;

    /**
     * The data in the Register
     */
    protected int data = 0b0;
}
