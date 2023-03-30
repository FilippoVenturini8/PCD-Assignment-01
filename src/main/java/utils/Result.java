package utils;

public record Result(String filePath, int lines) implements Comparable<Result>{
    @Override
    public int compareTo(Result o) {
        return Integer.compare(o.lines, this.lines);
    }

    @Override
    public String toString() {
        return  "Lines : " + lines +
                " <= " + filePath;
    }
}
