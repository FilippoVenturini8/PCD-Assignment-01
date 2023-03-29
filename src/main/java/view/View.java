package view;

import controller.Controller;
import model.ModelObserver;

public interface View extends ModelObserver {
    void setController(Controller controller);
}
