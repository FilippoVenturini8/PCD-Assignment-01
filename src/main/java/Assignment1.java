import controller.Controller;
import controller.ControllerImpl;
import model.Model;
import model.ModelImpl;
import utils.SetupInfo;
import view.ConsoleView;
import view.GuiView;
import view.View;

import static model.MasterThread.N_WORKERS;

public class Assignment1 {
    public static void main(String[] args){
        final SetupInfo setupInfo = new SetupInfo("D:\\Progetti\\Hangman_Online", 10000, 10, 100);

        final Model model = new ModelImpl();
        final View view = new GuiView();
        final Controller controller = new ControllerImpl(model, view);

        model.addObserver(view);

        //controller.start(setupInfo, N_WORKERS);
    }
}
