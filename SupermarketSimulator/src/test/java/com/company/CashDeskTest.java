package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CashDeskTest
{
    @BeforeEach
    public void init()
    {
        products = new ArrayList<>();
        products.add(new Product(new ProductInfo("Sugar", ProductInfo.Measurement.Grams, 1), 6000));
        products.add(new Product(new ProductInfo("Milk", ProductInfo.Measurement.Units, 40, new Discount(20)), 20));
        products.add(new Product(new ProductInfo("Cigarettes", ProductInfo.Measurement.Units, 40, true), 20));
    }

    @Test
    public void constructorTest()
    {
        CashDesk cashDesk = new CashDesk(new Report());

        assertEquals(0, cashDesk.getCustomersCount());
    }

    @Test
    public void addCustomerTest()
    {
        CashDesk cashDesk = new CashDesk(new Report());
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, products);

        cashDesk.addCustomer(customer);

        assertEquals(customer, cashDesk.getCustomer());
    }

    @Test
    public void removeCustomer()
    {
        CashDesk cashDesk = new CashDesk(new Report());
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, products);
        cashDesk.addCustomer(customer);

        cashDesk.removeCustomer();

        assertEquals(0, cashDesk.getCustomersCount());
    }

    @Test
    public void serveCustomerWithoutProducts()
    {
        CashDesk cashDesk = new CashDesk(new Report());
        Customer customer = new Customer(Customer.Category.Adult, 500, 5000, 50, products);
        cashDesk.addCustomer(customer);

        Bill bill = cashDesk.serveCustomer(new Date());
        customer.payBill(bill, new Date());

        assertEquals(0, bill.getPrice());
    }

    @Test
    public void serveCustomerWithProducts()
    {
        CashDesk cashDesk = new CashDesk(new Report());
        Customer customer = new Customer(Customer.Category.Adult, 200, 50, 10, products);
        customer.putInBasket(0, 200, new Date());
        cashDesk.addCustomer(customer);

        Bill bill = cashDesk.serveCustomer(new Date());
        customer.payBill(bill, new Date());

        assertEquals(200, bill.getPrice());
    }

    private ArrayList<Product> products;
}