package controller;

import utils.*;

import java.util.Map;

public interface Controller {
    void start(SetupInfo setupInfo, int nWorkers);
    SynchronizedQueue<Result> getResults();
    SynchronizedQueue<String> getFiles();

    SortedResultsList getSortedResults();

    void notifyObservers();

    SetupInfo getSetupInfo();
}
