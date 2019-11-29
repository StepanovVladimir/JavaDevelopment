package com.company;

import com.company.spreadsheet.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args)
    {
        Spreadsheet spreadsheet = new Spreadsheet();
        try
        {
            spreadsheet.setReference("D1", "C1");
            spreadsheet.setReference("D2", "B2");
            spreadsheet.setValue("A1", 12);
            spreadsheet.setValue("B1", 2.34);
            spreadsheet.setValue("C1", "hello");
            spreadsheet.setValue("A2", 2.3432);
            spreadsheet.setValue("B2", 2.347);
            spreadsheet.setValue("C2", 22377);
            spreadsheet.setValue("G5", 17.1756);
            spreadsheet.setFormula("E1", "*(+ A1 5) A1");
            spreadsheet.setFormula("E2", "+ C1 E1");
        }
        catch (IOException exc)
        {
            System.out.println(exc.getMessage());
        }
        spreadsheet.print();
    }
}