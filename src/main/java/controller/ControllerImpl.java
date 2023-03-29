package controller;

import model.MasterThread;
import model.Model;
import utils.*;
import view.View;

import java.util.Map;

public class ControllerImpl implements Controller{
    private final Model model;
    private final View view;

    public ControllerImpl(Model model, View view){
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    @Override
    public void start(SetupInfo setupInfo, int nWorkers) {
        new MasterThread(this, nWorkers).start();
    }

    @Override
    public SynchronizedQueue<Result> getResults() {
        return this.model.getResults();
    }

    @Override
    public SynchronizedQueue<String> getFiles() {
        return this.model.getFiles();
    }
    @Override
    public SortedResultsList getSortedResults(){
        return this.model.getSortedResults();
    }

    @Override
    public void notifyObservers(){
        this.model.notifyObservers();
    }

    @Override
    public SetupInfo getSetupInfo(){
        return this.model.getSetupInfo();
    }
}
