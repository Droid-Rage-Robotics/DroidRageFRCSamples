package frc.robot.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.utility.shuffleboard.ShuffleboardValue;

public class CycleTracker {
    protected static StatCalculator stat = new StatCalculator();
    protected static Timer timer = new Timer();
    // private static int high = 0 , low = 0;
    private static File file;
    private static final PrintStream stream;

    static {
        try {
            stream = new PrintStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static HashMap<String, Double> data;
    protected final static ShuffleboardValue<Double> cycle = 
        ShuffleboardValue.create(0.0, "Cycles:", "Misc")
            .withSize(1, 2)
            .build();
    protected final static ShuffleboardValue<Double> mean = 
        ShuffleboardValue.create(0.0, "Mean:", "Misc")
            .withSize(1, 2)
            .build();
    protected final static ShuffleboardValue<Double> fastest = 
        ShuffleboardValue.create(0.0, "Fastest Time:", "Misc")
            .withSize(1, 2)
            .build();
    protected final static ShuffleboardValue<Double> slowest = 
        ShuffleboardValue.create(0.0, "Slowest Time:", "Misc")
            .withSize(1, 2)
            .build();
    protected final static ShuffleboardValue<Double> high = 
        ShuffleboardValue.create(0.0, "High:", "Misc")
            .withSize(1, 2)
            .build();
    protected final static ShuffleboardValue<Double> low = 
        ShuffleboardValue.create(0.0, "Low:", "Misc")
            .withSize(1, 2)
            .build();

    public CycleTracker(){
        file = new File("/home/lvuser/Logs");//Add in data like time of match, what type it is from DRIVER STATION
        timer.start();
    }
    
    public static void trackCycle(int num){
        double cycleTime = timer.get();
        stat.addNumber(cycleTime);
        cycle.set(stat.getSizeDouble());
        mean.set(stat.getMean());
        slowest.set(stat.getLowestValue());
        fastest.set(stat.getHighestValue());
        // switch (getPosition()){
        //     case HIGH:
        //         high.set(high.get()+1);
        //     case LOW:
        //         low.set(low.get()+1);
        switch (num){
            case 1:
                high.set(high.get()+1);
            case 2:
                low.set(low.get()+1);
        }
    }

    public static void printOut(String string, double num){
        // data.put(string, num);
        stream.print(string+num);
    }
    public static void printAllData(){
        printOut(DriverStation.getMatchType().toString(), DriverStation.getMatchNumber());

        for (int i = 0; i < stat.getSizeInt(); i++) {
            printOut("Cycle "+ i +":", stat.getNum(i));
        }
        printOut("High: ", high.get());
        printOut("Low: ", low.get());
        printOut("Total Cycles: ", cycle.get());
        printOut("Mean", mean.get());
        printOut("Fastest: ", stat.getHighestValue());
        printOut("SLowest", stat.getLowestValue());
    }
}
