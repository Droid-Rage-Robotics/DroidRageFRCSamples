package frc.robot.subsystems.shooter;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utility.motor.SafeCanSparkMax;
import frc.robot.utility.shuffleboard.ShuffleboardValue;

//Keep in mind this doesn't implement Feedforward
public class ShooterSparkMax extends SubsystemBase {
    public enum IntakeSpeeds {
        FAR_CUBE_LOW(4000),
        FAR_CUBE_MID(5000),
        FAR_CUBE_HIGH(5400),

        CONE(1000),
        CONTINUOUS(600),
        INTAKE(-3000),
        OUTTAKE(3000),
        HOLD_CONE(500),
        HOLD_CUBE(500),
        STOP(0), 
        POSITION_TOLERANCE(5),

        ;
        private final ShuffleboardValue<Double> velocityRPM;
        private IntakeSpeeds(double velocityRPM) {
            this.velocityRPM = ShuffleboardValue.create(velocityRPM, ShooterSparkMax.class.getSimpleName()+"/"+name()+": Velocity (RPM)", ShooterTalonFX.class.getSimpleName())
                .withSize(1, 3)
                .build();
        }
        public double get() {
            return velocityRPM.get();
        }
    }


    protected SafeCanSparkMax intakeMotor;
    protected final ShuffleboardValue<Double> targetVelocityWriter = ShuffleboardValue.create(0.0, "Target Velocity", ShooterTalonFX.class.getSimpleName()).build();
    protected final ShuffleboardValue<Double> encoderVelocityWriter = ShuffleboardValue.create(0.0, "Encoder Velocity", ShooterTalonFX.class.getSimpleName()).build();
    protected final ShuffleboardValue<Double> encoderVelocityErrorWriter = ShuffleboardValue.create(0.0, "Encoder Velocity Error", ShooterTalonFX.class.getSimpleName()).build();
    private final PIDController intakeController;
    private final RelativeEncoder intakeEncoder;
    
    private final ShuffleboardValue<Boolean> isEnabled = ShuffleboardValue.create(true, "Is Enabled", ShooterTalonFX.class.getSimpleName())
            .withWidget(BuiltInWidgets.kToggleSwitch)
            .build();

    public ShooterSparkMax(Boolean isEnabled) {
        intakeMotor = new SafeCanSparkMax(
            19,
            MotorType.kBrushless,
            ShuffleboardValue.create(isEnabled, "Is Enabled", ShooterTalonFX.class.getSimpleName())
                    .withWidget(BuiltInWidgets.kToggleSwitch)
                    .build(),
                ShuffleboardValue.create(0.0, "Voltage", ShooterTalonFX.class.getSimpleName())
                    .build()
        );
        intakeMotor.setInverted(true);

        intakeController = new PIDController(
            0.000173611,
            0,
            0);
        intakeEncoder = intakeMotor.getEncoder();
        intakeController.setTolerance(IntakeSpeeds.POSITION_TOLERANCE.get());
    }

    @Override
    public void periodic() {
        setIntakePower(intakeController.calculate(getIntakeVelocity()));
    }
  
    @Override
    public void simulationPeriodic() {}
  
    public CommandBase runStop() { 
        return setTargetVelocity(IntakeSpeeds.STOP);
    }

    private CommandBase setTargetVelocity(IntakeSpeeds velocity) {
        return runOnce(() -> {
            intakeController.setSetpoint(velocity.get());
            targetVelocityWriter.set(velocity.get());
        });
    }

    public double getIntakeVelocity() {
        return intakeEncoder.getVelocity();
    }
      
    private void setIntakePower(double power) {
        if (!isEnabled.get()) return;
        intakeMotor.setPower(power);
    }
}