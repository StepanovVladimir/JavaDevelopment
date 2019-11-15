package com.company;

public class Customer
{
    public enum Category
    {
        Child,
        Adult,
        Retired
    }

    Customer(String name, Category category)
    {
        this.name = name;
        this.category = category;
    }

    public String getName()
    {
        return name;
    }

    public Category getCategory()
    {
        return this.category;
    }

    public Basket getBasket()
    {
        return basket;
    }

    public void setBasket(Basket basket)
    {
        this.basket = basket;
    }

    private final String name;
    private final Category category;

    private Basket basket;
}