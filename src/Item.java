public class Item {
    int index, profit, weight, assignNodeNumber;

    public Item(int index, int profit, int weight, int assignNodeNumber) {
        this.index = index;
        this.profit = profit;
        this.weight = weight;
        this.assignNodeNumber = assignNodeNumber;
    }

    @Override
    public String toString() {
        return "Item{" +
                "index=" + index +
                ", profit=" + profit +
                ", weight=" + weight +
                ", assignNodeNumber=" + assignNodeNumber +
                '}';
    }
}
