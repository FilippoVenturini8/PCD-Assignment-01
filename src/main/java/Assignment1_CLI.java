import controller.Controller;
import controller.ControllerImpl;
import model.Model;
import model.ModelImpl;
import utils.SetupInfo;
import utils.Strings;
import view.ConsoleView;
import view.GuiView;
import view.View;

import java.util.Scanner;

import static model.MasterThread.N_WORKERS;

public class Assignment1_CLI {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Root directory: ");
        final String dir = scanner.nextLine();

        String tmp;
        do{
            System.out.print("Number of files to visualize: ");
            tmp = scanner.nextLine();
        }while (!Strings.isNumeric(tmp) || Integer.parseInt(tmp) <= 0);
        final Integer nFiles = Integer.parseInt(tmp);

        do{
            System.out.print("Number of intervals: ");
            tmp = scanner.nextLine();
        }while (!Strings.isNumeric(tmp) || Integer.parseInt(tmp) <= 0);
        final Integer nIntervals = Integer.parseInt(tmp);

        do{
            System.out.print("Last interval max: ");
            tmp = scanner.nextLine();
        }while (!Strings.isNumeric(tmp) || Integer.parseInt(tmp) <= 0);
        final Integer lastInterval = Integer.parseInt(tmp);

        final SetupInfo setupInfo = new SetupInfo(dir, nFiles, nIntervals, lastInterval);

        final Model model = new ModelImpl();
        final View view = new ConsoleView();
        final Controller controller = new ControllerImpl(model, view);

        model.addObserver(view);

        controller.start(setupInfo, N_WORKERS);
    }
}
