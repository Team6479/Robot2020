package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Turn180NavX extends CommandBase {
  private static final double TOLERANCE = 5; // degrees

  private final Drivetrain drivetrain;

  public Turn180NavX(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void initialize() {
    drivetrain.resetNavX();
  }

  @Override
  public void execute() {
    double error = 180 - Math.abs(drivetrain.getHeading()); // possible error with negative angles
    double speed = 1;
    if (error > 120) {
      speed = 1;
    } else if (error > 90) {
      speed = 0.75;
    } else if (error > 60) {
      speed = 0.5;
    } else {
      speed = 0.25;
    }
    drivetrain.tankDrive(speed, -1 * speed); // turn right
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  @Override
  public boolean isFinished() {
    return Math.abs(180 - Math.abs(drivetrain.getHeading())) < TOLERANCE;
  }
}
