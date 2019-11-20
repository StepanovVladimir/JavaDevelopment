package com.company;

import java.util.ArrayList;

public class Supermarket
{
    public ArrayList<Product> getProducts()
    {
        return products;
    }

    public ArrayList<Customer> getCustomers()
    {
        return customers;
    }

    public CashDesk getCashDesk()
    {
        return cashDesk;
    }

    private ArrayList<Product> products;
    private ArrayList<Customer> customers;
    private CashDesk cashDesk;
}