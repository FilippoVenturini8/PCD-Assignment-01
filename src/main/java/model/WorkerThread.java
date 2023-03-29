package model;

import utils.Result;
import utils.SynchronizedQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class WorkerThread extends Thread{
    private final SynchronizedQueue<String> files;
    private final SynchronizedQueue<Result> results;

    public WorkerThread(SynchronizedQueue<String> files, SynchronizedQueue<Result> results){
        this.files = files;
        this.results = results;
    }

    @Override
    public void run() {
        while(true){
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
