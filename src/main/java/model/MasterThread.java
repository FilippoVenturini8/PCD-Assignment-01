package model;

import controller.Controller;
import utils.Result;
import utils.SetupInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class MasterThread extends Thread{
    private final Controller controller;
    private final SetupInfo setupInfo;
    private final int nWorkers;

    public MasterThread(Controller controller, SetupInfo setupInfo, int nWorkers) {
        this.controller = controller;
        this.setupInfo = setupInfo;
        this.nWorkers = nWorkers;
    }

    @Override
    public void run() {
        this.searchFiles();

        for(int i = 0; i < this.nWorkers; i++){
            new WorkerThread(this.controller.getFiles(), this.controller.getResults()).start();
        }

        while (true){
            try {
                final Result result = this.controller.getResults().blockingRemove();
                this.controller.getSortedResults().add(result);
                System.out.println(this.controller.getSortedResults().get(10));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void searchFiles(){
        try (Stream<Path> walkStream = Files.walk(Paths.get(this.setupInfo.startDir()))) {
            walkStream.filter(p -> p.toFile().isFile() && p.toString().endsWith(".java"))
                    .limit(this.setupInfo.nFiles())
                    .map(Path::toString)
                    .forEach(p -> this.controller.getFiles().add(p));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
