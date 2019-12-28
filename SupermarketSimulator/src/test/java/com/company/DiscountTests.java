package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountTests
{
    @Test
    public void constructorOnZeroPercent()
    {
        Discount discount = new Discount(0);

        assertEquals(0, discount.getSize());
    }

    @Test
    public void constructorOn100Percent()
    {
        Discount discount = new Discount(100);

        assertEquals(100, discount.getSize());
    }

    @Test
    public void constructorOnNegativePercent()
    {
        assertThrows(IllegalArgumentException.class, () -> new Discount(-1));
    }

    @Test
    public void constructorOnMoreThan100Percent()
    {
        assertThrows(IllegalArgumentException.class, () -> new Discount(101));
    }
}
