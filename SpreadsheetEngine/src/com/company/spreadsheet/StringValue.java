package com.company.spreadsheet;

class StringValue implements IValue
{
    public StringValue(String value)
    {
        this.value = value;
    }

    @Override
    public Double getNumber()
    {
        return null;
    }

    @Override
    public String getString()
    {
        return value;
    }

    private final String value;
}
