package uni.ai.rota;

import java.util.Scanner;

abstract class Player {
    RotaBoard board;
    Scanner scanner;
    char symbol;

    Player(RotaBoard board, Scanner scanner, char symbol) {
        this.board = board;
        this.scanner = scanner;
        this.symbol = symbol;
    }

    abstract Move getMove() throws InvalidMoveException;
    char getSymbol() { return symbol; }
}
