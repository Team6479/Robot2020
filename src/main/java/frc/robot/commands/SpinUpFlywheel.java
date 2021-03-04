/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.team6479.lib.util.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheels;
import frc.robot.util.DistanceCalculator;

public class SpinUpFlywheel extends CommandBase {
  private final double BIG_TOLERANCE = 300;
  private final double SMALL_TOLERANCE = 100;

  private final Flywheels flywheels;
  private DistanceCalculator distanceCalculator;

  private double smallSetpoint;

  public SpinUpFlywheel(Flywheels flywheels) {
    this.flywheels = flywheels;
    distanceCalculator = new DistanceCalculator(20, 92, 0.698); // TODO: remeasure angle
    addRequirements(this.flywheels);

    smallSetpoint = Double.MAX_VALUE; // can't be zero or it might think it's over before it starts
  }

  /**
   * Checks whether or not the shooter can hit the inner port from its current position This method
   * may contain magic numbers which are derived from real-world testing
   *
   * @param distance The distance to the target (in)
   * @param angle    The angle from the target (deg)
   */
  private boolean checkInnerPort(double distance, double angle) {
    return (angle >= -180 && angle <= 180) && (distance > 16 && distance < 256);
  }

  /**
   * Calculates the ideal flywheel RPM to hit the highest-scoring target possible This method may
   * contain magic numbers which are derived from regression models of data from real-world testing
   *
   * @param distance The distance to the target (in)
   */
  private double calculateSmall(double distance) {
    return 0; // TODO: calculate
  }

  private double calculateBig(double distance) {
    return 0; // TODO: calculate
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (Limelight.hasTarget()) {
      double distance = distanceCalculator.calculate(Math.toRadians(Limelight.getYOffset()));
      flywheels.setSpeed(calculateBig(distance), calculateSmall(distance));
    } else {
      // TODO: handle
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Math.abs(flywheels.getBigError()) < BIG_TOLERANCE) &&
        (Math.abs(flywheels.getSmallError(smallSetpoint)) < SMALL_TOLERANCE);
  }
}
