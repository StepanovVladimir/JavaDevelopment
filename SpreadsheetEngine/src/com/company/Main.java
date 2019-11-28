package com.company;

public class Main {

    public static void main(String[] args)
    {
        Spreadsheet spreadsheet = new Spreadsheet();
        spreadsheet.setValue("A1", 12);
        spreadsheet.setValue("B1", 2.34);
        spreadsheet.setValue("C1", "hello");
        spreadsheet.setValue("A2", 2.3432);
        spreadsheet.setValue("B2", 2.347);
        spreadsheet.setValue("C2", 22377);
        spreadsheet.setValue("G5", 17.1756);
        spreadsheet.print();
    }
}