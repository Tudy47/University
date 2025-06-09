/**
 * This class implements and evaluates game situations of a TicTacToe game.
 */
public class TicTacToe {

    /**
     * Returns an evaluation for player at the current board state.
     * Arbeitet nach dem Prinzip der Alphabeta-Suche. Works with the principle of Alpha-Beta-Pruning.
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
     * @return          rating of game situation from player's point of view
    **/
    public static int alphaBeta(Board board, int player)
    {
        return alphaBeta(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private static int alphaBeta(Board board, int player, int alpha, int beta)
    {
        if (board.nFreeFields() == 0) {
            return 0; // draw
        }

        int bestScore = Integer.MIN_VALUE;

        for (Position move : board.validMoves()) {
            board.doMove(move, player);

            int score;
            if (board.isGameWon()) {
                score = board.nFreeFields() + 1; // player wins -> positive score
            } else {
                score = -alphaBeta(board, -player, -beta, -alpha); // negamax
            }

            board.undoMove(move);

            bestScore = Math.max(bestScore, score);
            alpha = Math.max(alpha, score);
            if (alpha >= beta) {
                break; // β-cutoff
            }
        }

        return bestScore;
    }



    /**
     * Vividly prints a rating for each currently possible move out at System.out.
     * (from player's point of view)
     * Uses Alpha-Beta-Pruning to rate the possible moves.
     * formatting: See "Beispiel 1: Bewertung aller Zugmöglichkeiten" (Aufgabenblatt 4).
     *
     * @param board     current Board object for game situation
     * @param player    player who has a turn
    **/
    public static void evaluatePossibleMoves(Board board, int player)
    {
        System.out.println("Evaluation for player '" + (player == 1 ? "x" : "o") + "':");

        int n = board.getN();
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                Position pos = new Position(x, y);
                int val = board.getField(pos);
                if (val == 1) {
                    System.out.print("x ");
                } else if (val == -1) {
                    System.out.print("o ");
                } else {
                    board.doMove(pos, player);
                    int score;
                    if (board.isGameWon()) {
                        score = board.nFreeFields() + 1;
                    } else {
                        score = -alphaBeta(board, -player, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    }
                    board.undoMove(pos);
                    System.out.print(score + " ");
                }
            }
            System.out.println();
        }
    }


    public static void main(String[] args)
    {
        
    }
}

