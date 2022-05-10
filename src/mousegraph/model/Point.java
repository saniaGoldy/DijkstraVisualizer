package mousegraph.model;

public record Point(int x, int y) {

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
