package com.company.spreadsheet;

import java.io.IOException;

class ReferenceValue implements IValue
{
    public ReferenceValue(String reference, IValue[][] matrix) throws IOException
    {
        indexes = Spreadsheet.getIndexes(reference);
        this.matrix = matrix;
    }

    @Override
    public Double getNumber()
    {
        if (matrix[indexes.row][indexes.column] == null)
        {
            return null;
        }
        return matrix[indexes.row][indexes.column].getNumber();
    }

    @Override
    public String getString()
    {
        if (matrix[indexes.row][indexes.column] == null)
        {
            return null;
        }
        return matrix[indexes.row][indexes.column].getString();
    }

    private final Indexes indexes;
    private IValue[][] matrix;
}
