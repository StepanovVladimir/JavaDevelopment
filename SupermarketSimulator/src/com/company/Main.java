package com.company;

import java.util.ArrayList;
import java.util.Date;

public class Main
{
    public static void main(String[] args)
    {
        Date time = new Date(1000 * 60 * 60 * 4);
        Supermarket supermarket = new Supermarket();
        ArrayList<Product> products = supermarket.getProducts();
        products.add(new Product(new ProductInfo("milk", ProductInfo.Measurement.Units, 50), 30));
        products.add(new Product(new ProductInfo("sugar", ProductInfo.Measurement.Grams, 1), 20000));
        products.add(new Product(new ProductInfo("butter", ProductInfo.Measurement.Grams, 10), 1000));
        supermarket.logProducts(time);
        time.setTime(1000 * 60 * 60 * 5);
        supermarket.open(time);
        supermarket.addCustomer(new Customer(Customer.Category.Adult, 300, 3000, 30, products), time);

        try
        {
            supermarket.getCustomer(0).putInBasket(0, 2, time);
        }
        catch (IllegalArgumentException exc)
        {
            System.out.println(exc.getMessage());
        }

        CashDesk cashDesk = supermarket.getCashDesk();
        cashDesk.addCustomer(supermarket.getCustomer(0));
        Bill bill = cashDesk.serveCustomer(time);
        supermarket.getCustomer(0).payBill(bill, time);
        supermarket.removeCustomer(0);
    }
}