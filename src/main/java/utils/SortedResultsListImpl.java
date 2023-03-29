package utils;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class SortedResultsListImpl implements SortedResultsList{
    private final int nResults;
    private final Set<Result> set = new TreeSet<>();
    private final Lock mutex = new ReentrantLock();
    private final Map<Interval, Integer> distribution = new HashMap<>();

    public SortedResultsListImpl(int nResults, int nIntervals, int lastInterval){
        this.nResults = nResults;

    }

    @Override
    public List<Result> getResults() {
        try{
            this.mutex.lock();
            return this.set.stream().limit(this.nResults).collect(Collectors.toList());
        }finally {
            this.mutex.unlock();
        }
    }

    @Override
    public Map<Interval, Integer> getDistribution() {
        return null;
    }

    @Override
    public void add(Result elem) {
        try{
            this.mutex.lock();
            this.set.add(elem);
        }finally {
            this.mutex.unlock();
        }
    }
}
