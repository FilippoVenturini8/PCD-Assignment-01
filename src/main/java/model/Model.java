package model;

import utils.Flag;
import utils.SetupInfo;
import utils.Results;

public interface Model {

    void init(SetupInfo setupInfo);

    Results getResults();

    void addObserver(ModelObserver observer);

    void notifyObservers(ModelObserver.Event event);

    SetupInfo getSetupInfo();

    Flag getStopExecutionFlag();
}
