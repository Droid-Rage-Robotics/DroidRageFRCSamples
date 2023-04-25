package frc.robot.commands.manual;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DroidRageConstants;
import frc.robot.subsystems.SetPowerSparkMax;
public class ManualSparkMaxSetPower extends CommandBase {
    private final SetPowerSparkMax setPowerSparkMax;
    private final Supplier<Double> setPowerMove;
    
    public ManualSparkMaxSetPower(Supplier<Double> verticalElevatorMove, SetPowerSparkMax setPowerSparkMax) {
        this.setPowerSparkMax = setPowerSparkMax;
        this.setPowerMove = verticalElevatorMove;
        
        addRequirements(setPowerSparkMax);
    }

    @Override
    public void initialize() { }

    @Override
    public void execute() {
        double move = -setPowerMove.get();
        move = DroidRageConstants.squareInput(move);
        move = DroidRageConstants.applyDeadBand(move);
        setPowerSparkMax.setPower(move * 0.2);
        setPowerSparkMax.setMovingManually(!(move == 0));
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
