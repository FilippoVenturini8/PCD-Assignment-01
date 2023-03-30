package model;

public interface ModelObserver {
    enum Event{
        RESULT_UPDATED, COMPUTATION_ENDED
    }
    void resultsUpdated();
    void computationEnded();
}
