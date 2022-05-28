package com.neilcochran.component.register;

import lombok.Data;

/**
 * Represents a Register with a given name
 */
@Data
public class Register {

    protected final String name;
    protected final String alias;
    protected int data = 0b0;

    /**
     * Create a Register with a name but no alias
     * @param name The name of the register (Ex: R0, R1, ect)
     */
    public Register(String name) {
        this.name = name;
        this.alias = null;
    }

    /**
     * Create a Register with a name and alias
     * @param name The name of the register (Ex: R0, R1, ect)
     * @param alias The alias for the register (Ex: PC or ProgramCounter)
     */
    public Register(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }
}
