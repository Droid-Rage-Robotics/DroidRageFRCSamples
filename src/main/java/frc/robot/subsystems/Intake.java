package frc.robot.subsystems;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DisabledCommand;
import frc.robot.utility.shuffleboard.ComplexWidgetBuilder;
import frc.robot.utility.motor.SafeTalonFX;
import frc.robot.utility.shuffleboard.ShuffleboardValue;
import frc.robot.utility.shuffleboard.ShuffleboardValueEnum;
import frc.robot.utility.motor.SafeMotor.IdleMode;

public class Intake extends SubsystemBase {
    public enum Velocity implements ShuffleboardValueEnum<Double> {
        SHOOT_CUBE_LOW(19000),
        SHOOT_CUBE_MID(5000),

        SHOOT_AUTO_CUBE_LOW(26000),
        SHOOT_CUBE_HIGH(2400),
  
        SHOOT_CONE_LOW(SHOOT_CUBE_LOW.get()),
        // SHOOT_CONE_MID(500),
        SHOOT_CONE_HIGH(2400),

        //3000 for pivot 140
        // CONE(50),
        CONTINUOUS(-400),
        INTAKE(-3000),
        OUTTAKE(2300),//4500
        HOLD_CONE(-400),
        HOLD_CUBE(-3400),
        STOP(0)
        ;

        private final ShuffleboardValue<Double> velocityRPM;

        private Velocity(double velocityRPM) {
            this.velocityRPM = ShuffleboardValue.create(velocityRPM, Velocity.class.getSimpleName()+"/"+name()+": Velocity (RPM)", Intake.class.getSimpleName())
                .withSize(1, 3)
                .build();
        }

        @Override
        public ShuffleboardValue<Double> getShuffleboardValue() {
            return velocityRPM;
        }

    }

    protected final SafeTalonFX motor;
    protected final DoubleSolenoid intakeSolenoid;
    protected PneumaticHub pneumaticHub;
    protected boolean isOpenDefault;
    protected final ShuffleboardValue<Boolean> isOpen = ShuffleboardValue.create
        (isOpenDefault, "Is Open", Intake.class.getSimpleName()).build();
    protected final ShuffleboardValue<Boolean> compressorEnabledWriter = ShuffleboardValue.create(true, "Compressor Enabled", Intake.class.getSimpleName())
        .withWidget(BuiltInWidgets.kToggleSwitch)
        .withSize(1, 2)
        .build();
    protected final ShuffleboardValue<Double> targetVelocityWriter = ShuffleboardValue.create
        (0.0, "Target Velocity", Intake.class.getSimpleName()).build();
    protected final ShuffleboardValue<Double> encoderVelocityWriter = ShuffleboardValue.create
        (0.0, "Encoder Velocity", Intake.class.getSimpleName()).build();
    protected final ShuffleboardValue<Double> encoderVelocityErrorWriter = ShuffleboardValue.create
        (0.0, "Encoder Velocity Error", Intake.class.getSimpleName()).build();
    // protected final ShuffleboardValue<Double> pressureWriter = ShuffleboardValue.create
    //     (0.0, "Forward Pressure", Intake.class.getSimpleName()).build();
    // protected final ShuffleboardValue<Double> backwardPressureWriter = ShuffleboardValue.create(0.0, "Backward Pressure", Intake.class.getSimpleName()).build();
    private final ShuffleboardValue<Boolean> isElementInWriter = ShuffleboardValue.create
        (false, "Is Element In", Intake.class.getSimpleName()).build();
    protected final PIDController controller;
    protected final SimpleMotorFeedforward feedforward;
        

    public Intake(Boolean isEnabled) {
        motor = new SafeTalonFX(
            19,
            ShuffleboardValue.create(isEnabled, "Is Enabled", Intake.class.getSimpleName())
                    .withWidget(BuiltInWidgets.kToggleSwitch)
                    .build(),
                ShuffleboardValue.create(0.0, "Voltage", Intake.class.getSimpleName())
                    .build()
        );

        motor.setIdleMode(IdleMode.Brake);
        motor.setInverted(true);

        pneumaticHub = new PneumaticHub(10);
        intakeSolenoid = pneumaticHub.makeDoubleSolenoid(9, 11);

        if(!isEnabled){
            pneumaticHub.disableCompressor();
        }
        
        controller = new PIDController(
            0.0003,//0.0003 
            0,
            0);
        controller.setTolerance(5);
        feedforward = new SimpleMotorFeedforward(0.64, 0.000515, 0);
        ComplexWidgetBuilder.create(controller, "PID Controller", Intake.class.getSimpleName());
        ComplexWidgetBuilder.create(DisabledCommand.create(runOnce(this::resetEncoder)), "Reset Encoder", Intake.class.getSimpleName());
        close(true);
    }

    @Override
    public void periodic() {
        if (!compressorEnabledWriter.get()) pneumaticHub.disableCompressor();
        setVoltage(calculatePID(getTargetVelocity()) + calculateFeedforward(getTargetVelocity()));
        isElementInWriter.set(isElementIn());
        // pressureWriter.set(pneumaticHub.getPressure(1));//TODO:Test://Can only be between 0-2
    }
  
    public void close(boolean changeElement) {
        intakeSolenoid.set(Value.kReverse);
        isOpen.set(false);
    }

    public void open(boolean changeElement) {
        intakeSolenoid.set(Value.kForward);
        isOpen.set(true);
    }

    public void toggle(boolean changeElement) {
        if(isOpen.get()) close(changeElement);
            else open(changeElement);
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

    public void intake() {
    }

    public void outtake() {
    }

    public void hold() {
    }

    public void resetEncoder() {
        motor.setPosition(0);
    }

    public void stop() {
        setTargetVelocity(Velocity.STOP);
    }

    public CommandBase runIntakeFor(double waitSeconds) {
        return Commands.sequence(
            runOnce(this::intake),
            Commands.waitSeconds(waitSeconds),
            runOnce(this::stop)
        );
    }
    public CommandBase runOuttakeFor(double waitSeconds) {
        return Commands.sequence(
            runOnce(this::outtake),
            Commands.waitSeconds(waitSeconds),
            runOnce(this::stop)
        );
    }

    public CommandBase runFor(Velocity velocity, double waitSeconds) {//Doesn't Work
        return Commands.sequence(
            runOnce(()-> setTargetVelocity(velocity)),
            Commands.waitSeconds(waitSeconds),
            runOnce(this::stop)
        );
    }

    public boolean isElementIn(){
        return getEncoderVelocityError()<-2000;
    }

}

