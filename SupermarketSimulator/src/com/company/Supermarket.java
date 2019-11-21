package com.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Supermarket
{
    public ArrayList<Product> getProducts()
    {
        return products;
    }

    public int getCustomersCount()
    {
        return customers.size();
    }

    public Customer getCustomer(int i)
    {
        return customers.get(i);
    }

    public void addCustomer(Customer customer, Date time)
    {
        if (!isOpened)
        {
            throw new RuntimeException("Supermarket is closed");
        }

        customers.add(customer);
        System.out.println(format.format(time) + " Customer '" + customer.getName() + "' arrived");
        customer.log();
    }

    public void removeCustomer(int i)
    {
        customers.remove(i);
    }

    public CashDesk getCashDesk()
    {
        return cashDesk;
    }

    public void logProducts(Date time)
    {
        System.out.println(format.format(time) + " Supermarket products have been formed");
        for (Product product : products)
        {
            product.log();
        }
    }

    public boolean isOpened()
    {
        return isOpened;
    }

    public void open(Date time)
    {
        if (isOpened)
        {
            throw new RuntimeException("Supermarket is already open");
        }
        isOpened = true;
        System.out.println(format.format(time) + " Supermarket is opened");
    }

    public void close(Date time)
    {
        if (!isOpened)
        {
            throw new RuntimeException("Supermarket is already close");
        }
        if (!customers.isEmpty())
        {
            throw new RuntimeException("There are still customers in the supermarket");
        }

        isOpened = false;
        System.out.println(format.format(time) + " Supermarket is closed");
    }

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    private boolean isOpened;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private CashDesk cashDesk = new CashDesk();
}