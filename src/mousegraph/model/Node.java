package mousegraph.model;

public class Node {
    private String name;
    private double wt;

    public Node(String name, double wt) {
        this.name = name;
        this.wt = wt;
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

}
