package com.company;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

public class CashDesk
{
    public CashDesk(Report report)
    {
        this.report = report;
    }

    public int getCustomersCount()
    {
        return customers.size();
    }

    public Customer getCustomer()
    {
        return customers.element();
    }

    public void addCustomer(Customer customer)
    {
        customers.add(customer);
    }

    public void removeCustomer()
    {
        customers.remove();
    }

    public Bill serveCustomer(Date time)
    {
        if (customers.isEmpty())
        {
            throw new RuntimeException("There are no customers");
        }

        Customer customer = customers.element();
        int totalPrice = customer.getTotalMoneyAmount() - customer.getRemainingMoneyAmount();

        System.out.print(format.format(time) + " Customer '" + customer.getName());
        System.out.println("' at the cash desk, amount to pay: " + totalPrice);

        Basket basket = customer.getBasket();
        for (Product product : basket)
        {
            report.addProducts(product.getInfo(), product.getCount());
        }

        return new Bill(totalPrice);
    }

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    private Queue<Customer> customers = new ArrayDeque<>();
    private Report report;
}