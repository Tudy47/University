import java.util.Arrays;


public class ConvexPolygon extends Polygon {
    // TODO
        public ConvexPolygon(Vector2D[] vertices) {
            super();
            this.vertices = vertices;
        }


    @Override
    public double perimeter() {
        double perimeter = 0;
        for(int i = 0; i < vertices.length; i++){
            int next = (i+1)%vertices.length;
            double dx = vertices[i].getX() - vertices[next].getX();
            double dy = vertices[i].getY() - vertices[next].getY();
            perimeter += Math.sqrt(dx * dx + dy * dy);
        }
        return perimeter;
    }

    @Override
    public double area() {
    double totalArea = 0;

        for(int i = 1; i < vertices.length - 1; i++){
        Triangle triangle = new Triangle(vertices[0], vertices[i], vertices[i+1]);
        totalArea += triangle.area();
        }
        return totalArea;
    }

    public static double totalArea(ConvexPolygon[] convexPolygons) {
        double totalArea = 0;
        for(ConvexPolygon polygon : convexPolygons){
            totalArea += polygon.area();
        }
        return totalArea;
    }


    @Override
    public String toString() {
        return "ConvexPolygon{" + "vertices=" + Arrays.toString(vertices) + '}';
    }
}


