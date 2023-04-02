package controller;

import model.ModelObserver;
import utils.*;

public interface Controller {
    void start(SetupInfo setupInfo, int nWorkers);

    Results getResults();

    void notifyObservers(ModelObserver.Event event);

    SetupInfo getSetupInfo();

    void stopExecution();

    Flag getStopExecutionFlag();

    void processEvent(Runnable runnable);
}
