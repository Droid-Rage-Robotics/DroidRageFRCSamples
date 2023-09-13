package frc.robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.utility.InfoTracker.CycleTracker;
import frc.robot.utility.shuffleboard.ComplexWidgetBuilder;
import frc.robot.utility.shuffleboard.ShuffleboardValue;

public class RobotContainer {
    // guess these will never be implemented bc were done with the season
    //TODO: Ideas
    //Logging Battery usage
    //Logging Robot Positioning
    // override button for when sensors fail
    // custom shuffleboard droid rage theme
    // auto align
    // fix auto balance
    // fix lock wheels
    // limelight
    // see how slew rate limiter affects turning and movement. can it make motors stop faster without enabling brake mode?
    //Idea: Make a button that automatically aligns to 180 degrees with a press of a button - 
    //      only when held - still leave control of drive to driver except for turning
    //Make all of the writes that are not necessary during a match be in a practice only writers (Helps prevent the likelyhood of loop overruns)



    //Add a velocity offset intake 
    //Project to Test Motors Seperately
    //make lift reset while driving away ofr autos
    //save time on bump don't worry about free
    //Add lights to have the robot tell us any errors with can, etc.
    
    private final CycleTracker cycleTracker = new CycleTracker();
    private final CommandXboxController driver =
        new CommandXboxController(DroidRageConstants.Gamepad.DRIVER_CONTROLLER_PORT);
    private final CommandXboxController operator =
        new CommandXboxController(DroidRageConstants.Gamepad.OPERATOR_CONTROLLER_PORT);

    private ShuffleboardValue<Double> matchTime = ShuffleboardValue.create(0.0, "Match Time", "Misc")
        .withWidget(BuiltInWidgets.kTextView)
        .build();
    SendableChooser<CommandBase> autoChooser = new SendableChooser<CommandBase>();
    public RobotContainer() {
        ComplexWidgetBuilder.create(autoChooser, "Auto Chooser", "Misc")
            .withSize(1, 3);

        // ComplexWidgetBuilder.create(CameraServer.startAutomaticCapture(), "USB Camera Stream", "Misc")   //Usb Streaming
        //     .withSize(5, 5);
        
    }

    public void configureTeleOpBindings() {
        DriverStation.silenceJoystickConnectionWarning(true);
        // light.setDefaultCommand(new LightCommand(intake, light, driver));
        
        // drive.setDefaultCommand(
        //     new SwerveDriveTeleop(
        //         drive, 
        //         driver::getLeftX, 
        //         driver::getLeftY, 
        //         driver::getRightX,
        //         driver.x()
        //         )
        //     );
        // driver.leftTrigger().and(driver.leftBumper())
        //     .onTrue(new DropTeleopCone(arm, intake)) 
        //     .onFalse(intake.run(intake::stop))
        //     .onFalse(arm.setPositionCommand(Position.HOLD));
        // driver.leftTrigger().and(driver.leftBumper().negate())
        //     .onTrue(new DropTeleopCube(arm, intake))
        //     .onFalse(intake.run(intake::stop))
        //     .onFalse(arm.setPositionCommand(Position.HOLD));
        driver.leftTrigger()
            
            .onTrue(new InstantCommand(()->cycleTracker.trackCycle(1)))
            ;
            driver.rightTrigger()
            
            .onTrue(new InstantCommand(()->cycleTracker.trackCycle(1)))
            ;
        
    }

    public void configureTestBindings(){}
    
    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    public void teleopPeriodic() {
        matchTime.set(DriverStation.getMatchTime());
    }
}
