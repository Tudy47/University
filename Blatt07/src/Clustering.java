import java.util.*;
import java.awt.Color;

/**
 * This class solves a clustering problem with the Prim algorithm.
 */
public class Clustering {
	EdgeWeightedGraph G;
	List <List<Integer>>clusters; 
	List <List<Integer>>labeled; 
	
	/**
	 * Constructor for the Clustering class, for a given EdgeWeightedGraph and no labels.
	 * @param G a given graph representing a clustering problem
	 */
	public Clustering(EdgeWeightedGraph G) {
            this.G=G;
	    clusters= new LinkedList <List<Integer>>();
	}
	
    /**
	 * Constructor for the Clustering class, for a given data set with labels
	 * @param in input file for a clustering data set with labels
	 */
	public Clustering(In in) {
            int V = in.readInt();
            int dim= in.readInt();
            G= new EdgeWeightedGraph(V);
            labeled=new LinkedList <List<Integer>>();
            LinkedList labels= new LinkedList();
            double[][] coord = new double [V][dim];
            for (int v = 0;v<V; v++ ) {
                for(int j=0; j<dim; j++) {
                	coord[v][j]=in.readDouble();
                }
                String label= in.readString();
                    if(labels.contains(label)) {
                    	labeled.get(labels.indexOf(label)).add(v);
                    }
                    else {
                    	labels.add(label);
                    	List <Integer> l= new LinkedList <Integer>();
                    	labeled.add(l);
                    	labeled.get(labels.indexOf(label)).add(v);
                    	System.out.println(label);
                    }                
            }
             
            G.setCoordinates(coord);
            for (int w = 0; w < V; w++) {
                for (int v = 0;v<V; v++ ) {
                	if(v!=w) {
                	double weight=0;
                    for(int j=0; j<dim; j++) {
                    	weight= weight+Math.pow(G.getCoordinates()[v][j]-G.getCoordinates()[w][j],2);
                    }
                    weight=Math.sqrt(weight);
                    Edge e = new Edge(v, w, weight);
                    G.addEdge(e);
                	}
                }
            }
	    clusters= new LinkedList <List<Integer>>();
	}
	
    /**
	 * This method finds a specified number of clusters based on a MST.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * @param numberOfClusters number of expected clusters
	 */
	public void findClusters(int numberOfClusters){
		// TODO
		PrimMST arbore= new PrimMST(G);
		List<Edge> muchiiMST = new ArrayList<>();
		for(Edge e : arbore.edges()){
			muchiiMST.add(e);
		}

		Collections.sort(muchiiMST, (e1, e2) -> Double.compare(e2.weight(), e1.weight()));
		// folosim UF pentru a uni restul muchiilor
		UF reuniune = new UF(G.V());
		for (int i = numberOfClusters - 1; i < muchiiMST.size(); i++) {
			Edge e = muchiiMST.get(i);
			int v = e.either();
			int w = e.other(v);
			reuniune.union(v, w);
		}

		Map<Integer, List<Integer>> grupe = new TreeMap<>();
		for (int v = 0; v < G.V(); v++) {
			int rad = reuniune.find(v);
			grupe.computeIfAbsent(rad, k -> new ArrayList<>()).add(v);
		}

		clusters = new ArrayList<>(grupe.values());
	}
	
	/**
	 * This method finds clusters based on a MST and a threshold for the coefficient of variation.
	 *
	 * It is based in the idea that removing edges from a MST will create a
	 * partition into several connected components, which are the clusters.
	 * The edges are removed based on the threshold given. For further explanation see the exercise sheet.
	 *
	 * @param threshold for the coefficient of variation
	 */
	public void findClusters(double threshold){
		// TODO
		PrimMST arbore = new PrimMST(G);
		List<Edge> muchiiMST = new ArrayList<>();
		for (Edge e : arbore.edges()) {
			muchiiMST.add(e);
		}

		Collections.sort(muchiiMST, (e1, e2) -> Double.compare(e1.weight(), e2.weight()));

		List<Edge> muchiiSelectate = new ArrayList<>();
		for (Edge e : muchiiMST) {
			muchiiSelectate.add(e);
			double cv = coefficientOfVariation(muchiiSelectate);
			if (cv > threshold) {

				muchiiSelectate.remove(muchiiSelectate.size() - 1);
				break;
			}
		}

		UF reuniune = new UF(G.V());
		for (Edge e : muchiiSelectate) {
			int v = e.either();
			int w = e.other(v);
			reuniune.union(v, w);
		}

		Map<Integer, List<Integer>> grupe = new TreeMap<>();
		for (int v = 0; v < G.V(); v++) {
			int radacina = reuniune.find(v);
			grupe.computeIfAbsent(radacina, k -> new ArrayList<>()).add(v);
		}

		clusters = new ArrayList<>(grupe.values());
	}

	
	/**
	 * Evaluates the clustering based on a fixed number of clusters.
	 * @return array of the number of the correctly classified data points per cluster
	 */
	public int[] validation() {
		// TODO
		int k = clusters.size();
		int[] corect = new int[k];
		for (int i = 0; i < k; i++) {
			List<Integer> clusterPrev = clusters.get(i);
			List<Integer> etichetaAdev = labeled.get(i);
			int contor = 0;
			for (int v : clusterPrev) {
				if (etichetaAdev.contains(v)) {
					contor++;
				}
			}
			corect[i] = contor;
		}
		return corect;
	}
	
	/**
	 * Calculates the coefficient of variation.
	 * For the formula see exercise sheet.
	 * @param part list of edges
	 * @return coefficient of variation
	 */
	public double coefficientOfVariation(List <Edge> part) {
		// TODO
		int n = part.size();
		if (n == 0) return 0.0;
		double suma = 0.0;
		double sumaP = 0.0;
		// calculăm suma și suma pătratelor
		for (Edge e : part) {
			double g = e.weight();
			suma += g;
			sumaP += g * g;
		}
		double medie = suma / n;
		double varianta = sumaP / n - medie * medie;
		double abatere = Math.sqrt(varianta);
		// coeficient de variație = abatere standard / medie
		return abatere / medie;
	}
	
	/**
	 * Plots clusters in a two-dimensional space.
	 */
	public void plotClusters() {
		int canvas=800;
	    StdDraw.setCanvasSize(canvas, canvas);
	    StdDraw.setXscale(0, 15);
	    StdDraw.setYscale(0, 15);
	    StdDraw.clear(new Color(0,0,0));
		Color[] colors= {new Color(255, 255, 255), new Color(128, 0, 0), new Color(128, 128, 128), 
				new Color(0, 108, 173), new Color(45, 139, 48), new Color(226, 126, 38), new Color(132, 67, 172)};
	    int color=0;
		for(List <Integer> cluster: clusters) {
			if(color>colors.length-1) color=0;
		    StdDraw.setPenColor(colors[color]);
		    StdDraw.setPenRadius(0.02);
		    for(int i: cluster) {
		    	StdDraw.point(G.getCoordinates()[i][0], G.getCoordinates()[i][1]);
		    }
		    color++;
	    }
	    StdDraw.show();
	}
	

    public static void main(String[] args) {
		// FOR TESTING
    }
}

