public class Triangle extends ConvexPolygon {

    public Triangle(Vector2D a, Vector2D b, Vector2D c) {
        super(new Vector2D[]{a, b, c});
    }

    // Modificare: Copiere superficială (shallow copy)
    public Triangle(Triangle triangle) {
        super(triangle.vertices.clone()); // Copiază array-ul, dar păstrează referințele la Vector2D
    }

    // Restul codului rămâne neschimbat
    @Override
    public double area() {
        double x1 = vertices[0].getX();
        double y1 = vertices[0].getY();
        double x2 = vertices[1].getX();
        double y2 = vertices[1].getY();
        double x3 = vertices[2].getX();
        double y3 = vertices[2].getY();
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2);
    }

    @Override
    public String toString() {
        return "Triangle{[" + vertices[0] + ", " + vertices[1] + ", " + vertices[2] + "]}";
    }


    public static void main(String[] args) {
        Vector2D a = new Vector2D(0, 0);
        Vector2D b = new Vector2D(10, 0);
        Vector2D c = new Vector2D(5, 5);
        Triangle triangle = new Triangle(a, b, c);
        double area = triangle.area();
        System.out.printf("Die Fläche des Dreiecks 'triangle' {%s, %s, %s} beträgt %.1f LE^2.\n", a, b, c, area);

        Triangle triangle2 = new Triangle(triangle);
        System.out.println("triangle2 ist eine Kopie per Copy-Konstruktor von 'triangle': " + triangle2);
        a.setX(-5);
        System.out.println("Eckpunkt 'a', der zur Definition von 'triangle' verwendet wurde, wird geändert.");
        System.out.println("Nun ist der Wert von 'triangle2': " + triangle2);
        /*
         * HINWEIS: Machen Sie sich keine Sorgen um etwaige Abweichungen in den letzten
         * Nachkommastellen bei der Berechnung des Flächeninhalts.
         * Diese werden in den automatisierten Tests herausgerechnet.
         * Die erwartete Ausgabe ist:
         * Die Fläche des Dreiecks 'triangle' {(0.0, 0.0), (10.0, 0.0), (5.0, 5.0)}
         * beträgt 25,0 LE^2.
         * triangle2 ist eine Kopie per Copy-Konstruktor von 'triangle': Triangle{[(0.0,
         * 0.0), (10.0, 0.0), (5.0, 5.0)]}
         * Eckpunkt 'a', der zur Definition von 'triangle' verwendet wurde, wird
         * geändert.
         * Nun ist der Wert von 'triangle2': Triangle{[(-5.0, 0.0), (10.0, 0.0), (5.0,
         * 5.0)]}
         */
    }
}
