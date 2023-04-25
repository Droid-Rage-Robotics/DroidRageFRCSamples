package frc.robot.subsystems;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DisabledCommand;
import frc.robot.utility.ComplexWidgetBuilder;
import frc.robot.utility.SafeTalonFX;
import frc.robot.utility.ShuffleboardValue;
import frc.robot.utility.ShuffleboardValueEnum;
import frc.robot.utility.SafeMotor.IdleMode;

public class ShooterTalonFX extends SubsystemBase {
    public enum Velocity implements ShuffleboardValueEnum<Double> {
        SHOOT_CUBE_LOW(19000),
        SHOOT_CUBE_MID(5000),
        SHOOT_AUTO_CUBE_LOW(26000),
        SHOOT_CUBE_HIGH(2400),
        SHOOT_CONE_LOW(SHOOT_CUBE_LOW.get()),
        SHOOT_CONE_HIGH(2400),
        STOP(0)
        ;

        private final ShuffleboardValue<Double> velocityRPM;
        private Velocity(double velocityRPM) {
            this.velocityRPM = ShuffleboardValue.create(velocityRPM, Velocity.class.getSimpleName()+"/"+name()+": Velocity (RPM)", ShooterTalonFX.class.getSimpleName())
                .withSize(1, 3)
                .build();
        }

        @Override
        public ShuffleboardValue<Double> getShuffleboardValue() {
            return velocityRPM;
        }

    }

    protected final SafeTalonFX motor;
    protected final ShuffleboardValue<Double> targetVelocityWriter = ShuffleboardValue.create(0.0, "Target Velocity", ShooterTalonFX.class.getSimpleName()).build();
    protected final ShuffleboardValue<Double> encoderVelocityWriter = ShuffleboardValue.create(0.0, "Encoder Velocity", ShooterTalonFX.class.getSimpleName()).build();
    protected final ShuffleboardValue<Double> encoderVelocityErrorWriter = ShuffleboardValue.create(0.0, "Encoder Velocity Error", ShooterTalonFX.class.getSimpleName()).build();
    
    protected final PIDController controller;
    protected final SimpleMotorFeedforward feedforward;
        

    public ShooterTalonFX(Boolean isEnabled) {
        motor = new SafeTalonFX(
            19,
            ShuffleboardValue.create(isEnabled, "Is Enabled", ShooterTalonFX.class.getSimpleName())
                    .withWidget(BuiltInWidgets.kToggleSwitch)
                    .build(),
                ShuffleboardValue.create(0.0, "Voltage", ShooterTalonFX.class.getSimpleName())
                    .build()
        );

        motor.setIdleMode(IdleMode.Brake);
        motor.setInverted(true);
        
        controller = new PIDController(
            0.0003,//0.0003 
            0,
            0);
        controller.setTolerance(5);
        feedforward = new SimpleMotorFeedforward(0.64, 0.000515, 0);
        ComplexWidgetBuilder.create(controller, "PID Controller", ShooterTalonFX.class.getSimpleName());
        ComplexWidgetBuilder.create(DisabledCommand.create(runOnce(this::resetEncoder)), "Reset Encoder", ShooterTalonFX.class.getSimpleName());
    }

    @Override
    public void periodic() {
        setVoltage(calculatePID(getTargetVelocity()) + calculateFeedforward(getTargetVelocity()));
    }

    public double getEncoderVelocity() {
        double velocity = motor.getVelocity();
        encoderVelocityWriter.write(velocity);
        encoderVelocityErrorWriter.write(getTargetVelocity() - velocity);
        return velocity;
    }

    public double getTargetVelocity() {
        return controller.getSetpoint();
    }

    public double getEncoderVelocityError() {
        return encoderVelocityErrorWriter.get();
    }

    protected double calculatePID(double targetVelocity) {
        return controller.calculate(getEncoderVelocity(), targetVelocity);
    }

    protected double calculateFeedforward(double targetVelocity) {
        return feedforward.calculate(targetVelocity);
    }

    protected void setPower(double power) {
        motor.setPower(power);
    }

    protected void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }

    public void setTargetVelocity(Velocity velocity) {
        controller.setSetpoint(velocity.get());
        targetVelocityWriter.set(velocity.get());
    }

    public void resetEncoder() {
        motor.setPosition(0);
    }

    public void stop() {
        setTargetVelocity(Velocity.STOP);
    }

    public CommandBase runFor(Velocity velocity, double waitSeconds) {
        return Commands.sequence(
            runOnce(()-> setTargetVelocity(velocity)),
            Commands.waitSeconds(waitSeconds),
            runOnce(this::stop)
        );
    }
}

