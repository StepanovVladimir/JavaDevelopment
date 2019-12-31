package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest
{
    @Test
    public void constructorTests()
    {
        ProductInfo productInfo = new ProductInfo("Sugar", ProductInfo.Measurement.Grams, 1);

        Product product = new Product(productInfo, 6000);

        assertEquals(6000, product.getCount());
        assertEquals(productInfo, product.getInfo());
    }

    @Test
    public void addPositiveProductsCount()
    {
        Product product = new Product(new ProductInfo("Sugar", ProductInfo.Measurement.Grams, 1), 6000);

        product.add(2000);

        assertEquals(8000, product.getCount());
    }

    @Test
    public void addNegativeProductsCount()
    {
        Product product = new Product(new ProductInfo("Sugar", ProductInfo.Measurement.Grams, 1), 6000);

        assertThrows(IllegalArgumentException.class, () -> product.add(-1));
    }

    @Test
    public void removeSeveralProducts()
    {
        Product product = new Product(new ProductInfo("Sugar", ProductInfo.Measurement.Grams, 1), 6000);

        product.remove(2000);

        assertEquals(4000, product.getCount());
    }

    @Test
    public void removeMoreThanThereIs()
    {
        Product product = new Product(new ProductInfo("Sugar", ProductInfo.Measurement.Grams, 1), 6000);

        assertThrows(IllegalArgumentException.class, () -> product.remove(6001));
    }
}
