import java.util.ArrayList;
import java.util.Collections;


public class Individual {
    ArrayList<City> cities;

    public Individual() {
    }

    public Individual(ArrayList<City> cities) {
        this.cities = cities;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public void mutate() {
        int index1 = (int) Math.round(Math.random()*cities.size());
        int index2 = (int) Math.round(Math.random()*cities.size());
        while(index1==index2)
            index2 = (int) Math.round(Math.random()*cities.size());
        Collections.swap(cities, index1, index2);
    }
}
