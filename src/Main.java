import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Loader l = new Loader();
        l.readFromFile("D:\\JA\\Intelli_projekty\\si1\\trivial_1.ttp");
        l.proceedLines();
        Individual i = new Individual(l.cities);
        System.out.println(i.cities);
        TSP tsp = new TSP(i.cities, l.items, l.capacityOfKnapsack);
        double time = tsp.countF();
        System.out.println(time);
        System.out.println("G= " + countG(tsp.itemsChosen, time));
    }

    static double countG(ArrayList<Item> itemsChosen, double time) {
        int value = 0;
        for(Item item : itemsChosen)
            value += item.profit;
        return value-time;
    }
}
