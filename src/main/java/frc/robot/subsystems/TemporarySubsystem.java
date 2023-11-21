package frc.robot.subsystems;


import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utility.motor.SafeCanSparkMax;
import frc.robot.utility.motor.SafeCanSparkMaxNew;
import frc.robot.utility.shuffleboard.ShuffleboardValue;
import frc.robot.utility.motor.SafeMotor.IdleMode;

public class TemporarySubsystem extends SubsystemBase {
    protected final SafeCanSparkMaxNew motor1, motor2, motor3, motor4, motor5, motor6, motor7, motor8;
    
    private final ShuffleboardValue<Boolean> isEnabled, motorFail,
    motorF1=ShuffleboardValue.create(false, "1", "MotorFaults")
    .withWidget(BuiltInWidgets.kBooleanBox)
    .build(), 
    
    motorF2=ShuffleboardValue.create(true, "2", "MotorFaults")
    .withWidget(BuiltInWidgets.kBooleanBox)
    .build(),
    motorF3=ShuffleboardValue.create(false, "3", "MotorFaults")
    .withWidget(BuiltInWidgets.kBooleanBox)
    .build(), 
    motorF4=ShuffleboardValue.create(false, "4", "MotorFaults")
    .withWidget(BuiltInWidgets.kBooleanBox)
    .build(),
    motorF5=ShuffleboardValue.create(true, "5", "MotorFaults")
        .withWidget(BuiltInWidgets.kBooleanBox)
        .build(), 
    motorF6=ShuffleboardValue.create(false, "6", "MotorFaults")
        .withWidget(BuiltInWidgets.kBooleanBox)
        .build(),
    motorF7=ShuffleboardValue.create(false, "7", "MotorFaults")
    .withWidget(BuiltInWidgets.kBooleanBox)
    .build(), 
    motorF8=ShuffleboardValue.create(false, "8", "MotorFaults")
    .withWidget(BuiltInWidgets.kBooleanBox)
    .build();
    private final ShuffleboardValue<Double> voltage;


    public TemporarySubsystem(Boolean isEnabled) {
        this.isEnabled = ShuffleboardValue.create(isEnabled, "Is Enabled", Intake.class.getSimpleName())
        .withWidget(BuiltInWidgets.kToggleSwitch)
        .build();
        this.voltage = ShuffleboardValue.create(0.0, "Voltage", Intake.class.getSimpleName())
        .build();

        this.motorFail = ShuffleboardValue.create(true, "Motor FAIL", "MotorFaults")
        .withWidget(BuiltInWidgets.kBooleanBox)
        .build();

        
        motor1 = new SafeCanSparkMaxNew(
            1,
            MotorType.kBrushless,
            this.isEnabled,this.voltage,
            motorF1
        );
        motor1.setIdleMode(IdleMode.Brake);
        motor1.setInverted(true);

        motor2 = new SafeCanSparkMaxNew(
            2,
            MotorType.kBrushless,
            this.isEnabled,this.voltage,
            motorF2
        );
        motor2.setIdleMode(IdleMode.Brake);
        motor2.setInverted(true);

        motor3 = new SafeCanSparkMaxNew(
            3,
            MotorType.kBrushless,
            this.isEnabled,this.voltage,
            motorF3
        );
        motor3.setIdleMode(IdleMode.Brake);
        motor3.setInverted(true);

        motor4 = new SafeCanSparkMaxNew(
            4,
            MotorType.kBrushless,
            this.isEnabled,this.voltage,
            motorF4
        );
        motor4.setIdleMode(IdleMode.Brake);
        motor4.setInverted(true);

        motor5 = new SafeCanSparkMaxNew(
            5,
            MotorType.kBrushless,
            this.isEnabled,this.voltage,
            motorF5
        );
        motor5.setIdleMode(IdleMode.Brake);
        motor5.setInverted(true);

        motor6 = new SafeCanSparkMaxNew(
            6,
            MotorType.kBrushless,
            this.isEnabled,this.voltage,
            motorF6
        );
        motor6.setIdleMode(IdleMode.Brake);
        motor6.setInverted(true);

        motor7 = new SafeCanSparkMaxNew(
            7,
            MotorType.kBrushless,
            this.isEnabled,this.voltage,
            motorF7
        );
        motor7.setIdleMode(IdleMode.Brake);
        motor7.setInverted(true);

        motor8 = new SafeCanSparkMaxNew(
            8,
            MotorType.kBrushless,
            this.isEnabled,this.voltage,
            motorF8

        );
        motor8.setIdleMode(IdleMode.Brake);
        motor8.setInverted(true);
    }
    public boolean getTemp(){
        return motor5.getFault();
    }

    
    public void loop() {
        motorF1.set(motor1.getFault());
        motorF2.set(motor2.getFault());
        motorF3.set(motor3.getFault());
        motorF4.set(motor4.getFault());
        motorF5.set(motor5.getFault());
        motorF6.set(motor6.getFault());
        motorF7.set(motor7.getFault());
        motorF8.set(motor8.getFault());
        // motor1.getMotorFault();
        // motor2.getMotorFault();
        // motor3.getMotorFault();
        // motor4.getMotorFault();
        // motor5.getMotorFault();
        // motor6.getMotorFault();
        // motor7.getMotorFault();
        // motor8.getMotorFault();

        // motorFail.set(motor5.getFault());
    }
    @Override
    public void periodic() {
        motorF1.set(motor1.getFault());
        motorF2.set(motor2.getFault());
        motorF3.set(motor3.getFault());
        motorF4.set(motor4.getFault());
        motorF5.set(motor5.getFault());
        motorF6.set(motor6.getFault());
        motorF7.set(motor7.getFault());
        motorF8.set(motor8.getFault());
        // motor1.getMotorFault();
        // motor2.getMotorFault();
        // motor3.getMotorFault();
        // motor4.getMotorFault();
        // motor5.getMotorFault();
        // motor6.getMotorFault();
        // motor7.getMotorFault();
        // motor8.getMotorFault();

        // motorFail.set(motor5.getFault());
    }
  

}

