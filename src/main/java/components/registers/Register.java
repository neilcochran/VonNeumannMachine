package components.registers;

import lombok.Data;

@Data
public class Register {
    protected String name;
    protected int data;

    public Register(String name) {
        this.name = name;
        this.data = 0b0;
    }
}
