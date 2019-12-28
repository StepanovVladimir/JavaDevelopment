package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BillTests
{
    @Test
    public void constructorOnPositivePrice()
    {
        Bill bill = new Bill(1);

        assertEquals(1, bill.getPrice());
    }

    @Test
    public void constructorOnNegativePrice()
    {
        assertThrows(IllegalArgumentException.class, () -> new Bill(-1));
    }
}
