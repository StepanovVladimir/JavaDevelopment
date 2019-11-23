package com.company;

import java.util.HashMap;
import java.util.Map;

public class Report
{
    public void addProducts(ProductInfo productInfo, int count)
    {
        if (!products.containsKey(productInfo))
        {
            products.put(productInfo, count);
        }
        else
        {
            int i = products.get(productInfo);
            products.put(productInfo, i + count);
        }
    }

    public void log()
    {
        System.out.println("Per day sold:");
        for (Map.Entry<ProductInfo, Integer> product : products.entrySet())
        {
            System.out.print(product.getValue() + " " + product.getKey().getMeasurement().name().toLowerCase());
            System.out.println(" of " + product.getKey().getName());
        }
    }

    private Map<ProductInfo, Integer> products = new HashMap<>();
}
