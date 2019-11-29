package com.company.spreadsheet;

class NumberValue implements IValue
{
    public NumberValue(double value)
    {
        this.value = value;
    }

    @Override
    public Double getNumber()
    {
        return value;
    }

    @Override
    public String getString()
    {
        return String.valueOf(value);
    }

    private final double value;
}
