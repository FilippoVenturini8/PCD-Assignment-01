package view;

import controller.Controller;
import utils.Interval;
import utils.Result;
import utils.SetupInfo;
import utils.Strings;

import javax.swing.*;
import javax.xml.stream.FactoryConfigurationError;
import java.awt.*;
import java.util.Map;

import static model.MasterThread.N_WORKERS;

public class GuiView implements View{
    private Controller controller;
    private final JFrame frame = new JFrame();
    private final JList<Result> rankingList = new JList<>();
    private final JList<String> distributionList = new JList<>();
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");

    public GuiView(){
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.frame.setTitle("Source Tracker");
        this.frame.setSize(800, 500);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        final JLabel lblDirectory = new JLabel("Start directory: ");
        final JTextField txtDirectory = new JTextField(20);
        txtDirectory.setMinimumSize(txtDirectory.getPreferredSize());

        final JLabel lblNFiles = new JLabel("Number of files: ");
        final JTextField txtNFiles = new JTextField(3);
        txtDirectory.setMinimumSize(txtDirectory.getPreferredSize());

        final JLabel lblIntervals = new JLabel("Number of intervals: ");
        final JTextField txtIntervals = new JTextField(3);
        txtDirectory.setMinimumSize(txtDirectory.getPreferredSize());

        final JLabel lblLastInterval = new JLabel("Last interval: ");
        final JTextField txtLastInterval = new JTextField(3);
        txtDirectory.setMinimumSize(txtDirectory.getPreferredSize());


        btnStop.setEnabled(false);

        btnStart.addActionListener(e -> {
            if(txtDirectory.getText().isEmpty()){
                Toast.makeToast(frame, "Insert path of initial directory.", new Color(255,0,0,170), 3);
                return;
            }
            if(txtNFiles.getText().isEmpty() || !Strings.isNumeric(txtNFiles.getText()) || Integer.parseInt(txtNFiles.getText()) <= 0){
                Toast.makeToast(frame, "Insert correct number of files.", new Color(255,0,0,170), 3);
                return;
            }
            if(txtIntervals.getText().isEmpty() || !Strings.isNumeric(txtIntervals.getText()) || Integer.parseInt(txtNFiles.getText()) <= 0){
                Toast.makeToast(frame, "Insert correct number of intervals.", new Color(255,0,0,170), 3);
                return;
            }
            if(txtLastInterval.getText().isEmpty() || !Strings.isNumeric(txtLastInterval.getText()) || Integer.parseInt(txtNFiles.getText()) <= 0){
                Toast.makeToast(frame, "Insert correct last interval value.", new Color(255,0,0,170), 3);
                return;
            }

            this.controller.start(new SetupInfo(
                    txtDirectory.getText(),
                    Integer.parseInt(txtNFiles.getText()),
                    Integer.parseInt(txtIntervals.getText()),
                    Integer.parseInt(txtLastInterval.getText())), N_WORKERS);

            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
        });

        btnStop.addActionListener(e -> {

        });

        final JPanel resultsPanel = new JPanel();

        this.rankingList.setSize(100, 50);
        this.distributionList.setSize(100, 50);

        inputPanel.add(lblDirectory);
        inputPanel.add(txtDirectory);
        inputPanel.add(lblNFiles);
        inputPanel.add(txtNFiles);
        inputPanel.add(lblIntervals);
        inputPanel.add(txtIntervals);
        inputPanel.add(lblLastInterval);
        inputPanel.add(txtLastInterval);

        controlPanel.add(btnStart);
        controlPanel.add(btnStop);

        resultsPanel.add(rankingList);
        resultsPanel.add(distributionList);

        mainPanel.add(inputPanel);
        mainPanel.add(controlPanel);
        mainPanel.add(resultsPanel);

        this.frame.setContentPane(mainPanel);
        this.frame.setVisible(true);
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

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
