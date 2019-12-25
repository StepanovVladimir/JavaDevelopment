package com.company;

public class Bill
{
    Bill(int price)
    {
        this.price = price;
    }

    public int getPrice()
    {
        return price;
    }

    private int price;
}