package frc.robot.utility.motor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.utility.shuffleboard.ShuffleboardValue;

public class SafeCanSparkMaxNew extends SafeMotor {
    private final CANSparkMax motor;
    private final ShuffleboardValue<Boolean> motorFault;
    public SafeCanSparkMaxNew(int deviceId, MotorType type, ShuffleboardValue<Boolean> isEnabled, 
        ShuffleboardValue<Double> outputWriter, ShuffleboardValue<Boolean> motorFault) {
        super(isEnabled, outputWriter);
        motor = new CANSparkMax(deviceId, type);

        // motorFault = ShuffleboardValue.create(getMotorFault(), "Motor Fault " + deviceId, 
        //         "MotorFaults")
        //         .withWidget(BuiltInWidgets.kBooleanBox)
        //         .build();
        this.motorFault =  motorFault;
    }

    @Override
    public void setPower(double power) {
        if(!DriverStation.isFMSAttached()){ //TODO: Test
            outputWriter.write(power);
        }
        if (!isEnabled.get()) motor.set(0);
            else motor.set(power);
    }

    @Override
    public void setVoltage(double outputVolts) {
        if(!DriverStation.isFMSAttached()){
            outputWriter.write(outputVolts);
        }
        if (!isEnabled.get()) motor.set(0);
            else motor.setVoltage(outputVolts);
    }

    @Override
    public void setInverted(boolean isInverted) {
        motor.setInverted(isInverted);
    }

    @Override
    public void setIdleMode(IdleMode mode) {
        motor.setIdleMode(switch (mode) {
            case Brake -> CANSparkMax.IdleMode.kBrake;
            case Coast -> CANSparkMax.IdleMode.kCoast;
        });
    }
    
    private CANSparkMax getSparkMax() {
        return motor;
    }


    public RelativeEncoder getEncoder() {
        return motor.getEncoder();
    }    

    public SparkMaxAbsoluteEncoder getAbsoluteEncoder(SparkMaxAbsoluteEncoder.Type encoderType) {
        return motor.getAbsoluteEncoder(encoderType);
    }
    
    // public void follow(SafeCanSparkMax leader, boolean invert) {
    //     motor.follow(leader.getSparkMax(), invert);
    // }

    public void burnFlash() {
        motor.burnFlash();
    }
    public void getMotorFault(){
        motorFault.set(motor.getFault(FaultID.kMotorFault));
    }
    public boolean getFault(){
        return motor.getFault(FaultID.kMotorFault);
    }
}
