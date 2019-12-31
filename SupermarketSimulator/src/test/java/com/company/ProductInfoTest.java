package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductInfoTest
{
    @Test
    public void constructorSimple()
    {
        ProductInfo productInfo = new ProductInfo("Sugar", ProductInfo.Measurement.Grams, 1);

        assertEquals("Sugar", productInfo.getName());
        assertEquals(ProductInfo.Measurement.Grams, productInfo.getMeasurement());
        assertEquals(1, productInfo.getPrice());
        assertEquals(0, productInfo.getDiscount().getSize());
        assertFalse(productInfo.isAdultsOnly());
    }

    @Test
    public void constructorWithDiscount()
    {
        ProductInfo productInfo = new ProductInfo("Milk", ProductInfo.Measurement.Units, 40, new Discount(20));

        assertEquals("Milk", productInfo.getName());
        assertEquals(ProductInfo.Measurement.Units, productInfo.getMeasurement());
        assertEquals(40, productInfo.getPrice());
        assertEquals(20, productInfo.getDiscount().getSize());
        assertFalse(productInfo.isAdultsOnly());
    }

    @Test
    public void constructorWithIsAdultsOnly()
    {
        ProductInfo productInfo = new ProductInfo("Cigarettes", ProductInfo.Measurement.Units, 40, true);

        assertEquals("Cigarettes", productInfo.getName());
        assertEquals(ProductInfo.Measurement.Units, productInfo.getMeasurement());
        assertEquals(40, productInfo.getPrice());
        assertEquals(0, productInfo.getDiscount().getSize());
        assertTrue(productInfo.isAdultsOnly());
    }

    @Test
    public void constructorOnZeroPrice()
    {
        assertThrows(IllegalArgumentException.class, () -> new ProductInfo("Sugar", ProductInfo.Measurement.Grams, 0));
    }
}
