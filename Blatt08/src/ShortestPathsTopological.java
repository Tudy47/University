import java.util.Stack;

public class ShortestPathsTopological {
    private int[] parent;
    private int s;
    private double[] dist;

    public ShortestPathsTopological(WeightedDigraph G, int s) {
        // TODO
        this.s = s;
        int V = G.V();
        dist = new double[V];
        parent = new int[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Double.POSITIVE_INFINITY;
            parent[i] = -1;
        }
        dist[s] = 0.0;
        parent[s] = s;


        TopologicalWD topo = new TopologicalWD(G);
        topo.dfs(s);
        Stack<Integer> ordine = topo.order();


        while (!ordine.isEmpty()) {
            int v = ordine.pop();
            if (dist[v] < Double.POSITIVE_INFINITY) {
                for (DirectedEdge e : G.incident(v)) {
                    relax(e);
                }
            }
        }

    }

    public void relax(DirectedEdge e) {
        // TODO
        int u = e.from();
        int v = e.to();
        double w = e.weight();

        if (dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
            parent[v] = u;
        }
    }

    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }
}

