package com.company;

public class Bill
{
    Bill(int price)
    {
        if (price < 0)
        {
            throw new IllegalArgumentException("Price cannot be less than 0");
        }
        this.price = price;
    }

    public int getPrice()
    {
        return price;
    }

    private int price;
}