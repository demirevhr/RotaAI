package uni.ai.rota;

import java.util.List;
import java.util.Scanner;

class ComputerPlayer extends Player {
    private static final int MEDIUM_DIFFICULTY_DEPTH = 18;
    private final char opponentSymbol;
    ComputerPlayer(RotaBoard board, Scanner scanner, char symbol, char opponentSymbol) {
        super(board, scanner, symbol);
        this.opponentSymbol = opponentSymbol;
    }

    Move getMove() throws InvalidMoveException {
        int[] result = minimax(MEDIUM_DIFFICULTY_DEPTH, symbol, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new Move(result[1], result[2]);
    }

    private int[] minimax(int depth, char currentPlayer, int alpha, int beta) throws InvalidMoveException {
        List<Move> possibleMoves = board.getAvailableMoves(currentPlayer);
        int score;
        int bestFrom = -1;
        int bestTo = -1;

        if (board.checkWinner() || depth == 0) {
            score = h(board.getState());
            return new int[] {score, bestFrom, bestTo};
        }

        for (Move move : possibleMoves) {
            board.makeMove(move, currentPlayer);

            if (currentPlayer == symbol) {  // computer is maximizing player
                score = minimax(depth - 1, opponentSymbol, alpha, beta)[0];
                if (score > alpha) {
                    alpha = score;
                    bestFrom = move.getFrom();
                    bestTo = move.getTo();
                }
            } else {  // opponent is minimizing
                score = minimax(depth - 1, symbol, alpha, beta)[0];
                if (score < beta) {
                    beta = score;
                    bestFrom = move.getFrom();
                    bestTo = move.getTo();
                }
            }
            undoMove(move, currentPlayer);
            if (alpha >= beta)
                break;
        }
        return new int[]{(currentPlayer == symbol) ? alpha : beta, bestFrom, bestTo};
    }

    private int h(Space[] state) {
        int score = 0;
        for (int i = 0; i < RotaBoard.NUM_LINES; i++) {
            score += evaluateLine(state, RotaBoard.LINES[i][0],
                                         RotaBoard.LINES[i][1],
                                         RotaBoard.LINES[i][2]);
        }
        return score;
    }

    private void undoMove(Move move, char currentPlayer) throws InvalidMoveException {
        if (move.getFrom() == move.getTo()) {
            board.clearMove(move);
        } else {
            board.makeMove(new Move(move.getTo(), move.getFrom()), currentPlayer);
        }
    }

    private int evaluateLine(Space[] state, int s1, int s2, int s3) {
        int score = 0, numComp = 0, numOpponent = 0;

        if (state[s1].getSymbol() == symbol) {
            ++numComp;
        } else if (state[s1].getSymbol() == opponentSymbol) {
            ++numOpponent;
        }

        if (state[s2].getSymbol() == symbol) {
            ++numComp;
        } else if (state[s2].getSymbol() == opponentSymbol) {
            ++numOpponent;
        }

        if (state[s3].getSymbol() == symbol) {
            ++numComp;
        } else if (state[s3].getSymbol() == opponentSymbol) {
            ++numOpponent;
        }

        if (numOpponent == 0) {
            if (numComp == 2) {
                score += 2;
            } else if (numComp == 1) {
                score += 1;
            }
        }

        if (numComp == 0) {
            if (numOpponent == 2) {
                score -= 2;
            } else if (numOpponent == 1) {
                score -= 1;
            }
        }
        return score;
    }
}
