package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Turn180Encoders extends CommandBase {
  private static final double DISTANCE_TOLERANCE = 1000;
  private static final double SLOW_THRESHOLD = 0.1;
  private static final double SLOWDOWN_ERROR = 4096; // once it hits error this low it slows down
  
  private static final double GOAL_LEFT = 0; // TODO: determine goals for left and right
  private static final double GOAL_RIGHT = -0;

  private final Drivetrain drivetrain;

  private boolean tooSlowTriggerEnd;

  /**
   * @param goal raw encoder units
   */
  public Turn180Encoders(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    addRequirements(drivetrain);

    tooSlowTriggerEnd = false;
  }

  @Override
  public void initialize() {
    drivetrain.resetEncoders();
  }

  @Override
  public void execute() {
    double errorLeft = GOAL_LEFT - drivetrain.getLeftEncoderPos();
    double errorRight = GOAL_RIGHT - drivetrain.getRightEncoderPos();
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
    return ((Math.abs(GOAL_LEFT - drivetrain.getLeftEncoderPos()) < DISTANCE_TOLERANCE) &&
        (Math.abs(GOAL_RIGHT - drivetrain.getRightEncoderPos()) < DISTANCE_TOLERANCE)) ||
        tooSlowTriggerEnd;
  }
}