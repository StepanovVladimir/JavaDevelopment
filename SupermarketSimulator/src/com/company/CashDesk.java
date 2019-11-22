package com.company;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
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

    public Bill serveCustomer(Date time)
    {
        if (customers.isEmpty())
        {
            throw new RuntimeException("There are no customers");
        }

        Customer customer = customers.remove();
        int totalPrice = customer.getTotalMoneyAmount() - customer.getRemainingMoneyAmount();

        System.out.print(format.format(time) + " Customer '" + customer.getName());
        System.out.println("' at the cash desk, amount to pay: " + totalPrice);

        return new Bill(totalPrice);
    }

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    private Queue<Customer> customers;
}