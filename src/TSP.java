import java.util.ArrayList;
import java.util.logging.Logger;

public class TSP {
    ArrayList<City> cities;
    ArrayList<Item> items;
    ArrayList<Item> itemsChosen;
    int capacity;
    protected final Logger log;


    public TSP(ArrayList<City> cities) {
        this.cities = cities;
        log = Logger.getLogger(getClass().getName());
    }

    public TSP(ArrayList<City> cities, ArrayList<Item> items) {
        this.cities = cities;
        this.items = items;
        log = Logger.getLogger(getClass().getName());
    }

    public TSP(ArrayList<City> cities, ArrayList<Item> items, int capacity) {
        itemsChosen = new ArrayList<>();
        this.cities = cities;
        this.items = items;
        this.capacity = capacity;
        log = Logger.getLogger(getClass().getName());
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    double countF() {
        double time = 0;
        int weight = 0;
        for(int i=0; i<cities.size()-1; i++) {
            City city1 = cities.get(i);
            City city2 = cities.get(i+1);
            double dist = countDist(city1.x, city1.y, city2.x, city2.y);
            Item bestItem = new Item(-1, Integer.MAX_VALUE);
            for (Item item : items) {
                if(item.assignNodeNumber==city1.index) {
                    if(item.weight+weight<=capacity) {
                        if (((double) item.profit / item.weight) > ((double) bestItem.profit / bestItem.weight))
                            bestItem = item;
                    }
                }
            }
            if(bestItem.profit != -1){
                weight += bestItem.weight;
                itemsChosen.add(bestItem);
            }
            time+=dist/(1-(weight*(0.9/capacity)));
        }
        assert weight <= capacity;
        return time;
   }

   double countDist(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1 , 2));
   }
}
