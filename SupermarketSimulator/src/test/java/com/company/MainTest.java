package com.company;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest
{
    @Test
    public void initProductsTest()
    {
        Supermarket supermarket = new Supermarket();
        Random random = new Random();
        Date time = new Date();

        Main.initProducts(supermarket, random, time);

        assertEquals(4, supermarket.getProducts().size());
    }

    @Test
    public void addCustomerToSupermarketTest()
    {
        Supermarket supermarket = new Supermarket();
        Random random = new Random();
        Date time = new Date();
        supermarket.open(time);

        Main.addCustomerToSupermarket(supermarket, random, time);

        assertEquals(1, supermarket.getCustomersCount());
    }
}
