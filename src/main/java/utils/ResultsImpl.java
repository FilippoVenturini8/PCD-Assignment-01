package utils;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ResultsImpl implements Results {
    private final int nResults;
    private final Set<Result> ranking = new TreeSet<>();
    private final Lock mutex = new ReentrantLock();
    private final Map<Interval, Integer> distribution = new HashMap<>();

    public ResultsImpl(int nResults, int nIntervals, int lastInterval){
        this.nResults = nResults;
        if(nIntervals == 1){
            distribution.put(new Interval(0, Integer.MAX_VALUE), 0);
        }else {
            final int intervalSize = lastInterval / (nIntervals - 1);
            for (int i = 0; i < nIntervals - 2; i++) {
                distribution.put(new Interval(intervalSize * i, intervalSize * (i + 1)), 0);
            }
            distribution.put(new Interval(intervalSize * (nIntervals - 2), lastInterval), 0);
            distribution.put(new Interval(lastInterval, Integer.MAX_VALUE), 0);
        }
    }

    @Override
    public List<Result> getRanking() {
        try{
            this.mutex.lock();
            return this.ranking.stream().limit(this.nResults).collect(Collectors.toList());
        }finally {
            this.mutex.unlock();
        }
    }

    @Override
    public Map<Interval, Integer> getDistribution() {
        return this.distribution;
    }

    @Override
    public void add(Result elem) {
        try{
            this.mutex.lock();
            this.ranking.add(elem);
            for(Map.Entry<Interval, Integer> entry : this.distribution.entrySet()){
                if(entry.getKey().contains(elem.lines())){
                    entry.setValue(entry.getValue() + 1);
                }
            }
        }finally {
            this.mutex.unlock();
        }
    }
}
