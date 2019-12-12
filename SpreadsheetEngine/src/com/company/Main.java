package com.company;

import com.company.spreadsheet.*;

import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Spreadsheet spreadsheet = new Spreadsheet();
        Scanner scanner = new Scanner(System.in);
        runSpreadsheet(spreadsheet, scanner);
    }

    private static void runSpreadsheet(Spreadsheet spreadsheet, Scanner scanner)
    {
        while (scanner.hasNext())
        {
            try
            {
                String command = scanner.next();
                String cellName;
                switch (command)
                {
                    case "set":
                        cellName = scanner.next();
                        String value = scanner.next();
                        spreadsheet.setValue(cellName, value);
                        System.out.println("OK");
                        break;

                    case "setreference":
                        cellName = scanner.next();
                        String reference = scanner.next();
                        spreadsheet.setReference(cellName, reference);
                        System.out.println("OK");
                        break;

                    case "setformula":
                        cellName = scanner.next();
                        String formula = scanner.nextLine();
                        spreadsheet.setFormula(cellName, formula);
                        System.out.println("OK");
                        break;

                    case "display":
                        spreadsheet.print();
                        break;

                    case "exit":
                        return;

                    default:
                        System.out.println("Nonexistent command");
                }
            }
            catch (IOException exc)
            {
                System.out.println(exc.getMessage());
            }
            catch (IllegalArgumentException exc)
            {
                System.out.println(exc.getMessage());
            }
        }
    }
}