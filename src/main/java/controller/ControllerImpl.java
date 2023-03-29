package controller;

import model.MasterThread;
import model.Model;
import model.ModelImpl;
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
    public void start() {
        new MasterThread().start();
    }
}
