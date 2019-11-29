package com.company.spreadsheet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

class FormulaValue implements IValue
{
    FormulaValue(String formula, IValue[][] matrix) throws IOException
    {
        InputStream stream = new ByteArrayInputStream(formula.getBytes());
        InputStreamReader reader = new InputStreamReader(stream);
        //Scanner scanner = new Scanner(formula);

        char ch = (char)reader.read();
        switch (ch)
        {
            case '+':
                operation = Operation.Add;
                break;

            case '-':
                operation = Operation.Sub;
                break;

            case '*':
                operation = Operation.Mul;
                break;

            case '/':
                operation = Operation.Div;
                break;

            default:
                throw new IOException("Invalid formula");
        }

        do
        {
            ch = (char)reader.read();
        }
        while (ch == ' ');

        if (ch != '(')
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
                first = new NumberValue(num);
            }
            catch (NumberFormatException exc)
            {
                first = new ReferenceValue(str, matrix);
            }
        }
        else
        {
            int count = 1;
            StringBuilder builder = new StringBuilder();
            while (count > 0)
            {
                ch = (char)reader.read();
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
            first = new FormulaValue(str, matrix);
        }


        do
        {
            ch = (char)reader.read();
        }
        while (ch == ' ');

        if (ch != '(')
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
                second = new NumberValue(num);
            }
            catch (NumberFormatException exc)
            {
                second = new ReferenceValue(str, matrix);
            }
        }
        else
        {
            int count = 1;
            StringBuilder builder = new StringBuilder();
            while (count > 0)
            {
                ch = (char)reader.read();
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
            second = new FormulaValue(str, matrix);
        }
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

        return calculateFormula(num1, num2);
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
            Double num1 = Double.parseDouble(str1);
            Double num2 = Double.parseDouble(str2);
            return String.valueOf(calculateFormula(num1, num2));
        }
        catch (NumberFormatException exc)
        {
            if (operation != Operation.Add)
            {
                return null;
            }
            return str1 + str2;
        }
    }

    private IValue first;
    private IValue second;
    private Operation operation;

    private Double calculateFormula(double num1, double num2)
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