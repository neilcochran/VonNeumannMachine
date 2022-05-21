package com.neilcochran.components;

import com.neilcochran.components.registers.Accumulator;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an Arithmetic Logic Unit (ALU) which performs all math and logical operations
 */
@Data
@AllArgsConstructor
public class ALU {
    private final Accumulator acc;
}
