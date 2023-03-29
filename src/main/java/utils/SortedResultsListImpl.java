package utils;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class SortedResultsListImpl implements SortedResultsList{
    private final Set<Result> set = new TreeSet<>();
    private final Lock mutex = new ReentrantLock();

    @Override
    public List<Result> get(int nElem) {
        try{
            this.mutex.lock();
            return this.set.stream().limit(nElem).collect(Collectors.toList());
        }finally {
            this.mutex.unlock();
        }
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
