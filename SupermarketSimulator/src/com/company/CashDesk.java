package com.company;

import java.util.ArrayDeque;
import java.util.Queue;

public class CashDesk
{
    public CashDesk()
    {
        customers = new ArrayDeque<>();
    }

    public int customersCount()
    {
        return customers.size();
    }

    public void addCustomer(Customer customer)
    {
        customers.add(customer);
    }

    public Bill serveCustomer()
    {
        if (customers.isEmpty())
        {
            throw new RuntimeException("There are no customers");
        }

        Customer customer = customers.remove();
        int totalPrice = customer.getTotalMoneyAmount() - customer.getRemainingMoneyAmount();

        return new Bill(totalPrice);
    }

    private Queue<Customer> customers;
}