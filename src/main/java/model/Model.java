package model;

import utils.SetupInfo;
import utils.Results;

public interface Model {

    Results getResults();

    void addObserver(ModelObserver observer);

    void notifyObservers(ModelObserver.Event event);

    SetupInfo getSetupInfo();
}
