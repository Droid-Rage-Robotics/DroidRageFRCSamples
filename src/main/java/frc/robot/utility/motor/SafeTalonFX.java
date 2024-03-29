package frc.robot.utility.motor;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import frc.robot.utility.shuffleboard.ShuffleboardValue;

public class SafeTalonFX extends SafeMotor{
    private final TalonFX motor;

    public SafeTalonFX(int deviceNumber, String canbus, ShuffleboardValue<Boolean> isEnabled, ShuffleboardValue<Double> outputWriter) {
        super(isEnabled, outputWriter);
        motor = new TalonFX(deviceNumber, canbus);
    }

    public SafeTalonFX(int deviceNumber, ShuffleboardValue<Boolean> isEnabled, ShuffleboardValue<Double> outputWriter) {
        super(isEnabled, outputWriter);
        motor = new TalonFX(deviceNumber);
    }


    public void setPower(double power) {
        if(!DriverStation.isFMSAttached()){
            outputWriter.write(power);
        }
        if (!isEnabled.get()) motor.set(TalonFXControlMode.PercentOutput, 0);
            else motor.set(TalonFXControlMode.PercentOutput, power);
    }

    public void setVoltage(double outputVolts) {
        if(!DriverStation.isFMSAttached()){
            outputWriter.write(outputVolts);
        }
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
    public void getMotorFault(){
        // motorFault.set(motor.getFault(FaultID.kMotorFault));
    }
}
