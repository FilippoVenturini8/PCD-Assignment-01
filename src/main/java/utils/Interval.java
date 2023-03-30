package utils;

public record Interval(int min, int max) {
    public boolean contains(int value){
        return value >= min && value < max;
    }

    @Override
    public String toString() {
        return "[" + min +
                ", " + max +
                ']';
    }
}
