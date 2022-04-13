package yoon.hw;

public class Item {
    private int idx;
    private String item;

    public Item(int idx, String item) {
        this.idx = idx;
        this.item = item;
    }

    @Override
    public String toString() {
        return idx + "\t" + item;
    }
}
