import java.util.ArrayList;

public class TSP {
    ArrayList<City> cities;
    ArrayList<Item> items;
    ArrayList<Item> itemsChosen;
    int capacity;



    public TSP(ArrayList<City> cities) {
        this.cities = cities;
    }

    public TSP(ArrayList<City> cities, ArrayList<Item> items) {
        this.cities = cities;
        this.items = items;
    }

    public TSP(ArrayList<City> cities, ArrayList<Item> items, int capacity) {
        itemsChosen = new ArrayList<>();
        this.cities = cities;
        this.items = items;
        this.capacity = capacity;
    }

    double countF() {
        double time = 0;
        int weight = 0;
        for(int i=0; i<cities.size()-1; i++) {
            City city1 = cities.get(i);
            City city2 = cities.get(i+1);
            double dist = countDist(city1.x, city1.x, city2.x, city2.y);
            for (Item item : items) {
                if(item.assignNodeNumber==city1.index) {
                    if(item.weight+weight<=capacity) {
                        System.out.println(city1);
                        weight += item.weight;
                        System.out.println("Weight= " + weight);
                        itemsChosen.add(item);
                    }
                    break;
                }
            }
            time+=dist/(1-(weight*(0.9/capacity)));
        }
        return time;
   }

   double countDist(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1 , 2));
   }
}
