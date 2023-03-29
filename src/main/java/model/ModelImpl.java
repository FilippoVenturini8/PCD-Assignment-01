package model;

import utils.*;

import java.util.HashMap;

public class ModelImpl implements Model{
    private final SynchronizedQueue<String> files = new SynchronizedQueueImpl<>();
    private final SynchronizedQueue<Result> results = new SynchronizedQueueImpl<>();
    private final SortedResultsList sortedResults = new SortedResultsListImpl();
    @Override
    public SynchronizedQueue<String> getFiles() {
        return files;
    }

    @Override
    public SynchronizedQueue<Result> getResults() {
        return results;
    }

    @Override
    public SortedResultsList getSortedResults() {
        return sortedResults;
    }
}
