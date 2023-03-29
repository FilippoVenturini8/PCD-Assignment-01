package controller;

import model.MasterThread;
import model.Model;
import utils.Result;
import utils.SetupInfo;
import utils.SynchronizedQueue;
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
        new MasterThread(this, setupInfo, nWorkers).start();
    }

    @Override
    public SynchronizedQueue<Result> getResults() {
        return this.model.getResults();
    }

    @Override
    public SynchronizedQueue<String> getFiles() {
        return this.model.getFiles();
    }
}
