package com.company.spreadsheet;

interface Value
{
    Double getNumber();
    String getString();
    boolean containsReference(Indexes indexes);
}