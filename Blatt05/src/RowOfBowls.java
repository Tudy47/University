import java.util.ArrayList;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {

    public RowOfBowls() {
    }
    private int[] valori;
    private int[][] tabla;

    /**
     *
     * Implements an optimal game using dynamic programming
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGain(int[] values)
    {
        // TODO
        int n = values.length;
        tabla = new int[n][n];
        valori = values;

        for(int i = 0; i < n; i++) {
            tabla[i][i] = values[i];
        }

        for(int j = 1; j < n; j++) {
            for(int i = 0; i < n-j; i++) {
                int k = i +j;
                tabla[i][k] = Math.max(values[i]- tabla[i+1][k], values[k]-tabla[i][k-1]);
            }
        }
        return tabla[0][n-1];
    }

    /**
     * Implements an optimal game recursively.
     *
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGainRecursive(int[] values) {
        // TODO
        return foo(values, 0 , values.length-1);



    }

    //functie
    private int foo(int[] v, int stang,int dreap){
        if (stang == dreap){
            return v[stang];
        }
        if (stang > dreap){
            return 0;
        }
        int p1 = v[stang] - foo(v, stang+1, dreap);
        int p2 = v[dreap] - foo(v, stang, dreap-1);
        return Math.max(p1, p2);
    }

    
    /**
     * Calculates an optimal sequence of bowls using the partial solutions found in maxGain(int values)
     * @return optimal sequence of chosen bowls (represented by the index in the values array)
     */
    public Iterable<Integer> optimalSequence()
    {
        // TODO
        ArrayList<Integer> ordine = new ArrayList<Integer>();
        int i=0, j= valori.length -1;

        while (i <= j) {
            int stanga, dreapta;

            if (i + 1 <= j) {
                stanga = valori[i] - tabla[i + 1][j];
            } else {
                stanga = valori[i];
            }

            if (i <= j - 1) {
                dreapta = valori[j] - tabla[i][j - 1];
            } else {
                dreapta = valori[j];
            }

            if (stanga >= dreapta) {
                ordine.add(i);
                i++;
            } else {
                ordine.add(j);
                j--;
            }
        }


    return ordine;

    }


    public static void main(String[] args)
    {
        // For Testing

        
        }
}

