package model;

import utils.Result;
import utils.SynchronizedQueue;

public interface Model {
    SynchronizedQueue<String> getFiles();

    SynchronizedQueue<Result> getResults();
}
