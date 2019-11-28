package com.company;

public class NumberValue implements IValue
{
    public NumberValue(double value)
    {
        this.value = value;
    }

    @Override
    public Double getNumberValue()
    {
        return value;
    }

    @Override
    public String getStringValue()
    {
        return String.valueOf(value);
    }

    private final double value;
}
