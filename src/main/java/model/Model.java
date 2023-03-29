package model;

import utils.Result;
import utils.SortedResultsList;
import utils.SynchronizedQueue;

public interface Model {
    SynchronizedQueue<String> getFiles();

    SynchronizedQueue<Result> getResults();

    SortedResultsList getSortedResults();

    void addObserver(ModelObserver observer);

    void notifyObservers();
}
