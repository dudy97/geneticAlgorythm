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

    public void mutate(double prob) {
        this.cities.remove(this.cities.size()-1);
        Random r = new Random();
        for(int i = 0; i<cities.size(); i++){
            if(r.nextDouble() < prob) {
                Collections.swap(cities, i, r.nextInt(cities.size()-1));
            }
        }
        this.cities.add(this.cities.get(0));
    }

//    @Override
//    public String toString() {
//        return "Individual{" +
//                "cities=" + cities +
//                ", value=" + value +
//                '}';
//    }


    @Override
    public String toString() {
        return cities+"";
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(this.value, o.value);
    }

}
