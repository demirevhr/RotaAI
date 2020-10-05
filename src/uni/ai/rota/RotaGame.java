package uni.ai.rota;

import java.util.Scanner;

public class RotaGame {
    private RotaBoard board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Scanner scanner;

    public RotaGame() {
        board = new RotaBoard();
        scanner = new Scanner(System.in);
    }

    public void play() {
        System.out.println("Hello, player 1! Please choose your symbol:");
        char sym = scanner.next().charAt(0);
        player1 = new HumanPlayer(this.board, this.scanner, sym);

        System.out.println("To play vs computer press 1\nTo play vs another player press 2");
        getOtherPlayer();

        determineFirst();
        System.out.println(String.format("Player %s is first", currentPlayer.getSymbol()));

        board.printHelp();
        Move move = new Move();
        while(!isGameOver()) {
            System.out.println("Please enter your move: a b  (where a,b = [0-8])");
            board.print();

            try {
                move = currentPlayer.getMove();
                board.makeMove(move, currentPlayer.getSymbol());
                switchPlayers();
            } catch (Exception e) {
                String message = !move.isValid() ?  "Invalid move. Please check the instructions and enter a move as expected"
                        : String.format("Moving from %d to %d is not possible : invalid move", move.getFrom(), move.getTo());
                System.out.println(message);
            }
        }

        switchPlayers();
        board.print();
        System.out.println(String.format("Congratulations player %s! You win!", currentPlayer.getSymbol()));
    }

    private void switchPlayers() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    private void getOtherPlayer() {
        char otherPlayer = scanner.next().charAt(0);
        if (otherPlayer == '1') {
            player2 = new ComputerPlayer(this.board, this.scanner, '$', player1.getSymbol());
        } else {
            System.out.println("Hello, player! Please choose your symbol:");
            char sym = scanner.next().charAt(0);
            player2 = new HumanPlayer(this.board, this.scanner, sym);
        }
    }

    private void determineFirst() {
        System.out.println("Who goes first? Enter 1 or 2");
        int first = scanner.nextInt();
        while (first != 1 && first != 2) {
            System.out.println("Invalid player number");
            first = scanner.nextInt();
        }
        if (first == 1) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
    }

    private boolean isGameOver() {
        return board.checkWinner();
    }
}