package view;

import controller.Controller;
import utils.Interval;
import utils.Result;

import java.util.Map;

public class ConsoleView implements View{
    private Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void resultsUpdated() {
    }

    @Override
    public void computationEnded() {
        System.out.println("Files ranking:");
        for(Result result : this.controller.getResults().getRanking()){
            System.out.println(result.filePath() + " has: " + result.lines() + " lines.");
        }
        System.out.println("Files distribution:");
        for(Map.Entry<Interval, Integer> entry : this.controller.getResults().getDistribution().entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
