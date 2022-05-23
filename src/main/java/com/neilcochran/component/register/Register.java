package com.neilcochran.component.register;

import lombok.Data;

import org.json.JSONObject;

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

    /**
     * Returns a JSON string representation of the Register
     * @return A JSON string representation of the Register
     */
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.putOpt("alias", alias);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }
}
