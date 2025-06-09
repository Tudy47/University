import java.util.InputMismatchException;
import java.util.Stack;

import static java.lang.Math.abs;
/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private int n;
    //TODO
    private int[][] board;
    private int freeFields;
    private boolean lastMoveWon;
    private Position lastMove;

    private Stack<Position> moveHistory = new Stack<>();
    private Stack<Integer> valueHistory = new Stack<>();



    /**
     *  Creates Board object, am game board of size n * n with 1<=n<=10.
     */
    public Board(int n)
    {
        // TODO
        if (n < 1 || n > 10)
            throw new InputMismatchException();
        this.n = n;
        this.board = new int[n][n];
        this.freeFields = n * n;
    }

    /**
     *  @return     length/width of the Board object
     */
    public int getN() { return n; }

    /**
     *  @return     number of currently free fields
     */
    public int nFreeFields() {
        // TODO
        return freeFields;
    }

    /**
     *  @return     token at position pos
     */
    public int getField(Position pos) throws InputMismatchException
    {
        // TODO
        if (pos.x < 0 || pos.x >= n || pos.y < 0 || pos.y >= n)
            throw new InputMismatchException();
        return board[pos.x][pos.y];
    }

    /**
     *  Sets the specified token at Position pos.
     */
    public void setField(Position pos, int token) throws InputMismatchException
    {
        // TODO
        if (pos.x < 0 || pos.x >= n || pos.y < 0 || pos.y >= n)
            throw new InputMismatchException();
        if (token < -1 || token > 1)
            throw new InputMismatchException();

        int current = board[pos.x][pos.y];
        if (current == 0 && token != 0)
            freeFields--;
        if (current != 0 && token == 0)
            freeFields++;

        board[pos.x][pos.y] = token;
    }
    
    /**
     *  Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player)
    {
        // TODO
        if (getField(pos) != 0)
            throw new IllegalArgumentException("Field already taken.");
        moveHistory.push(pos);
        valueHistory.push(getField(pos));
        setField(pos, player);
        lastMove = pos;
        lastMoveWon = checkWin(pos, player);
    }

    /**
     *  Clears board at Position pos.
     */
    public void undoMove(Position pos)
    {
        // TODO
        if (!moveHistory.isEmpty()) {
            Position last = moveHistory.pop();
            int previousValue = valueHistory.pop();
            setField(last, previousValue);
            lastMoveWon = false;
        }

    }
    
    /**
     *  @return     true if game is won, false if not
     */
    public boolean isGameWon() {
        // TODO
        return lastMoveWon;
    }

    /**
     *  @return     set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves()
    {
        // TODO
        java.util.List<Position> moves = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] == 0)
                    moves.add(new Position(i, j));
        return moves;
    }

    /**
     *  Outputs current state representation of the Board object.
     *  Practical for debugging.
     */
    public void print()
    {
        // TODO
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                int val = board[x][y];
                if (val == 1)
                    System.out.print("x ");
                else if (val == -1)
                    System.out.print("o ");
                else
                    System.out.print("0 ");
            }
            System.out.println();
        }
    }

    private boolean checkWin(Position pos, int player) {
        int x = pos.x, y = pos.y;
        int[][] directions = {{1,0},{0,1},{1,1},{1,-1}};

        for (int[] d : directions) {
            int count = 1;

            // forward
            for (int dx = d[0], dy = d[1]; ; dx += d[0], dy += d[1]) {
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && board[nx][ny] == player)
                    count++;
                else break;
            }

            // backward
            for (int dx = -d[0], dy = -d[1]; ; dx -= d[0], dy -= d[1]) {
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < n && ny >= 0 && ny < n && board[nx][ny] == player)
                    count++;
                else break;
            }

            if (abs(count) >= n)
                return true;
        }
        return false;
    }

}

