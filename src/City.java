public class City {
    int index;
    double x, y;

    public City(int index, double x, double y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "City{" +
                "index=" + index +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
