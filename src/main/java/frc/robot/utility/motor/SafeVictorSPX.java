package frc.robot.utility.motor;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.RobotController;
import frc.robot.utility.shuffleboard.ShuffleboardValue;

public class SafeVictorSPX extends SafeMotor{
    private final VictorSPX motor;

    public SafeVictorSPX(int deviceNumber, String canbus, 
        ShuffleboardValue<Boolean> isEnabled, ShuffleboardValue<Double> outputWriter) {
        super(isEnabled, outputWriter);
        motor = new VictorSPX(deviceNumber, canbus);
    }

    public SafeVictorSPX(int deviceNumber, 
        ShuffleboardValue<Boolean> isEnabled, 
        ShuffleboardValue<Double> outputWriter) {
        super(isEnabled, outputWriter);
        motor = new TalonFX(deviceNumber);
    }


    public void setPower(double power) {
        outputWriter.write(power);
        if (!isEnabled.get()) motor.set(TalonFXControlMode.PercentOutput, 0);
            else motor.set(TalonFXControlMode.PercentOutput, power);
    }

    public void setVoltage(double outputVolts) {
        outputWriter.write(outputVolts);
        if (!isEnabled.get()) motor.set(TalonFXControlMode.PercentOutput, 0);
            else motor.set(TalonFXControlMode.PercentOutput, outputVolts / RobotController.getBatteryVoltage());
    }

    @Override
    public void setInverted(boolean isInverted) {
        motor.setInverted(isInverted);;
    }

    @Override
    public void setIdleMode(IdleMode mode) {
        motor.setNeutralMode(switch (mode) {
            case Brake -> NeutralMode.Brake;
            case Coast -> NeutralMode.Coast;
        });
    }

    public double getVelocity() {
        return motor.getSelectedSensorVelocity();
    }

    public double getPosition() {
        return motor.getSelectedSensorPosition();
    }

    public void setPosition(double position) {
        motor.setSelectedSensorPosition(position);
    }
}
