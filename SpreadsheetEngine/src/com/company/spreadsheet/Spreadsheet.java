package com.company.spreadsheet;

import java.io.IOException;

public class Spreadsheet
{
    public void setValue(String cellName, String value) throws IOException
    {
        Indexes indexes = getIndexes(cellName);
        try
        {
            double num = Double.parseDouble(value);
            matrix[indexes.row][indexes.column] = new NumberValue(num);
        }
        catch (NumberFormatException exc)
        {
            matrix[indexes.row][indexes.column] = new StringValue(value);
        }
    }

    public void setReference(String cellName, String reference) throws IOException
    {
        Indexes indexes = getIndexes(cellName);
        matrix[indexes.row][indexes.column] = new ReferenceValue(reference, matrix);
    }

    public void setFormula(String cellName, String formula) throws IOException
    {
        Indexes indexes = getIndexes(cellName);
        matrix[indexes.row][indexes.column] = new FormulaValue(formula, matrix);
    }

    public void print()
    {
        System.out.printf("%-5s", "");
        for (int i = 0; i < MATRIX_SIZE; ++i)
        {
            System.out.printf("%-10s", indexToCellName(i));
        }
        System.out.println();
        for (int i = 0; i < MATRIX_SIZE; ++i)
        {
            System.out.printf("%-5s", i + 1);
            for (int j = 0; j < 30; ++j)
            {
                IValue cell = matrix[i][j];
                if (cell != null && cell.getString() != null)
                {
                    System.out.printf("%-10s", cell.getString());
                }
                else
                {
                    System.out.printf("%-10s", "");
                }
            }
            System.out.println();
        }
    }

    private static final int MATRIX_SIZE = 30;
    private static final String INVALID_NAME_OF_CELL = "Invalid name of cell";

    private IValue[][] matrix = new IValue[MATRIX_SIZE][MATRIX_SIZE];

    static Indexes getIndexes(String cellName) throws IOException
    {
        if (cellName.length() < 2)
        {
            throw new IOException(INVALID_NAME_OF_CELL);
        }

        int index = 0;
        for (int i = 0; i < cellName.length(); ++i)
        {
            char ch = cellName.charAt(i);
            if (ch >= '1' && ch <= '9')
            {
                if (i == 0)
                {
                    throw new IOException(INVALID_NAME_OF_CELL);
                }
                index = i;
                break;
            }
            else if (ch < 'A' || ch > 'Z')
            {
                throw new IOException(INVALID_NAME_OF_CELL);
            }
        }

        if (index == 0)
        {
            throw new IOException(INVALID_NAME_OF_CELL);
        }

        String column = cellName.substring(0, index);
        int columnIndex = cellNameToIndex(column);
        int rowIndex;
        try
        {
            rowIndex = Integer.parseInt(cellName.substring(index)) - 1;
        }
        catch (NumberFormatException exc)
        {
            throw new IOException(INVALID_NAME_OF_CELL);
        }
        return new Indexes(columnIndex, rowIndex);
    }

    private static int cellNameToIndex(String str)
    {
        int index = 0;
        for (int i = 0; i < str.length(); ++i)
        {
            int degree = str.length() - i - 1;
            if (degree > 0)
            {
                index += (str.charAt(i) - 'A' + 1) * (int)Math.pow(26, degree);
            }
            else
            {
                index += str.charAt(i) - 'A';
            }
        }
        return index;
    }

    private static String indexToCellName(int index)
    {
        StringBuilder builder = new StringBuilder();
        do
        {
            if (builder.length() == 0)
            {
                int i = index % 26;
                char ch = (char)('A' + i);
                builder.insert(0, ch);
                index /= 26;
            }
            else
            {
                int i = index % 27;
                char ch = (char)('A' + i - 1);
                builder.insert(0, ch);
                index /= 27;
            }
        }
        while (index > 0);

        return builder.toString();
    }
}