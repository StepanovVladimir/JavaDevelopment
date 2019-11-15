package com.company;

import java.util.ArrayList;

public class Basket
{
    public Basket()
    {
        products = new ArrayList<>();
    }

    public Product getProduct(int i)
    {
        return products.get(i);
    }

    public void addProduct(Product product)
    {
        products.add(product);
    }

    public void removeProduct(int i)
    {
        products.remove(i);
    }

    private ArrayList<Product> products;
}