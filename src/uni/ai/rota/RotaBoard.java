package uni.ai.rota;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class RotaBoard {
    private final static int ROWS = 17;
    private final static int COLUMNS = 32;
    private final static int SPACES = 9;
    final static int NUM_LINES = 12;
    private final static int MAX_PAWNS = 6;
    private final static String BOARD_PATH = "src//resources//RotaBoard.txt";
    private final static String HELP_BOARD_PATH = "src//resources//RotaBoardHelp.txt";
    static int[][] LINES = {
            {0, 4, 8},
            {3, 4, 5},
            {1, 4, 7},
            {6, 4, 2},
            {0, 2, 5},
            {2, 5, 7},
            {5, 7, 8},
            {7, 8, 6},
            {8, 6, 3},
            {6, 3, 1},
            {3, 1, 0},
            {1, 0, 2}
    };

    private char[][] board;
    private char[][] helpBoard;
    private Space[] spaces;

    RotaBoard() {
        initializeBoards();
        initializeSpaces();
    }

    void print() {
        for (int i = 0; i < ROWS; ++i) {
            System.out.println(board[i]);
        }
    }

    void printHelp() {
        for (int i = 0; i < ROWS; ++i) {
            System.out.println(helpBoard[i]);
        }
    }

    boolean isFull() {return numberOfEmptySpaces() == SPACES - MAX_PAWNS;}

    void makeMove(Move move, char symbol) throws InvalidMoveException {
        int from = move.getFrom();
        int to = move.getTo();

        if (!move.isValid() || !spaces[to].isAvailable()) {
            throw new InvalidMoveException("");
        }

        if (from == to) {
            if (!isFull()) {
                setSpace(to, symbol);
                return;
            } else {
                throw new InvalidMoveException("");
            }
        }

        if (spaces[from].getSymbol() != symbol || !isFull()) {
            throw new InvalidMoveException("");
        }

        setSpace(from, Space.EMPTY_SPACE);
        setSpace(to, symbol);
    }


    void clearMove(Move move) {
        setSpace(move.getFrom(), Space.EMPTY_SPACE);
    }

    boolean checkWinner() {
        boolean win = false;
        int s1,s2,s3, i = -1;

        while (++i < NUM_LINES && !win) {
            s1 = LINES[i][0];
            s2 = LINES[i][1];
            s3 = LINES[i][2];

            if (spaces[s1].getSymbol() != Space.EMPTY_SPACE &&
                    spaces[s1].getSymbol() == spaces[s2].getSymbol() &&
                    spaces[s2].getSymbol() == spaces[s3].getSymbol()) {
                win = true;
            }
        }

        return win;
    }

    int numberOfEmptySpaces() {
        int n = 0;
        for (Space s : spaces) {
            if(s.isAvailable()) {
                ++n;
            }
        }
        return n;
    }

    Space[] getState() {
        return spaces;
    }

    List<Move> getAvailableMoves(char symbol) {
        List<Move> availableMoves = new ArrayList<>();
        List<Space> availableSpaces = new ArrayList<>();

        if (checkWinner()) {
            return availableMoves;
        }

        for (Space s : spaces) {
            if(s.isAvailable()) {
               availableSpaces.add(s);
            }
        }

        if (!isFull()) {
            int num;
            for (Space s : availableSpaces) {
                num = s.getSpaceNumber();
                availableMoves.add(new Move(num, num));
            }
            return availableMoves;
        }

        for (Space s : availableSpaces) {
            for (int n : Move.getNeighbours(s.getSpaceNumber())) {
                if(spaces[n].getSymbol() == symbol) {
                    availableMoves.add(new Move(spaces[n].getSpaceNumber(), s.getSpaceNumber()));
                }
            }
        }
        return availableMoves;
    }

    private void setSpace(int space, char symbol) {
        spaces[space].setSymbol(symbol);
        int row = spaces[space].getRow();
        int column = spaces[space].getColumn();
        board[row][column] = symbol;
    }

    private void allocateBoards() {
        board = new char[ROWS][];
        for (int i = 0; i < ROWS; i++) {
            board[i] = new char[COLUMNS];
        }

        helpBoard = new char[ROWS][];
        for (int i = 0; i < ROWS; i++) {
            helpBoard[i] = new char[COLUMNS];
        }
    }

    private void initializeBoards() {
        allocateBoards();
        try {
            File file = new File(BOARD_PATH);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String readLine;
            int i = 0;
            while ((readLine = bufferedReader.readLine()) != null) {
                board[i++] = readLine.toCharArray();
            }
            file = new File(HELP_BOARD_PATH);
            bufferedReader = new BufferedReader(new FileReader(file));

            i = 0;
            while ((readLine = bufferedReader.readLine()) != null) {
                helpBoard[i++] = readLine.toCharArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeSpaces() {
        spaces = new Space[SPACES];
        spaces[0] = new Space(2, 15, 0);
        spaces[1] = new Space(5, 9,  1);
        spaces[2] = new Space(5, 21, 2);
        spaces[3] = new Space(8, 6,  3);
        spaces[4] = new Space(8, 15, 4);
        spaces[5] = new Space(8, 24, 5);
        spaces[6] = new Space(11, 9, 6);
        spaces[7] = new Space(11, 21,7);
        spaces[8] = new Space(14, 15,8);
    }
}