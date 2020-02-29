/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.team6479.lib.util.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;
import frc.robot.util.DistanceCalculator;

public class SpinUpFlywheel extends CommandBase {
  private final double THRESHOLD_RPM = 3;

  private final Flywheel flywheel;
  private DistanceCalculator distanceCalculator;

  public SpinUpFlywheel(Flywheel flywheel) {
    this.flywheel = flywheel;
    distanceCalculator = new DistanceCalculator(0, 0, 0);
    addRequirements(this.flywheel);
  }

  /**
   * Calculates the ideal flywheel RPM to hit the highest-scoring target possible
   * This method may contain magic numbers which are derived from regression models of data from real-world testing
   * @param distance The distance to the target (in)
   * @param angle The angle from the target (deg)
   */
  private double calculate(double distance) {
    return (-0.0003607 * Math.pow(distance, 3)) + (1.027 * Math.pow(distance, 2)) + (428.1 * distance) + 88422.6; // based on inner port testing only
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (Limelight.hasTarget()) {
      flywheel.set(calculate(
          distanceCalculator.calculate(
            Math.toRadians(Limelight.getYOffset())
          )));
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
    return Math.abs(flywheel.getError()) < THRESHOLD_RPM;
  }
}
