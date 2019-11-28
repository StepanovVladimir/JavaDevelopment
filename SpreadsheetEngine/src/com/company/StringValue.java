package com.company;

public class StringValue implements IValue
{
    public StringValue(String value)
    {
        this.value = value;
    }

    @Override
    public Double getNumberValue()
    {
        return null;
    }

    @Override
    public String getStringValue()
    {
        return value;
    }

    private final String value;
}
