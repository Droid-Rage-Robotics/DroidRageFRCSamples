package frc.robot.utility.InfoTracker;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.utility.shuffleboard.ShuffleboardValue;

public class CycleTrackerTwo {
//     protected StatCalculator stat = new StatCalculator();
//     protected Timer timer = new Timer();
//     // private int high = 0 , low = 0;
//     private final File file = new File("/home/lvuser/Logs");//Add in data like time of match, what type it is from DRIVER STATION
//     private final PrintStream stream = new PrintStream(file);
// /* SIngleton - {@link CommandScheduler} {@link Sendable}*/ 
    
//     private HashMap<String, Double> data;
//     protected final ShuffleboardValue<Double> cycle = 
//         ShuffleboardValue.create(0.0, "Cycles:", "Misc")
//             .withSize(1, 2)
//             .build();
//     protected final ShuffleboardValue<Double> mean = 
//         ShuffleboardValue.create(0.0, "Mean:", "Misc")
//             .withSize(1, 2)
//             .build();
//     protected final ShuffleboardValue<Double> fastest = 
//         ShuffleboardValue.create(0.0, "Fastest Time:", "Misc")
//             .withSize(1, 2)
//             .build();
//     protected final ShuffleboardValue<Double> slowest = 
//         ShuffleboardValue.create(0.0, "Slowest Time:", "Misc")
//             .withSize(1, 2)
//             .build();
//     protected final ShuffleboardValue<Double> high = 
//         ShuffleboardValue.create(0.0, "High:", "Misc")
//             .withSize(1, 2)
//             .build();
//     protected final  ShuffleboardValue<Double> low = 
//         ShuffleboardValue.create(0.0, "Low:", "Misc")
//             .withSize(1, 2)
//             .build();

//     public CycleTrackerTwo(){
//         timer.start();
//     }
    
//     public void trackCycle(int num){
//         double cycleTime = timer.get();
//         stat.addNumber(cycleTime);
//         cycle.set(stat.getSizeDouble());
//         mean.set(stat.getMean());
//         slowest.set(stat.getLowestValue());
//         fastest.set(stat.getHighestValue());
//         // switch (getPosition()){
//         //     case HIGH:
//         //         high.set(high.get()+1);
//         //     case LOW:
//         //         low.set(low.get()+1);
//         switch (num){
//             case 1:
//                 high.set(high.get()+1);
//             case 2:
//                 low.set(low.get()+1);
//         }
//     }

//     public void printOut(String string, double num){
//         // data.put(string, num);
//         stream.print(string+num);
//         try {
//             file.createNewFile();
//         } catch (IOException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }
//     }
//     public void printAllData(){
//         printOut(DriverStation.getMatchType().toString(), DriverStation.getMatchNumber());

//         for (int i = 0; i < stat.getSizeInt(); i++) {
//             printOut("Cycle "+ i +":", stat.getNum(i));
//         }
//         printOut("High: ", high.get());
//         printOut("Low: ", low.get());
//         printOut("Total Cycles: ", cycle.get());
//         printOut("Mean", mean.get());
//         printOut("Fastest: ", stat.getHighestValue());
//         printOut("SLowest", stat.getLowestValue());
//     }
}
