package controller;

import model.MasterThread;
import model.Model;
import model.ModelObserver;
import utils.*;
import view.View;

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
        this.model.init(setupInfo);
        new MasterThread(this, nWorkers).start();
    }

    @Override
    public Results getResults(){
        return this.model.getResults();
    }

    @Override
    public void notifyObservers(ModelObserver.Event event){
        this.model.notifyObservers(event);
    }

    @Override
    public SetupInfo getSetupInfo(){
        return this.model.getSetupInfo();
    }
}
