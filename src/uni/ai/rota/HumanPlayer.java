package uni.ai.rota;

import java.util.Scanner;

class HumanPlayer extends Player {
    HumanPlayer(RotaBoard board, Scanner scanner, char symbol) {
        super(board, scanner, symbol);
    }

    Move getMove() throws InvalidMoveException {
        int from, to;
        try {
            String command = scanner.nextLine();
            String[] args = command.split("\\s");
            if (args.length != 2) {
                throw new InvalidMoveException("Invalid move");
            }
            from = Integer.parseInt(args[0]);
            to = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new InvalidMoveException(e.getMessage());
        }
        return new Move(from, to);
    }
}
