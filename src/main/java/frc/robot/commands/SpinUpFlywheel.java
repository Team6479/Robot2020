/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.team6479.lib.util.Limelight;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    distanceCalculator = new DistanceCalculator(20, 92, 0.4955); // TODO: remeasure angle
    addRequirements(this.flywheels);

    smallSetpoint = Double.MAX_VALUE; // can't be zero or it might think it's over before it starts
    // SmartDashboard.putNumber("small rpm", 0);
    // SmartDashboard.putNumber("big rpm", 0);
  }

  /**
   * Calculates the ideal flywheel RPM to hit the highest-scoring target possible This method may
   * contain magic numbers which are derived from regression models of data from real-world testing
   *
   * @param distance The distance to the target (in)
   */
  private double calculateSmall(double distance) {
    if (distance <= 184) { // close
      // quadratic: -0.178276x^2 + 79.6397x - 6984.8
      return Math.max(0, // TODO: investigate negative rpm
          (26.074 * distance) - 3030.42);
    } else {
      return (25.96 * distance) - 4372.66;
      // return SmartDashboard.getNumber("small rpm", 0);
    }
  }

  // https://www.desmos.com/calculator/z3u4ucqboa
  // TODO: get data points for farther distances
  private double calculateBig(double distance) {
    if (distance <= 184) {
      // quadratic: .0227007x^2 - 52.8617x + 23904
      return 23401 - (46.0409 * distance);
    } else {
      return (-11.37 * distance) + 16430.7;
      // return SmartDashboard.getNumber("big rpm", 0);
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (Limelight.hasTarget()) {
      DriverStation.reportError("here", false);
      SmartDashboard.putNumber("angle thing math", Math.toRadians(Limelight.getYOffset()));
      double distance = distanceCalculator.calculate(Math.toRadians(Limelight.getYOffset()));
      SmartDashboard.putNumber("distance", distance);
      smallSetpoint = calculateSmall(distance);
      SmartDashboard.putNumber("small setpoint", smallSetpoint);
      flywheels.setSpeed(calculateBig(distance), smallSetpoint);
    } else {
      DriverStation.reportError("No target", false);
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
    SmartDashboard.putNumber("small error", flywheels.getSmallError(smallSetpoint));
    return (Math.abs(flywheels.getBigError()) < BIG_TOLERANCE) &&
        (Math.abs(flywheels.getSmallError(smallSetpoint)) < SMALL_TOLERANCE);
  }
}
