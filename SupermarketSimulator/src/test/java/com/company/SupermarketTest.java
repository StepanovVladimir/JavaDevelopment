package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SupermarketTest
{
    @BeforeEach
    public void init()
    {
        supermarket = new Supermarket();
        ArrayList<Product> products = supermarket.getProducts();
        products.add(new Product(new ProductInfo("milk", ProductInfo.Measurement.Units, 50, new Discount(20)), 100));
        products.add(new Product(new ProductInfo("sugar", ProductInfo.Measurement.Grams, 1, new Discount(20)), 20000));
        products.add(new Product(new ProductInfo("butter", ProductInfo.Measurement.Grams, 10, new Discount(20)), 1000));
        products.add(new Product(new ProductInfo("cigarettes", ProductInfo.Measurement.Units, 40, true), 100));
    }

    @Test
    public void constructorTest()
    {
        assertEquals(0, supermarket.getCustomersCount());
        assertEquals(0, supermarket.getCashDesk().getCustomersCount());
        assertFalse(supermarket.isOpened());
    }

    @Test
    public void addCustomerWhenSupermarketIsClosed()
    {
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, supermarket.getProducts());

        assertThrows(RuntimeException.class, () -> supermarket.addCustomer(customer, new Date()));
    }

    @Test
    public void openTest()
    {
        Supermarket supermarket = new Supermarket();

        supermarket.open(new Date());

        assertTrue(supermarket.isOpened());
    }

    @Test
    public void openTwice()
    {
        supermarket.open(new Date());

        assertThrows(RuntimeException.class, () -> supermarket.open(new Date()));
    }

    @Test
    public void addCustomerWhenSupermarketIsOpened()
    {
        supermarket.open(new Date());
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, supermarket.getProducts());

        supermarket.addCustomer(customer, new Date());

        assertEquals(customer, supermarket.getCustomer(0));
    }

    @Test
    public void removeCustomer()
    {
        supermarket.open(new Date());
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, supermarket.getProducts());
        supermarket.addCustomer(customer, new Date());

        supermarket.removeCustomer(0);

        assertEquals(0, supermarket.getCustomersCount());
    }

    @Test
    public void closeWhenIsClosed()
    {
        assertThrows(RuntimeException.class, () -> supermarket.close(new Date()));
    }

    @Test
    public void closeWhenSupermarketNotEmpty()
    {
        supermarket.open(new Date());
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, supermarket.getProducts());
        supermarket.addCustomer(customer, new Date());

        assertThrows(RuntimeException.class, () -> supermarket.close(new Date()));
    }

    @Test
    public void closeTest()
    {
        supermarket.open(new Date());
        CashDesk cashDesk = supermarket.getCashDesk();
        Customer customer = new Customer(Customer.Category.Adult, 200, 50, 10, supermarket.getProducts());
        customer.putInBasket(0, 2, new Date());
        cashDesk.addCustomer(customer);
        Bill bill = cashDesk.serveCustomer(new Date());
        customer.payBill(bill, new Date());

        supermarket.close(new Date());

        assertFalse(supermarket.isOpened());
    }

    private Supermarket supermarket;
}
