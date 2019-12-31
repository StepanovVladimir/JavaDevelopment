package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main
{
    private static final int HOUR = 1000 * 60 * 60;

    public static void main(String[] args)
    {
        Supermarket supermarket = new Supermarket();
        Random random = new Random();
        Date time = new Date(HOUR * 3);

        initProducts(supermarket, random, time);
        runSupermarket(supermarket, random, time);
    }

    public static void initProducts(Supermarket supermarket, Random random, Date time)
    {
        ArrayList<Product> products = supermarket.getProducts();
        products.add(new Product(new ProductInfo("milk", ProductInfo.Measurement.Units, 50, new Discount(20)), random.nextInt(100)));
        products.add(new Product(new ProductInfo("sugar", ProductInfo.Measurement.Grams, 1, new Discount(20)), random.nextInt(20000)));
        products.add(new Product(new ProductInfo("butter", ProductInfo.Measurement.Grams, 10, new Discount(20)), random.nextInt(1000)));
        products.add(new Product(new ProductInfo("cigarettes", ProductInfo.Measurement.Units, 40, true), random.nextInt(100)));

        addTime(time, random.nextInt(HOUR));
        supermarket.logProducts(time);
    }

    public static void runSupermarket(Supermarket supermarket, Random random, Date time)
    {
        addTime(time, random.nextInt(HOUR));
        supermarket.open(time);
        CashDesk cashDesk = supermarket.getCashDesk();
        while (time.getTime() < HOUR * 15 || supermarket.getCustomersCount() > 0 || cashDesk.getCustomersCount() > 0)
        {
            int actionNumber;
            if (time.getTime() < HOUR * 14)
            {
                actionNumber = random.nextInt(4);
            }
            else
            {
                actionNumber = random.nextInt(3) + 1;
            }

            switch (actionNumber)
            {
                case 0:
                    addCustomerToSupermarket(supermarket, random, time);
                    break;

                case 1:
                    putProductInBasket(supermarket, random, time);
                    break;

                case 2:
                    addCustomerToCashDesk(supermarket, random, time);
                    break;

                case 3:
                    serveCustomerAtCashDesk(supermarket, random, time);
                    break;
            }
        }

        supermarket.close(time);
    }

    public static void addCustomerToSupermarket(Supermarket supermarket, Random random, Date time)
    {
        addTime(time, random.nextInt(HOUR));
        ArrayList<Product> products = supermarket.getProducts();

        int category = random.nextInt(3);
        int cashAmount = random.nextInt(10000) + 100;
        int cardMoneyAmount = random.nextInt(50000);
        int bonusesAmount = random.nextInt(1000);

        Customer customer = new Customer(Customer.Category.values()[category], cashAmount, cardMoneyAmount, bonusesAmount, products);
        supermarket.addCustomer(customer, time);
    }

    public static void putProductInBasket(Supermarket supermarket, Random random, Date time)
    {
        try
        {
            if (supermarket.getCustomersCount() > 0)
            {
                addTime(time, random.nextInt(HOUR));
                ArrayList<Product> products = supermarket.getProducts();

                int customerIndex = random.nextInt(supermarket.getCustomersCount());
                int productIndex = random.nextInt(products.size());
                int count = random.nextInt(products.get(productIndex).getCount() + 1) + 1;

                supermarket.getCustomer(customerIndex).putInBasket(productIndex, count, time);
            }
        }
        catch (IllegalArgumentException exc)
        {
            System.out.println(exc.getMessage());
        }
    }

    public static void addCustomerToCashDesk(Supermarket supermarket, Random random, Date time)
    {
        if (supermarket.getCustomersCount() > 0)
        {
            addTime(time, random.nextInt(HOUR));
            CashDesk cashDesk = supermarket.getCashDesk();
            int customerIndex = random.nextInt(supermarket.getCustomersCount());
            cashDesk.addCustomer(supermarket.getCustomer(customerIndex));
            supermarket.removeCustomer(customerIndex);
        }
    }

    public static void serveCustomerAtCashDesk(Supermarket supermarket, Random random, Date time)
    {
        CashDesk cashDesk = supermarket.getCashDesk();
        if (cashDesk.getCustomersCount() > 0)
        {
            addTime(time, random.nextInt(HOUR));
            Bill bill = cashDesk.serveCustomer(time);
            cashDesk.getCustomer().payBill(bill, time);
            cashDesk.removeCustomer();
        }
    }

    public static void addTime(Date time, int adding)
    {
        time.setTime(time.getTime() + adding);
    }
}