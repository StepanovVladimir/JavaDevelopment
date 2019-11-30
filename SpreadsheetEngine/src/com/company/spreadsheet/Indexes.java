package com.company.spreadsheet;

class Indexes
{
    Indexes(int column, int row)
    {
        this.column = column;
        this.row = row;
    }

    @Override
    public boolean equals(Object obj)
    {
        Indexes indexes = (Indexes)obj;
        return column == indexes.column && row == indexes.row;
    }

    int column;
    int row;
}
