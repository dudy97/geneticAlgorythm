import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.awt.font.NumericShaper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static javax.swing.text.html.HTML.Tag.HEAD;

public class Main {
    private static final int  NUMBER_OF_ITERATIONS= 100;
    private static final int  POPULATION= 100;
    private static final int  TOURNAMENT_SIZE= 5;
    private static final double  CHANCE_OF_CROSSOVER= 0.7;
    private static final double  CHANCE_OF_MUTATION= 0.01;

    public static void main(String[] args) {
        Loader l = new Loader();
        l.readFromFile("D:\\JA\\Intelli_projekty\\si1\\files\\hard_3.ttp");
        l.proceedLines();
        ArrayList<Individual> population = new ArrayList<>();
        ArrayList<String> logs = new ArrayList<>();

        for(int i=0; i<POPULATION; i++) {
            ArrayList<City> cities = new ArrayList<>();
            cities = copyArray(cities, l.cities);
            Individual ind = new Individual(cities);
            population.add(ind);
            l.shuffle();
        }
        for(int i=0; i<population.size(); i++){
            population.get(i).setValue(countVal(population.get(i).cities, l.items, l.capacityOfKnapsack));
        }
        int iter=1;
        Random random = new Random();
        ArrayList<Individual> newPopulation = new ArrayList<>();
        Individual bestInd = new Individual();
        bestInd.setValue(-100000);

        while(iter<NUMBER_OF_ITERATIONS) {
            tournamentSelection(population, newPopulation, random, TOURNAMENT_SIZE, l);
//            ruletteSelection(population, newPopulation, random, l);
            for (int i = 0; i < newPopulation.size() - 1; i=i+2) {
                if (random.nextDouble() < CHANCE_OF_CROSSOVER) {
                    crossover(newPopulation, i , i+1);
                    newPopulation.get(i).setValue(countVal(newPopulation.get(i).cities, l.items, l.capacityOfKnapsack));
                    newPopulation.get(i+1).setValue(countVal(newPopulation.get(i+1).cities, l.items, l.capacityOfKnapsack));
                }
            }
            for (int i = 0; i < newPopulation.size(); i++) {
                newPopulation.get(i).mutate(CHANCE_OF_MUTATION);
                newPopulation.get(i).setValue(countVal(newPopulation.get(i).cities, l.items, l.capacityOfKnapsack));

            }

            double fitness = countVal(Collections.max(newPopulation).cities, l.items, l.capacityOfKnapsack);
//            if(iter%10==0) {
                System.out.println("Iteracja nr. " + iter);
                System.out.println(fitness);
                System.out.println(Collections.max(newPopulation));
//            }
            logs.add(iter + "," + Math.round(fitness));
            saveToFile("hard_3_20190316", logs);
            Collections.copy(population , newPopulation);
            newPopulation.clear();
            iter++;
        }
    }

    public static void saveToFile(String fileName, ArrayList<String> logs){
        try (PrintWriter writer = new PrintWriter(new File(fileName + ".csv"))) {

            StringBuilder sb = new StringBuilder();
            for(String s : logs) {
                sb.append(s);
                sb.append('\n');
            }

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void ruletteSelection(ArrayList<Individual> pop, ArrayList<Individual> newPop, Random rng, Loader l) {
        Double worst = (Collections.min(pop).value < 0)?  Collections.min(pop).value * (-1) : 0;
        ArrayList<Double> fitness = new ArrayList<>();
        fitness.add(pop.get(0).value + worst);
        for (int i = 1; i < pop.size(); i++)
        {
            double fit = pop.get(i).value + worst;
            fitness.add(fitness.get(i-1) + fit);
        }
        for (int i = 0; i < pop.size(); i++)
        {
            double randomFitness = rng.nextDouble() * fitness.get(fitness.size()-1);
            int index = Collections.binarySearch(fitness, randomFitness);
            if (index < 0)
            {
                // Convert negative insertion point to array index.
                index = Math.abs(index + 1);
            }
            ArrayList<City> cities = new ArrayList<>();
            cities = copyArray(cities, pop.get(index).cities);
            Individual newI = new Individual(cities);
            newI.setValue(countVal(newI.cities, l.items, l.capacityOfKnapsack));
            newPop.add(newI);
        }
    }

    public static void tournamentSelection(ArrayList<Individual> population, ArrayList<Individual> newPopulation, Random random, int tournamentSize, Loader l) {
        ArrayList<Individual> selection = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            ArrayList<Individual> toAdd = new ArrayList<>();
            for (int j = 0; j < tournamentSize; j++) {
                int idx = random.nextInt(population.size() - 1);
                Individual tempI = population.get(idx);
                toAdd.add(tempI);
                population.remove(idx);
                Individual indForAdd = new Individual();
                indForAdd.setCities(tempI.getCities());
                indForAdd.setValue(countVal(tempI.getCities(), l.items, l.capacityOfKnapsack));
                selection.add(indForAdd);
            }
            population.addAll(toAdd);
            Collections.sort(selection);
            ArrayList<City> cities = new ArrayList<>();
            cities = copyArray(cities, selection.get(selection.size()-1).cities);
            Individual newI = new Individual(cities);
            newI.setValue(countVal(newI.cities, l.items, l.capacityOfKnapsack));
            newPopulation.add(newI);
            selection.clear();
        }
    }


    static double countG(ArrayList<Item> itemsChosen, double time) {
        int value = 0;
        for(Item item : itemsChosen)
            value += item.profit;
        return value-time;
    }

    public static void crossover(ArrayList<Individual> individualArrayList, int i1, int i2) {
        int size = individualArrayList.get(i1).cities.size();
        int idx1 = ThreadLocalRandom.current().nextInt(size/4, size/2);
        int idx2 = ThreadLocalRandom.current().nextInt(size/2, (int) size*3/4);

        final int start = Math.min(idx1, idx2);
        final int end = Math.max(idx2, idx1);

        ArrayList<City> temp1 = new ArrayList<City>(individualArrayList.get(i1).cities.subList(idx1, idx2));
        ArrayList<City> temp2 = new ArrayList<City>(individualArrayList.get(i2).cities.subList(idx1, idx2));

        int currentIndex = 0;
        City currentCityInI1 = null;
        City currentCityInI2 = null;

        for(int i=0; i<size; i++) {
            currentIndex = (end + i) % size;
            currentCityInI1 = individualArrayList.get(i1).cities.get(currentIndex);
            currentCityInI2 = individualArrayList.get(i2).cities.get(currentIndex);

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

        Collections.copy(individualArrayList.get(i1).cities, temp1);
        Collections.copy(individualArrayList.get(i2).cities, temp2);
    }

    public static void printTab(ArrayList<Individual> tab){
        for(Individual i : tab)
            System.out.println(i);
    }

    public static double countAvg(ArrayList<Individual> list){
        double sum=0;
        for(Individual i : list)
            sum+=i.getValue();
        return sum/list.size();
    }

    public static double countVal(ArrayList<City> cities, ArrayList<Item> items, int capacity){
        TSP tsp = new TSP(cities, items, capacity);
        double t = tsp.countF();
        return countG(tsp.itemsChosen, t);
    }

    public static ArrayList<City> copyArray(ArrayList<City> arrDest, ArrayList<City> arrSource){
        for(int i=0; i<arrSource.size(); i++)
            arrDest.add(new City(arrSource.get(i).index, arrSource.get(i).x, arrSource.get(i).y));
        return arrDest;
    }

}
