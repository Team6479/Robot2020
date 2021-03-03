package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class StraightDrive extends CommandBase {
  private static final double DISTANCE_TOLERANCE = 1000;
  private static final double STRAIGHTNESS_TOLERANCE = 300;
  private static final double SLOW_THRESHOLD = 0.1;
  private static final double SLOWDOWN_ERROR = 4096; // once it hits error this low it slows down

  private final Drivetrain drivetrain;
  private final double goal;

  private boolean tooSlowTriggerEnd;

  /**
   * @param goal raw encoder units
   */
  public StraightDrive(Drivetrain drivetrain, double goal) {
    this.drivetrain = drivetrain;
    this.goal = goal;

    addRequirements(drivetrain);

    tooSlowTriggerEnd = false;
  }

  @Override
  public void initialize() {
    drivetrain.resetEncoders();
  }

  @Override
  public void execute() {
    double errorLeft = goal - drivetrain.getLeftEncoderPos();
    double errorRight = goal - drivetrain.getRightEncoderPos();
    double speedLeft = Math.min(1, errorLeft / SLOWDOWN_ERROR);
    double speedRight = Math.min(1, errorRight / SLOWDOWN_ERROR);
    drivetrain.tankDrive(speedLeft, speedRight);
    tooSlowTriggerEnd = (Math.abs(speedLeft) < SLOW_THRESHOLD) && (Math.abs(speedRight) < SLOW_THRESHOLD);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  @Override
  public boolean isFinished() {
    return ((Math.abs(goal - drivetrain.getLeftEncoderPos()) < DISTANCE_TOLERANCE) &&
        (Math.abs(goal - drivetrain.getRightEncoderPos()) < DISTANCE_TOLERANCE) &&
        (Math.abs(drivetrain.getLeftEncoderPos() - drivetrain.getRightEncoderPos()) < STRAIGHTNESS_TOLERANCE)) ||
        tooSlowTriggerEnd;
  }
}
