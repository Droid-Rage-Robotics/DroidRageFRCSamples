package frc.robot.subsystems.elevator;

import java.util.Map;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.commands.DisabledCommand;
import frc.robot.utility.motor.SafeCanSparkMax;
import frc.robot.utility.motor.SafeMotor.IdleMode;
import frc.robot.utility.shuffleboard.ComplexWidgetBuilder;
import frc.robot.utility.shuffleboard.ShuffleboardValue;

public class HorizontalElevator extends Elevator {
    public static class Constants {
        public static final double GEAR_RATIO = 1 / 1;
        public static final double GEAR_DIAMETER_INCHES = 1.4;
        public static final double COUNTS_PER_PULSE = 1; // 2048 bc rev through bore
        public static final double ROT_TO_INCHES = (COUNTS_PER_PULSE * GEAR_RATIO) / (GEAR_DIAMETER_INCHES * Math.PI);
        public static final double MIN_POSITION = 0;
        public static final double MAX_POSITION = 13.1;
    }
    private final PIDController controller = new PIDController(2.4, 0, 0);
    private final ElevatorFeedforward feedforward = new ElevatorFeedforward(0.05, 0, 0, 0);
    private final SafeCanSparkMax motor;

    protected final ShuffleboardValue<Double> encoderPositionWriter = ShuffleboardValue.create(0.0, "Encoder Position", HorizontalElevator.class.getSimpleName())
        .withSize(1, 3)
        .withWidget(BuiltInWidgets.kNumberSlider)//TODO: How does this work? And doe sit look nice?
        .withProperties(Map.of("min", Constants.MIN_POSITION, "max", Constants.MAX_POSITION))
        .build();

    protected final ShuffleboardValue<Boolean> isMovingManually = ShuffleboardValue.create(false, "Moving manually", HorizontalElevator.class.getSimpleName())
        .build();

    private final RelativeEncoder encoder;

    public HorizontalElevator(Boolean isEnabled) {
        motor = new SafeCanSparkMax(
            17, 
            MotorType.kBrushless,
            ShuffleboardValue.create(isEnabled, "Is Enabled", HorizontalElevator.class.getSimpleName())
                .withWidget(BuiltInWidgets.kToggleSwitch)
                .build(),
            ShuffleboardValue.create(0.0, "Voltage", HorizontalElevator.class.getSimpleName())
                .build()
        );
        motor.setIdleMode(IdleMode.Brake);
        motor.setInverted(true);

        controller.setTolerance(0.1);

        encoder = motor.getEncoder();
        encoder.setPositionConversionFactor(Constants.ROT_TO_INCHES);

        ComplexWidgetBuilder.create(getController(), " PID Controller", HorizontalElevator.class.getSimpleName())
            .withWidget(BuiltInWidgets.kPIDController)
            .withSize(2, 2);

        ComplexWidgetBuilder.create(DisabledCommand.create(runOnce(this::resetEncoder)), "Reset Encoder", HorizontalElevator.class.getSimpleName());
    }

    @Override
    protected PIDController getController() {
        return controller;
    }

    @Override
    protected ElevatorFeedforward getFeedforward() {
        return feedforward;
    }

    @Override
    protected void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }

    @Override
    protected ShuffleboardValue<Boolean> getIsMovingManually() {
        return isMovingManually;
    }

    @Override
    public double getMaxPosition() {
        return Constants.MAX_POSITION;
    }

    @Override
    public double getMinPosition() {
        return Constants.MIN_POSITION;
    }

    @Override
    public void resetEncoder() {
        encoder.setPosition(0);
    }

    @Override
    public double getEncoderPosition() {
        double position = encoder.getPosition();
        encoderPositionWriter.write(position);
        return position;
    } 
}
