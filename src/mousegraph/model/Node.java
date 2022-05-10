package mousegraph.model;

public class Node {
    private String name;
    private double wt;
    private Point point;

    public Node(String name, double wt, Point point) {
        this.name = name;
        this.wt = wt;
        this.point = point;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String getName() {
        return this.name;
    }

    public void setWt(double s) {
        this.wt = s;
    }

    public double getWt() {
        return this.wt;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
