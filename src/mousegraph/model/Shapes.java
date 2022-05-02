package mousegraph.model;

import java.awt.geom.GeneralPath;

public class Shapes {
    public static GeneralPath plus(int x, int y) {
        int[] x1Points = {x - 2, x - 2, x + 2, x + 2, x + 17, x + 17, x + 2, x + 2, x - 2, x - 2, x - 17, x - 17};
        int[] y1Points = {y + 2, y + 17, y + 17, y + 2, y + 2, y - 2, y - 2, y - 17, y - 17, y - 2, y - 2, y + 2};
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);
        for (int i = 1; i < 12; i++) {
            polygon.lineTo(x1Points[i], y1Points[i]);
        }
        polygon.closePath();
        return polygon;
    }

    public static GeneralPath cross(int x, int y) {
        int[] x1Points = {x - 2, x - 16, x - 16, x, x + 16, x + 16, x + 2, x + 16, x + 16, x, x - 16, x - 16};
        int[] y1Points = {y, y + 16, y + 16, y + 2, y + 16, y + 16, y, y - 16, y - 16, y - 2, y - 16, y - 16};
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);
        for (int i = 1; i < x1Points.length; i++) {
            polygon.lineTo(x1Points[i], y1Points[i]);
        }
        polygon.closePath();
        return polygon;
    }

    public static GeneralPath triangle(int x, int y) {
        int[] x1Points = {x, x + 7, x - 7};
        int[] y1Points = {y + 7, y - 7, y - 7};
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);
        for (int i = 1; i < x1Points.length; i++) {
            polygon.lineTo(x1Points[i], y1Points[i]);
        }
        polygon.closePath();
        return polygon;
    }
}
