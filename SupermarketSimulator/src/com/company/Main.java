package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main
{
    private static final int hour = 1000 * 60 * 60;

    public static void main(String[] args)
    {
        Supermarket supermarket = new Supermarket();
        ArrayList<Product> products = supermarket.getProducts();
        products.add(new Product(new ProductInfo("milk", ProductInfo.Measurement.Units, 50, new Discount(20)), 30));
        products.add(new Product(new ProductInfo("sugar", ProductInfo.Measurement.Grams, 1, new Discount(20)), 20000));
        products.add(new Product(new ProductInfo("butter", ProductInfo.Measurement.Grams, 10, new Discount(20)), 1000));
        products.add(new Product(new ProductInfo("cigarettes", ProductInfo.Measurement.Units, 40, true), 50));

        Random random = new Random();
        Date time = new Date(hour * 3);
        addTime(time, random.nextInt(hour));

        supermarket.logProducts(time);

        addTime(time, random.nextInt(hour));
        supermarket.open(time);
        CashDesk cashDesk = supermarket.getCashDesk();

        while (time.getTime() < hour * 15 || supermarket.getCustomersCount() > 0)
        {
            int actionNumber;
            if (time.getTime() < hour * 15)
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
                    addTime(time, random.nextInt(hour));

                    int category = random.nextInt(3);
                    int cashAmount = random.nextInt(10000) + 100;
                    int cardMoneyAmount = random.nextInt(50000);
                    int bonusesAmount = random.nextInt(1000);

                    Customer customer = new Customer(Customer.Category.values()[category], cashAmount, cardMoneyAmount, bonusesAmount, products);
                    supermarket.addCustomer(customer, time);
                    break;

                case 1:
                    try
                    {
                        if (supermarket.getCustomersCount() > 0)
                        {
                            addTime(time, random.nextInt(hour));

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
                    break;

                case 2:
                    if (supermarket.getCustomersCount() > 0)
                    {
                        addTime(time, random.nextInt(hour));
                        int customerIndex = random.nextInt(supermarket.getCustomersCount());
                        cashDesk.addCustomer(supermarket.getCustomer(customerIndex));
                        supermarket.removeCustomer(customerIndex);
                    }
                    break;

                case 3:
                    if (cashDesk.getCustomersCount() > 0)
                    {
                        addTime(time, random.nextInt(hour));
                        Bill bill = cashDesk.serveCustomer(time);
                        cashDesk.getCustomer().payBill(bill, time);
                        cashDesk.removeCustomer();
                    }
                    break;
            }
        }

        supermarket.close(time);
    }

    private static void addTime(Date time, int adding)
    {
        time.setTime(time.getTime() + adding);
    }
}