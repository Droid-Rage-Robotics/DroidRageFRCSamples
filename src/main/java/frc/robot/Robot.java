// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.server.PathPlannerServer;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final RobotContainer robotContainer = new RobotContainer();
  private Command autonomousCommand;
  

  /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        PathPlannerServer.startServer(5811); // Use to see the Path of the robot on PathPlanner
        // robotContainer = new RobotContainer();
    }
    
    /**
     * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {

    }
    
    @Override
    public void disabledPeriodic() {
        // if(RobotController.getBatteryVoltage()<11.5){
        //     light.setAllColor(light.batteryBlue);
        // } else{
        //     light.flashingColors(light.yellow, light.blue);
        // }
        
        //TODO: Add lights to have the robot tell us any errors with can, etc.
        // light.rainbow();
        // light.orangeAndBlue();
        // light.switchLeds();
        // light.chaseLED( 1);
    }

    @Override
    public void autonomousInit() {
        CommandScheduler.getInstance().cancelAll();
        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {}
    
    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().cancelAll();
        robotContainer.configureTeleOpBindings();
        // robotContainer.configureTeleOpDriverOnlyBindings();
    }

    @Override
    public void teleopPeriodic() {
        robotContainer.teleopPeriodic();
    }
    
    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
        robotContainer.configureTestBindings();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void simulationInit() {}

    @Override
    public void simulationPeriodic() {}
}
