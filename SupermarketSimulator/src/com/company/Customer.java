package com.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Customer
{
    public enum Category
    {
        Child,
        Adult,
        Retired
    }

    Customer(Category category, int cashAmount, int cardMoneyAmount, int bonusesAmount,
             ArrayList<Product> supermarketProducts)
    {
        this.category = category;

        this.cashAmount = cashAmount;
        this.cardMoneyAmount = cardMoneyAmount;
        this.bonusesAmount = bonusesAmount;
        remainingMoneyAmount = getTotalMoneyAmount();

        basket = new Basket();
        this.supermarketProducts = supermarketProducts;

        number = ++count;
    }

    public Category getCategory()
    {
        return category;
    }

    public Basket getBasket()
    {
        return (Basket)basket.clone();
    }

    public void putInBasket(int index, int count, Date time)
    {
        Product product = supermarketProducts.get(index);

        if (category == Category.Child && product.getInfo().isAdultsOnly())
        {
            throw new IllegalArgumentException("The child can not take the product for adults only");
        }
        if (product.getCount() < count)
        {
            throw new IllegalArgumentException("There is no such quantity of product");
        }

        int price = getProductPrice(product);

        if (remainingMoneyAmount < price)
        {
            throw new IllegalArgumentException("Customer will not have enough money for this product");
        }

        product.remove(count);
        remainingMoneyAmount -= price;

        basket.add(new Product(product.getInfo(), count));

        System.out.print(format.format(time) + " Customer '" + getName() + "' picked up " + count + " ");
        System.out.println(product.getInfo().getMeasurement().name().toLowerCase() + " of " + product.getInfo().getName());
    }

    public int getCashAmount()
    {
        return cashAmount;
    }

    public int getCardMoneyAmount()
    {
        return cardMoneyAmount;
    }

    public int getBonusesAmount()
    {
        return bonusesAmount;
    }

    public int getRemainingMoneyAmount()
    {
        return remainingMoneyAmount;
    }

    public int getTotalMoneyAmount()
    {
        return cashAmount + cardMoneyAmount + bonusesAmount;
    }

    public String getName()
    {
        return "customer#" + number;
    }

    public void log()
    {
        System.out.println("Category: " + category.name());
        System.out.print("Cash amount: " + cashAmount + "; Card money amount: " + cardMoneyAmount);
        System.out.println("; Bonuses amount: " + bonusesAmount);
    }

    private static int count = 0;
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    private int number;
    private final Category category;

    private int cashAmount;
    private int cardMoneyAmount;
    private int bonusesAmount;
    private int remainingMoneyAmount;

    private Basket basket;

    private ArrayList<Product> supermarketProducts;

    private int getProductPrice(Product product)
    {
        int price = product.getTotalPrice();
        if (category == Category.Retired)
        {
            price *= (100 - product.getInfo().getDiscount().getSize());
            price /= 100;
        }
        return price;
    }
}