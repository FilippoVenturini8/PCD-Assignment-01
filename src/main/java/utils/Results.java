package utils;

import java.util.List;
import java.util.Map;

public interface Results {
    List<Result> getRanking();
    Map<Interval, Integer> getDistribution();
    void add(Result elem);
}
