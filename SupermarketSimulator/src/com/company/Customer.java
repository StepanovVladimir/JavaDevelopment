package com.company;

public class Customer
{
    public enum Category
    {
        Child,
        Adult,
        Retired
    }

    Customer(Category category, int cashAmount, int cardMoneyAmount, int bonusesAmount)
    {
        this.category = category;
        this.cashAmount = cashAmount;
        this.cardMoneyAmount = cardMoneyAmount;
        this.bonusesAmount = bonusesAmount;
        this.remainingMoneyAmount = getTotalMoneyAmount();
        this.basket = new Basket();
    }

    public Category getCategory()
    {
        return category;
    }

    public Basket getBasket()
    {
        return (Basket)basket.clone();
    }

    public void putInBasket(Product product)
    {
        if (category == Category.Child && product.getInfo().isAdultsOnly())
        {
            throw new IllegalArgumentException("The child can not take the product for adults only");
        }
        if (remainingMoneyAmount < product.getTotalPrice())
        {
            throw new IllegalArgumentException("Customer will not have enough money for this product");
        }

        int price = product.getTotalPrice() * product.getTotalPrice();
        if (category == Category.Retired)
        {
            price *= (100 - product.getInfo().getDiscount().getSize());
            price /= 100;
        }

        remainingMoneyAmount -= price;
        basket.add(product);
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

    private final Category category;
    private int cashAmount;
    private int cardMoneyAmount;
    private int bonusesAmount;
    private int remainingMoneyAmount;
    private Basket basket;
}