package mousegraph.logic;

import mousegraph.model.Point;

public class Check {
    public static boolean inLine(Point A, Point B, Point C) {
        double distAB = Math.sqrt((A.getX() + 7 - B.getX()) * (A.getX() + 7 - B.getX()) + (A.getY() + 7 - B.getY()) * (A.getY() + 7 - B.getY()));
        double distAC = Math.sqrt((A.getX() - C.getX()) * (A.getX() - C.getX()) + (A.getY() - C.getY()) * (A.getY() - C.getY()));
        double distBC = Math.sqrt((C.getX() + 7 - B.getX()) * (C.getX() + 7 - B.getX()) + (C.getY() + 7 - B.getY()) * (C.getY() + 7 - B.getY()));
        return Math.abs(distAB + distBC - distAC) < 0.3f;
    }
}


