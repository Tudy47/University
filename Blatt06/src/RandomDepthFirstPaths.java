import java.util.*;

public class RandomDepthFirstPaths {
    private boolean[] marked; // marked[v] = is there an s-v path?
    private int[] edgeTo; // edgeTo[v] = last edge on s-v path
    private final int s; // source vertex

    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public RandomDepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
    }

    public void randomDFS(Graph G) {
        randomDFS(G, s);
    }

    // depth first search from v
    private void randomDFS(Graph G, int v) {
        // TODO
        marked[v] = true;
        List<Integer> vecini = new ArrayList<>(G.adj(v));
        Collections.shuffle(vecini);
        for (int w : vecini) {
            if (!marked[w]) {
                edgeTo[w] = v;
                randomDFS(G, w);
            }
        }

    }

    public void randomNonrecursiveDFS(Graph G) {
        // TODO
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];

        Stack<Integer> pila = new Stack<>();
        marked[s] = true;
        pila.push(s);

        while (!pila.isEmpty()) {
            int nodCurent = pila.peek();
            List<Integer> candidati = new ArrayList<>();

            for (int vecin : G.adj(nodCurent)) {
                if (!marked[vecin]) {
                    candidati.add(vecin);
                }
            }

            if (candidati.isEmpty()) {
                pila.pop();
            } else {
                Collections.shuffle(candidati);
                int urmator = candidati.get(0);
                marked[urmator] = true;
                edgeTo[urmator] = nodCurent;
                pila.push(urmator);
            }

        }

    }

    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     *
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a path between the vertex {@code v} and the source vertex {@code s},
     * or
     * {@code null} if no such path.
     *
     * @param v the vertex
     * @return the sequence of vertices on a path between the vertex
     *         {@code v} and the source vertex {@code s}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     *
     */
    public List<Integer> pathTo(int v) {
        // TODO
        validateVertex(v);
        if (!hasPathTo(v)) return null;

        List<Integer> drum = new ArrayList<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            drum.add(x);
        }
        drum.add(s);
        Collections.reverse(drum);
        return drum;
    }

    public int[] edge() {
        return edgeTo;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}
