package model;

import utils.Result;
import utils.SynchronizedQueue;
import utils.SynchronizedQueueImpl;

public class ModelImpl implements Model{
    private final SynchronizedQueue<String> files = new SynchronizedQueueImpl<>();
    private final SynchronizedQueue<Result> results = new SynchronizedQueueImpl<>();

    @Override
    public SynchronizedQueue<String> getFiles() {
        return files;
    }

    @Override
    public SynchronizedQueue<Result> getResults() {
        return results;
    }
}
