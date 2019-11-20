package com.company;

public class Product
{
    public Product(ProductInfo info, int count)
    {
        add(count);
        this.info = info;
    }

    public ProductInfo getInfo()
    {
        return info;
    }

    public int getCount()
    {
        return count;
    }

    public void add(int count)
    {
        if (count < 0)
        {
            throw new IllegalArgumentException("The number of products can not be negative");
        }
        this.count += count;
    }

    public void remove(int count)
    {
        if (count < 0 || this.count - count < 0)
        {
            throw new IllegalArgumentException("The number of products can not be negative");
        }
        this.count -= count;
    }

    public int getTotalPrice()
    {
        return info.getPrice() * count;
    }

    private final ProductInfo info;
    private int count = 0;
}
