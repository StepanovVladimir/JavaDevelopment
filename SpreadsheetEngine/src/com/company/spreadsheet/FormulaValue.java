package com.company.spreadsheet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class FormulaValue implements Value
{
    FormulaValue(String formula, Value[][] matrix, Indexes source) throws IOException
    {
        InputStream stream = new ByteArrayInputStream(formula.getBytes());
        InputStreamReader reader = new InputStreamReader(stream);

        operation = readOperation(reader);
        first = readFormulaMember(reader, matrix, source);
        second = readFormulaMember(reader, matrix, source);

        if (containsReference(source))
        {
            throw new IllegalArgumentException("There is a circular reference");
        }

        getString();
    }

    @Override
    public Double getNumber()
    {
        Double num1 = first.getNumber();
        Double num2 = second.getNumber();
        if (num1 == null || num2 == null)
        {
            return null;
        }

        return calculateFormula(operation, num1, num2);
    }

    @Override
    public String getString()
    {
        String str1 = first.getString();
        String str2 = second.getString();
        if (str1 == null || str2 == null)
        {
            return null;
        }

        try
        {
            double num1 = Double.parseDouble(str1);
            double num2 = Double.parseDouble(str2);
            return String.valueOf(calculateFormula(operation, num1, num2));
        }
        catch (NumberFormatException exc)
        {
            if (operation != Operation.Add)
            {
                throw new IllegalArgumentException("Invalid string operation");
            }
            return str1 + str2;
        }
    }

    @Override
    public boolean containsReference(Indexes indexes)
    {
        return first.containsReference(indexes) || second.containsReference(indexes);
    }

    private Operation operation;
    private Value first;
    private Value second;

    private static Operation readOperation(InputStreamReader reader) throws IOException
    {
        char ch = readFirstChar(reader);
        switch (ch)
        {
            case '+':
                return Operation.Add;

            case '-':
                return Operation.Sub;

            case '*':
                return Operation.Mul;

            case '/':
                return Operation.Div;

            default:
                throw new IOException("Invalid formula");
        }
    }

    private static Value readFormulaMember(InputStreamReader reader, Value[][] matrix, Indexes source) throws IOException
    {
        char ch = readFirstChar(reader);
        if (ch == '(')
        {
            return readSubFormula(reader, matrix, source);
        }
        else
        {
            return readSimpleMember(ch, reader, matrix, source);
        }
    }

    private static FormulaValue readSubFormula(InputStreamReader reader, Value[][] matrix, Indexes source) throws IOException
    {
        int count = 1;
        StringBuilder builder = new StringBuilder();
        while (count > 0)
        {
            char ch = (char)reader.read();
            if (ch == '(')
            {
                ++count;
            }
            else if (ch == ')')
            {
                --count;
            }
            if (count > 0)
            {
                builder.append(ch);
            }
        }
        String str = builder.toString();
        return new FormulaValue(str, matrix, source);
    }

    private static Value readSimpleMember(char ch, InputStreamReader reader, Value[][] matrix, Indexes source) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        builder.append(ch);

        while (ch != ' ' && reader.ready())
        {
            ch = (char)reader.read();
            if (ch != ' ')
            {
                builder.append(ch);
            }
        }

        String str = builder.toString();
        try
        {
            double num = Double.parseDouble(str);
            return new NumberValue(num);
        }
        catch (NumberFormatException exc)
        {
            return new ReferenceValue(str, matrix, source);
        }
    }

    private static char readFirstChar(InputStreamReader reader) throws IOException
    {
        char ch;
        do
        {
            ch = (char)reader.read();
        }
        while (ch == ' ');
        return ch;
    }

    private static Double calculateFormula(Operation operation, double num1, double num2)
    {
        switch (operation)
        {
            case Add:
                return num1 + num2;

            case Sub:
                return num1 - num2;

            case Mul:
                return num1 * num2;

            case Div:
                return num1 / num2;

            default:
                return null;
        }
    }
}