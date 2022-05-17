import components.CPU;
import components.RAM;
import lombok.Data;

@Data
public class VonNeumannMachine {
    //16 bit word size
    private static final int WORD_SIZE = 16;

    private final CPU cpu = new CPU();

    private final RAM ram;

    /**
     * Creates a 16 bit "Von Neumann Machine" with 65536 bytes (64k) of RAM
     */
    public VonNeumannMachine() {
        this.ram = new RAM(65536, WORD_SIZE);
    }

    /**
     * Creates a 16 bit "Von Neumann Machine" with the indicated amount of RAM
     * @param bytesRAM - The amount (bytes) of RAM to be allocated to the machine
     */
    public VonNeumannMachine(int bytesRAM) {
        this.ram = new RAM(bytesRAM, WORD_SIZE);
    }


    /**
     * Start running the Fetch -> Decode -> Execute loop
     */
    public void run() {
        //Fetch -> Decode -> Execute loop
    }
}
