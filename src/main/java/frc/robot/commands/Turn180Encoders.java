package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Turn180Encoders extends CommandBase {
  private static final double DISTANCE_TOLERANCE = 1000;
  private static final double SLOW_THRESHOLD = 0.1;
  private static final double SLOWDOWN_ERROR = 4096; // once it hits error this low it slows down

  private static final double CPR = 4096;
  private static final double WHEEL_DIAM = 5; // inches
  private static final double IN_TO_ENCU = CPR / (WHEEL_DIAM * Math.PI); // encoder units per rotation / inches per rotation
  private static final double DRIVETRAIN_WIDTH = 26.13; // inches // TODO: determine actual width
  private static final double WHEEL_DISTANCE = DRIVETRAIN_WIDTH * Math.PI / 2; // inches; half circumference of drivetrain circle for 180deg
  private static final double WHEEL_ENCODER_DISTANCE = WHEEL_DISTANCE * IN_TO_ENCU;
  
  private static final double GOAL_LEFT = WHEEL_ENCODER_DISTANCE; // left forward = turn right
  private static final double GOAL_RIGHT = -1 * WHEEL_ENCODER_DISTANCE; // opposite direction from left

  private final Drivetrain drivetrain;

  private boolean tooSlowTriggerEnd;

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
    double errorRight = GOAL_RIGHT - drivetrain.getRightEncoderPos(); // possible that these would have to be flipped because negative
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