package com.neilcochran.component.register;

/**
 * Represents a Register with a given name
 */
public class Register {

    /**
     * The name of the register
     */
    protected final String name;

    /**
     * An alias for the register (for instance: 'LR' is the alias for the 'Link Register')
     */
    protected final String alias;

    //TODO have setData use bit logic like Memory#storeData()
    /**
     * The data value held in the register
     */
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


    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
