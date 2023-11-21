package frc.robot;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.drive.SwerveDriveTeleop;
import frc.robot.subsystems.TemporarySubsystem;
import frc.robot.subsystems.drive.SwerveDrive;
import frc.robot.utility.InfoTracker.CycleTracker;
import frc.robot.utility.shuffleboard.ComplexWidgetBuilder;
import frc.robot.utility.shuffleboard.ShuffleboardValue;

public class RobotContainer {
    
    // private final CycleTracker cycleTracker = new CycleTracker();
    private final CommandXboxController driver =
        new CommandXboxController(DroidRageConstants.Gamepad.DRIVER_CONTROLLER_PORT);
    private final CommandXboxController operator =
        new CommandXboxController(DroidRageConstants.Gamepad.OPERATOR_CONTROLLER_PORT);
    private final TemporarySubsystem temporarySubsystem =new TemporarySubsystem(true);
    private ShuffleboardValue<Double> matchTime = ShuffleboardValue.create(0.0, "Match Time", "Misc")
        .withWidget(BuiltInWidgets.kTextView)
        .build();
    SendableChooser<CommandBase> autoChooser = new SendableChooser<CommandBase>();
    ShuffleboardValue<Boolean> motorTestRC = ShuffleboardValue.create(
        false, "motorTestRC", "Misc")
        .withSize(1, 3)
        .withWidget(BuiltInWidgets.kBooleanBox)
        .build();

    public RobotContainer() {
        ComplexWidgetBuilder.create(autoChooser, "Auto Chooser", "Misc")
            .withSize(1, 3);
        

        // ComplexWidgetBuilder.create(CameraServer.startAutomaticCapture(), "USB Camera Stream", "Misc")   //Usb Streaming
        //     .withSize(5, 5);
        
    }

    public void configureTeleOpBindings(
        // SwerveDrive drive
        ) {
            // motorFaultRX.set(testMotor.getFault(FaultID.kCANRX));
            // motorFaultTX.set(testMotor.getFault(FaultID.kCANTX));
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

        // driver.leftTrigger()
        //     .onTrue(new InstantCommand(()->cycleTracker.trackCycle(1)));
        // driver.rightTrigger()
        //     .onTrue(new InstantCommand(()->cycleTracker.trackCycle(1)));
        
    }
    
    public void configureTestBindings(){}
    
    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    public void teleopPeriodic() {
        temporarySubsystem.loop();
        motorTestRC.set(temporarySubsystem.getTemp());
        matchTime.set(DriverStation.getMatchTime());
    }
}
