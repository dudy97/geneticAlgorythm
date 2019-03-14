import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return index == city.index &&
                Double.compare(city.x, x) == 0 &&
                Double.compare(city.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, x, y);
    }
}
