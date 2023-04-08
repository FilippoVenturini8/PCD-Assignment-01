import controller.Controller;
import controller.ControllerImpl;
import model.Model;
import model.ModelImpl;
import view.GuiView;
import view.View;

public class Assignment1_GUI {
    public static void main(String[] args){
        final Model model = new ModelImpl();
        final View view = new GuiView();
        final Controller controller = new ControllerImpl(model, view);

        model.addObserver(view);
    }
}
