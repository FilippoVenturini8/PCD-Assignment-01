package controller;

import utils.Result;
import utils.SetupInfo;
import utils.SynchronizedQueue;

public interface Controller {
    void start(SetupInfo setupInfo, int nWorkers);
    SynchronizedQueue<Result> getResults();
    SynchronizedQueue<String> getFiles();
}
