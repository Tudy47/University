import java.util.LinkedList;

/**
 * ParitialSolution provides at least the functionality which is required
 * for the use in searching for solutions of the game in a search tree.
 * It can store a game situation (Board) and a sequence of mooves.
 */
public class PartialSolution {

    /* TODO */
    private Board tabla;
    private LinkedList<Move> mutari;
    /* Add object variables, constructors, methods as required and desired.      */

    public  PartialSolution(Board tabla) {
        this.tabla = tabla;
        this.mutari = new LinkedList<>();
        
    }

    public PartialSolution(Board tablaNoua, LinkedList<Move> mutariAnterioare, Move mutareNoua) {
        this.tabla = tablaNoua;
        this.mutari = new LinkedList<>(mutariAnterioare);
        this.mutari.add(mutareNoua);
    }

    public Board getTabla() {
        return tabla;
    }

    /**
     * Return the sequence of moves which resulted in this partial solution.
     *
     * @return The sequence of moves.
     */
    public LinkedList<Move> moveSequence() {
        /* TODO */
        return mutari;

    }

    @Override
    public String toString() {
        String str = "";
        int lastRobot = -1;
        for (Move move : moveSequence()) {
            if (lastRobot == move.iRobot) {
                str += " -> " + move.endPosition;
            } else {
                if (lastRobot != -1) {
                    str += ", ";
                }
                str += "R" + move.iRobot + " -> " + move.endPosition;
            }
            lastRobot = move.iRobot;
        }
        return str;
    }
}

