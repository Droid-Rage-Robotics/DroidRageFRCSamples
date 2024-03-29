package frc.robot.utility.InfoTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TimeZone;
import java.util.TimerTask;
import java.util.function.Supplier;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class sends data out to log file and the SmartDashboard at a periodic rate.
 * 
 * Usage Example:
 *   ConsolePrinter.init(); //get instance of printer
 *   ConsolePrinter.setRate(100); //set rate to 100ms
 *   ConsolePrinter.putBoolean("isBallPresent", Robot::ballPresent, true, false);
 *   ConsolePrinter.putNumber("Encoder", Shooter::getRightPosition, true, false);
 *   
 *   ConsolePrinter.startThread(); //begin logging data 
 */
public class ConsolePrinter {
	// public static final boolean PRINT_SD_DEBUG_DATA = true;
	// // public static final long SmartDashThreadPeriod = 100; //ms
	// // public static final long CONSOLE_PRINTER_LOG_RATE_MS = 100; //ms

	// private static ConsolePrinter instance = null;
	// private static java.util.Timer executor;
	// private static long period = 100; //ms
	// private static boolean started = false;
	// private static PrintWriter log;
	// private static HashMap<String, Loggable> data;
	// private static LinkedHashSet<String> fileKeys;

	// private ConsolePrinter() {
	// 	data = new HashMap<String, Loggable>();
	// 	fileKeys = new LinkedHashSet<String>();
		
	// 	//Initialize the log file with timestamps
	// 	putNumber("Time", Timer::getFPGATimestamp);
	// 	putNumber("TimeOfDay", ()->{return (double)System.currentTimeMillis();});
	// }
	
	// /**
	//  * Gets a console printer instance.
	//  * @return the console printer 
	//  */
	// public static void init() {
	// 	if (instance == null) {
	// 		instance = new ConsolePrinter();
	// 	}
	// }

	
	
	// /**
	//  * Sets the rate of periodic logging to the dashboard and log file.  
	//  * @param ms the rate in milliseconds
	//  */
	// public static void setRate(long ms) {
	// 	period = ms;
	// }
	
	// /**
	//  * Add a key/value pair to the set of data that will be reported to the dashboard and/or logged to file.
	//  * 
	//  * @param key the name identifying the data
	//  * @param value the data
	//  */
	// public static void putBoolean(String key, Supplier<Boolean> value) {
	// 	// data.put(key, new LoggableBoolean(value));
    //     fileKeys.add(key);
	// }
	
	// /**
	//  * Add a key/value pair to the set of data that will be reported to the dashboard and/or logged to file.
	//  * 
	//  * @param key the name identifying the data
	//  * @param value the data
	//  * @param toDashboard true to report this item to the dashboard
	//  * @param toFile true to log this item to file
	//  */
	// public static void putNamedSendable(String key, Supplier<NamedSendable> value) {
	// 	data.put(key, new LoggableNamedSendable(value));
	// 	fileKeys.add(key);
	// }
	
	// /**
	//  * Add a key/value pair to the set of data that will be reported to the dashboard and/or logged to file.
	//  * 
	//  * @param key the name identifying the data
	//  * @param value the data
	//  * @param toDashboard true to report this item to the dashboard
	//  * @param toFile true to log this item to file
	//  */
	// public static void putSendable(String key, Supplier<Sendable> value) {
	// 	data.put(key, new LoggableSendable(value));
	// 	fileKeys.add(key);
	// }
	
	// /**
	//  * Add a key/value pair to the set of data that will be reported to the dashboard and/or logged to file.
	//  * 
	//  * @param key the name identifying the data
	//  * @param value the data
	//  * @param toDashboard true to report this item to the dashboard
	//  * @param toFile true to log this item to file
	//  */
	// public static void putNumber(String key, Supplier<Double> value) {
	// 	data.put(key, new LoggableNumber(value));
	// 	fileKeys.add(key);
	// }
	
	// /**
	//  * Add a key/value pair to the set of data that will be reported to the dashboard and/or logged to file.
	//  * 
	//  * @param key the name identifying the data
	//  * @param value the data
	//  * @param toDashboard true to report this item to the dashboard
	//  * @param toFile true to log this item to file
	//  */
	// public static void putString(String key, Supplier<String> value) {
	// 	data.put(key, new LoggableString(value));
	// 	fileKeys.add(key);
	// }
	
	// /**
	//  * This method begins transmitting data to the dashboard, and writing data to the log file.
	//  * This method shouldn't be called until after all data elements being logged to file have been "put",
	//  *   otherwise their associated "key" won't show up in the header line of the TSV file.
	//  */
	// public static void startThread() {
	// 	if(started) {
	// 		return;
	// 	}
	// 	started = true;
		
	// 	executor = new java.util.Timer();
	// 	executor.schedule(new ConsolePrintTask(), 0L, ConsolePrinter.period);

	// 	try {
	// 		File file = new File("/home/lvuser/Logs");
	// 		if (!file.exists()) {
	// 			if (file.mkdir()) {
	// 				System.out.println("Log Directory is created!");
	// 			} else {
	// 				System.out.println("Failed to create Log directory!");
	// 			}
	// 		}
	// 		Date date = new Date() ;
	// 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	// 		dateFormat.setTimeZone(TimeZone.getTimeZone("EST5EDT"));
	// 		log = new PrintWriter("/home/lvuser/Logs/" + dateFormat.format(date) + "-Log.txt", "UTF-8");
	// 		log.println(dataFileHeader());
	// 		log.flush();
	// 	} catch (FileNotFoundException e) {
	// 		// TODO Auto-generated catch block
	// 		e.printStackTrace();
	// 	} catch (UnsupportedEncodingException e) {
	// 		// TODO Auto-generated catch block
	// 		e.printStackTrace();
	// 	}

	// }

	// private static String dataFileHeader() {
	// 	Iterator<String> i;
	// 	String key;
	// 	String output = "";
		
	// 	if (PRINT_SD_DEBUG_DATA) {
	// 		i = fileKeys.iterator();
	// 		//Build string
	// 		while(i.hasNext()) {
	// 			key = i.next();
	// 			output = output.concat(key + "\t");
	// 		}
	// 	}
	// 	return output;
	// }
	
	// /**
	//  * Writes current values for all data elements to the log file for data elements that have file logging enabled.
	//  */
	// private static void dataToFile() {
	// 	Iterator<String> i;
	// 	String key;
	// 	String output = "";
		
	// 	if (PRINT_SD_DEBUG_DATA) {
	// 		i = fileKeys.iterator();
	// 		//Build string
	// 		while(i.hasNext()) {
	// 			key = i.next();
	// 			output = output.concat(data.get(key).valueToString() + "\t");
	// 		}
	// 		if(log != null) {
	// 			//send string to log file
	// 			log.println(output);
	// 			log.flush();
	// 		} else {
	// 			System.out.println("Log file is null");
	// 		}
	// 	}
	// }

	// private static class ConsolePrintTask extends TimerTask {
	// 	private ConsolePrintTask() {
	// 	}

	// 	/**
	// 	 * Called periodically in its own thread
	// 	 */
	// 	public void run() {
	// 		ConsolePrinter.dataToDashboard();
	// 		ConsolePrinter.dataToFile();
	// 	}
	// }	
	
	
	
	
	
	
}