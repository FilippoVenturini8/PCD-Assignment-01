package model;

import utils.*;

import java.util.LinkedList;
import java.util.List;

public class ModelImpl implements Model{
    private Results results;
    private final List<ModelObserver> observers = new LinkedList<>();
    private SetupInfo setupInfo;

    @Override
    public void init(SetupInfo setupInfo){
        this.setupInfo = setupInfo;
        this.results = new ResultsImpl(setupInfo.nFiles(), setupInfo.intervals(), setupInfo.lastInterval());
    }

    @Override
    public Results getResults() {
        return results;
    }

    @Override
    public void addObserver(ModelObserver observer){
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers(ModelObserver.Event event){
        for(ModelObserver observer : this.observers){
            switch (event){
                case RESULT_UPDATED -> observer.resultsUpdated();
                case COMPUTATION_ENDED -> observer.computationEnded();
            }
        }
    }

    @Override
    public SetupInfo getSetupInfo() {
        return setupInfo;
    }

}
