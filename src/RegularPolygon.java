// Diese Klasse implementiert nur *zentrierte* reguläre Polygone, also mit midpoint = (0, 0).

public class RegularPolygon extends ConvexPolygon {

    // TODO
    private int N;
    private double radius;


    public RegularPolygon(int N, double radius) {
        // TODO
        super(new Vector2D[N]);
        this.N = N;
        this.radius = radius;

        for(int i = 0; i < N; i++){
            double angle = 2 * Math.PI * i / N;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            vertices[i] = new Vector2D(x, y);
        }

    }

    public RegularPolygon(RegularPolygon polygon) {
        // TODO
        this(polygon.N, polygon.radius);

    }

    public void resize(double newradius) {
        // TODO
        this.radius = newradius;

        for(int i = 0; i < N; i++){
            double angle = 2 * Math.PI * i / N;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            vertices[i] = new Vector2D(x, y);
        }
    }

    @Override
    public String toString() {
        // TODO
        return "RegularPolygon{N=" + N + ", radius=" + radius + "}";
    }

    public static void main(String[] args) {
        RegularPolygon pentagon = new RegularPolygon(5, 1);
        System.out.println("Der Flächeninhalt des " + pentagon + " beträgt " + pentagon.area() + " LE^2.");
        // RegularPolygon otherpentagon = pentagon; // Dies funktioniert nicht!
        RegularPolygon otherpentagon = new RegularPolygon(pentagon);
        pentagon.resize(10);
        System.out.println("Nach Vergrößerung: " + pentagon + " mit Fläche " + pentagon.area() + " LE^2.");
        System.out.println("Die Kopie: " + otherpentagon + " mit Fläche " + otherpentagon.area() + " LE^2.");
        /*
         * HINWEIS: Machen Sie sich keine Sorgen um etwaige Abweichungen in den letzten
         * Nachkommastellen bei der Berechnung des Flächeninhalts.
         * Diese werden in den automatisierten Tests herausgerechnet.
         * Die erwartete Ausgabe ist:
         * Der Flächeninhalt des RegularPolygon{N=5, radius=1.0} beträgt
         * 2.377641290737883 LE^2.
         * Nach Vergrößerung: RegularPolygon{N=5, radius=10.0} mit Fläche
         * 237.7641290737884 LE^2.
         * Die Kopie: RegularPolygon{N=5, radius=1.0} mit Fläche 2.377641290737883 LE^2.
         */
    }
}

