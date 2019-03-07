import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Loader {
    ArrayList<String> lines;
    ArrayList<City> cities;
    ArrayList<Item> items;
    int cityNum, itemsNum, capacityOfKnapsack, maxSpeed;
    double minSpeed;

    public Loader() {
        this.lines = new ArrayList<>();
        this.cities = new ArrayList<>();
        this.items = new ArrayList<>();
        maxSpeed = 1;
        minSpeed = 0.1;
    }

    public ArrayList<String> readFromFile(String path) {
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return lines;
    }

    public void proceedLines() {
        cityNum = Integer.parseInt(lines.get(2).substring(11));
        itemsNum = Integer.parseInt(lines.get(3).substring(18));
        capacityOfKnapsack = Integer.parseInt(lines.get(4).substring(23));
        for(int i = 10; i<10+cityNum; i++) {
            String[] lane = lines.get(i).split("\t");
            int index = Integer.parseInt(lane[0]);
            double x = Double.parseDouble(lane[1]);
            double y = Double.parseDouble(lane[2]);
            cities.add(new City(index, x, y));
        }
        Collections.shuffle(cities);
        cities.add(cities.get(0));
        for(int i = 11 + cityNum; i<11 + cityNum + itemsNum; i++) {
            String[] lane = lines.get(i).split("\t");
            int index = Integer.parseInt(lane[0]);
            int profit = Integer.parseInt(lane[1]);
            int weight = Integer.parseInt(lane[2]);
            int assignNodeNumber = Integer.parseInt(lane[3]);
            items.add(new Item(index, profit, weight, assignNodeNumber));
        }

    }

    public void shuffle() {
        cities.remove(cities.size()-1);
        Collections.shuffle(cities);
        cities.add(cities.get(0));
    }



}
