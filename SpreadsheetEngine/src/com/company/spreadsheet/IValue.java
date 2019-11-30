package com.company.spreadsheet;

interface IValue
{
    Double getNumber();
    String getString();
    boolean containsReference(Indexes indexes);
}