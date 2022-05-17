package components.registers;

public class ProgramCounter extends Register {

    public ProgramCounter() {
        super("PC");
    }

    public void increment() {
        this.data += 0b1;
    }
}
