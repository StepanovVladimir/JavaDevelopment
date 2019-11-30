package com.company.spreadsheet;

class StringValue implements IValue
{
    StringValue(String value)
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

    @Override
    public boolean containsReference(Indexes indexes)
    {
        return false;
    }

    private final String value;
}
