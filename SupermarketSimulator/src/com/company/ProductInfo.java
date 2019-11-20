package com.company;

import java.util.Objects;

public class ProductInfo
{
    public enum Measurement
    {
        Units,
        Grams
    }

    public ProductInfo(String name, Measurement measurement, int price)
    {
        validatePrice(price);
        this.name = name;
        this.measurement = measurement;
        this.price = price;
        this.discount = new Discount(0);
        this.adultsOnly = false;
    }

    public ProductInfo(String name, Measurement measurement, int price, Discount discount)
    {
        validatePrice(price);
        this.name = name;
        this.measurement = measurement;
        this.price = price;
        this.discount = discount;
        this.adultsOnly = false;
    }

    public ProductInfo(String name, Measurement measurement, int price, boolean adultsOnly)
    {
        validatePrice(price);
        this.name = name;
        this.measurement = measurement;
        this.price = price;
        this.discount = new Discount(0);
        this.adultsOnly = adultsOnly;
    }

    public ProductInfo(String name, Measurement measurement, int price, Discount discount, boolean adultsOnly)
            throws IllegalArgumentException
    {
        validatePrice(price);
        this.name = name;
        this.measurement = measurement;
        this.price = price;
        this.discount = discount;
        this.adultsOnly = adultsOnly;
    }

    public String getName()
    {
        return name;
    }

    public Measurement getMeasurement()
    {
        return measurement;
    }

    public int getPrice()
    {
        return price;
    }

    public Discount getDiscount()
    {
        return discount;
    }

    public boolean isAdultsOnly()
    {
        return adultsOnly;
    }

    private final String name;
    private final Measurement measurement;
    private final int price;
    private final Discount discount;
    private final boolean adultsOnly;

    private void validatePrice(int price)
    {
        if (price <= 0)
        {
            throw new IllegalArgumentException("The price must be greater than zero");
        }
    }
}