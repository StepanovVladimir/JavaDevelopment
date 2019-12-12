package com.company.spreadsheet;

import java.io.IOException;

class ReferenceValue implements Value
{
    ReferenceValue(String reference, Value[][] matrix, Indexes source) throws IOException
    {
        this.reference = Spreadsheet.getIndexes(reference);
        this.matrix = matrix;

        if (containsReference(source))
        {
            throw new IllegalArgumentException("There is a circular reference");
        }
    }

    @Override
    public Double getNumber()
    {
        if (matrix[reference.row][reference.column] == null)
        {
            return null;
        }
        return matrix[reference.row][reference.column].getNumber();
    }

    @Override
    public String getString()
    {
        if (matrix[reference.row][reference.column] == null)
        {
            return null;
        }
        return matrix[reference.row][reference.column].getString();
    }

    @Override
    public boolean containsReference(Indexes indexes)
    {
        if (reference.equals(indexes))
        {
            return true;
        }
        Value value = matrix[reference.row][reference.column];
        return value != null && value.containsReference(indexes);
    }

    private final Indexes reference;
    private Value[][] matrix;
}