package utils;

import java.util.List;
import java.util.Map;

public interface SortedResultsList{
    List<Result> getResults();
    Map<Interval, Integer> getDistribution();
    void add(Result elem);
}
