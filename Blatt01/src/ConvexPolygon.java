

import java.util.Arrays;

public class ConvexPolygon extends Polygon {

    public ConvexPolygon(Vector2D[] vertices) {
        this.vertices = Arrays.copyOf(vertices, vertices.length);
    }

    @Override
    public double perimeter() {
        double perimeter = 0.0;
        for (int i = 0; i < vertices.length; i++) {
            Vector2D current = vertices[i];
            Vector2D next = vertices[(i + 1) % vertices.length];
            perimeter += Math.hypot(
                    current.getX() - next.getX(),
                    current.getY() - next.getY()
            );
        }
        return perimeter;
    }

    @Override
    public double area() {
        double totalArea = 0.0;
        for (int i = 1; i < vertices.length - 1; i++) {
            Triangle triangle = new Triangle(vertices[0], vertices[i], vertices[i + 1]);
            totalArea += triangle.area();
        }
        return totalArea;
    }

    public static Polygon[] somePolygons() {
        return new Polygon[]{
                new Triangle(
                        new Vector2D(0, 0),
                        new Vector2D(10, 0),
                        new Vector2D(5, 5)
                ),
                new Tetragon(
                        new Vector2D(0, 0),
                        new Vector2D(10, -5),
                        new Vector2D(12, 2),
                        new Vector2D(3, 17)
                ),
                new RegularPolygon(5, 1),
                new RegularPolygon(6, 1)
        };
    }

    public static double totalArea(Polygon[] polygons) {
        double total = 0.0;
        for (Polygon p : polygons) {
            total += p.area();
        }
        return total;
    }

    @Override
    public String toString() {
        return "ConvexPolygon([" + Arrays.toString(vertices) + "])";
    }
}





