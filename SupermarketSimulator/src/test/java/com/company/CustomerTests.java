package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTests
{
    @BeforeEach
    public void init()
    {
        products = new ArrayList<>();
        products.add(new Product(new ProductInfo("Sugar", ProductInfo.Measurement.Grams, 1), 6000));
        products.add(new Product(new ProductInfo("Milk", ProductInfo.Measurement.Units, 40, new Discount(20)), 20));
        products.add(new Product(new ProductInfo("Cigarettes", ProductInfo.Measurement.Units, 40, new Discount(10), true), 20));
    }

    @Test
    public void constructorTest()
    {
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, products);

        assertEquals(Customer.Category.Adult, customer.getCategory());
        assertEquals(500, customer.getCashAmount());
        assertEquals(5000, customer.getCardMoneyAmount());
        assertEquals(50, customer.getBonusesAmount());
        assertTrue(customer.getBasket().isEmpty());
        assertEquals(5550, customer.getRemainingMoneyAmount());
        assertEquals(5550, customer.getTotalMoneyAmount());
        assertEquals("customer#", customer.getName().substring(0, 9));
    }

    @Test
    public void putInBasketTest()
    {
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, products);

        customer.putInBasket(0, 200, new Date());

        assertEquals(5350, customer.getRemainingMoneyAmount());
        assertEquals(5800, products.get(0).getCount());
    }

    @Test
    public void retiredPutInBasket()
    {
        Customer customer = new Customer(Customer.Category.Retired, 500, 0, 0, products);

        customer.putInBasket(1, 1, new Date());

        assertEquals(468, customer.getRemainingMoneyAmount());
    }

    @Test
    public void childPutInBasketProductAdultsOnly()
    {
        Customer customer = new Customer(Customer.Category.Child, 100, 0, 0, products);

        assertThrows(IllegalArgumentException.class, () -> customer.putInBasket(2, 1, new Date()));
    }

    @Test
    public void putInBasketMoreThanThereIs()
    {
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, products);

        assertThrows(IllegalArgumentException.class, () -> customer.putInBasket(1, 21, new Date()));
    }

    @Test
    public void putInBasketWhenNotEnoughMoney()
    {
        Customer customer = new Customer(Customer.Category.Adult, 50, 0, 0, products);

        assertThrows(IllegalArgumentException.class, () -> customer.putInBasket(0, 51, new Date()));
    }

    private ArrayList<Product> products;
}
