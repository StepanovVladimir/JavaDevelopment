package com.company;

public class Discount
{
    public Discount(int size)
    {
        if (size < 0 || size > 90)
        {
            throw new IllegalArgumentException("There can be no discount less than zero or more than 90");
        }
        this.size = size;
    }

    public int getSize()
    {
        return size;
    }

    private int size;
}