import java.awt.font.NumericShaper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        Loader l = new Loader();
        l.readFromFile("D:\\JA\\Intelli_projekty\\si1\\files\\trivial_1.ttp");
        l.proceedLines();
        ArrayList<City> temp = new ArrayList<>(l.cities.size());
        temp.addAll(l.cities);
        Individual i = new Individual(temp);
        l.shuffle();
        Individual i2 = new Individual(l.cities);
        TSP tsp = new TSP(i.cities, l.items, l.capacityOfKnapsack);
        double time = tsp.countF();
        System.out.println("Przed: ");
        System.out.println(i.cities);
        System.out.println(i2.cities);
        crossover(i, i2);
        System.out.println("Po:");
        System.out.println(i.cities);
        System.out.println(i2.cities);
    }

    static double countG(ArrayList<Item> itemsChosen, double time) {
        int value = 0;
        for(Item item : itemsChosen)
            value += item.profit;
        return value-time;
    }

    public static void crossover(Individual i1, Individual i2) {

        int size = i1.cities.size();
        int idx1 = ThreadLocalRandom.current().nextInt(size/4, size/2);
        int idx2 = ThreadLocalRandom.current().nextInt(size/2, (int) size*3/4);

//        int idx1 = 3;
//        int idx2 = 6;

        final int start = Math.min(idx1, idx2);
        final int end = Math.max(idx2, idx1);

        ArrayList<City> temp1 = new ArrayList<City>(i1.cities.subList(idx1, idx2));
        ArrayList<City> temp2 = new ArrayList<City>(i2.cities.subList(idx1, idx2));

        int currentIndex = 0;
        City currentCityInI1 = null;
        City currentCityInI2 = null;

        for(int i=0; i<size; i++) {
            currentIndex = (end + i) % size;
            currentCityInI1 = i1.cities.get(currentIndex);
            currentCityInI2 = i2.cities.get(currentIndex);

            if(!temp1.contains(currentCityInI2)){
                temp1.add(currentCityInI2);
            }

            if(!temp2.contains(currentCityInI1)){
                temp2.add(currentCityInI1);
            }
        }

        Collections.rotate(temp1, start);
        Collections.rotate(temp2, start);

        temp1.add(temp1.get(0));
        temp2.add(temp2.get(0));

        Collections.copy(i1.cities, temp1);
        Collections.copy(i2.cities, temp2);

    }

}
