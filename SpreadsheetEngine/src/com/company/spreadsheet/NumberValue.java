package com.company.spreadsheet;

class NumberValue implements IValue
{
    NumberValue(double value)
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

    @Override
    public boolean containsReference(Indexes indexes)
    {
        return false;
    }

    private final double value;
}
