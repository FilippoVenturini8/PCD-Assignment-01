import controller.Controller;
import controller.ControllerImpl;
import model.Model;
import model.ModelImpl;
import utils.SetupInfo;
import view.ConsoleView;
import view.View;

public class Assignment1 {
    public static void main(String[] args){
        final SetupInfo setupInfo = new SetupInfo("D:\\Progetti\\Hangman_Online", 10000, 10, 100);

        final Model model = new ModelImpl(setupInfo);
        final View view = new ConsoleView();
        final Controller controller = new ControllerImpl(model, view);

        model.addObserver(view);

        controller.start(setupInfo, 2);
    }
}
