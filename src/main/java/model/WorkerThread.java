package model;

import utils.Flag;
import utils.Result;
import utils.SynchronizedQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class WorkerThread extends Thread{
    private final SynchronizedQueue<String> files;
    private final SynchronizedQueue<Result> results;
    private Flag stopExecutionFlag;

    public WorkerThread(SynchronizedQueue<String> files, SynchronizedQueue<Result> results, Flag stopExecutionFlag){
        this.files = files;
        this.results = results;
        this.stopExecutionFlag = stopExecutionFlag;
    }

    @Override
    public void run() {
        while(!this.stopExecutionFlag.get()){
            final Optional<String> pathToProcess = files.remove();
            if(pathToProcess.isEmpty()){
                break;
            }
            final Result result = new Result(pathToProcess.get(), this.countLines(pathToProcess.get()));
            this.results.add(result);
        }
    }

    private int countLines(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            return lines;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
