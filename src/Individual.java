import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Individual implements Comparable<Individual>{
    ArrayList<City> cities;
    double value;

    public Individual() {
    }

    public Individual(ArrayList<City> cities) {
        this.cities = cities;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public void mutate() {
        this.cities.remove(this.cities.size()-1);
        Random r = new Random();
        int index1 = r.nextInt(cities.size()-1);
        int index2 = r.nextInt(cities.size()-1);
        while(index1==index2)
            index2 = r.nextInt(cities.size()-1);
        Collections.swap(cities, index1, index2);
        this.cities.add(this.cities.get(0));
    }

    @Override
    public String toString() {
        return "Individual{" +
                "cities=" + cities +
                ", value=" + value +
                '}';
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(this.value, o.value);
    }

}
