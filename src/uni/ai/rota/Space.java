package uni.ai.rota;

class Space {
    final static char EMPTY_SPACE = ' ';

    private int rowIndex;
    private int columnIndex;
    private boolean available;
    private char symbol;
    private int spaceNumber;

    Space(int row, int column, int spaceNumber) {
        this.rowIndex = row;
        this.columnIndex = column;
        setSymbol(EMPTY_SPACE);
        this.spaceNumber = spaceNumber;
    }

    char getSymbol()  { return symbol; }
    boolean isAvailable() { return available; }
    int getRow() { return rowIndex; }
    int getColumn() { return columnIndex; };
    int getSpaceNumber() { return spaceNumber; }
    void setSymbol(char a) { symbol = a; available = (a == EMPTY_SPACE); }
}