package model;

import utils.*;

import java.util.LinkedList;
import java.util.List;

public class ModelImpl implements Model{
    private final SynchronizedQueue<String> files = new SynchronizedQueueImpl<>();
    private final SynchronizedQueue<Result> results = new SynchronizedQueueImpl<>();
    private final SortedResultsList sortedResults;
    private final List<ModelObserver> observers = new LinkedList<>();
    private final SetupInfo setupInfo;

    public ModelImpl(SetupInfo setupInfo){
        this.setupInfo = setupInfo;
        this.sortedResults = new SortedResultsListImpl(setupInfo.nFiles(), setupInfo.intervals(), setupInfo.lastInterval());
    }

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

    @Override
    public void addObserver(ModelObserver observer){
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers(){
        for(ModelObserver observer : this.observers){
            observer.resultsUpdated();
        }
    }

    @Override
    public SetupInfo getSetupInfo() {
        return setupInfo;
    }
}
