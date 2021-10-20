/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.team6479.lib.util.Limelight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheels;
import frc.robot.util.DistanceCalculator;

public class SpinUpFlywheels extends CommandBase {
  private final double THRESHOLD_RPM = 3;

  private final Flywheels flywheels;
  private DistanceCalculator distanceCalculator;

  public SpinUpFlywheels(Flywheels flywheels) {
    this.flywheels = flywheels;
    distanceCalculator = new DistanceCalculator(21, 93, 0.4014); // inches inches radians
    addRequirements(this.flywheels);
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
   * Uses regression equation from real world testing to determine the top wheel rpm, along with distance to the target calculated with the limelight
   */
  private double getTopRPM() {
    return 4.2555438225977*distanceCalculator.calculate(Math.toRadians(Limelight.getYOffset()))+549.31362196409;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Top FW Speed", getTopRPM());
    SmartDashboard.putNumber("Top FW Error", getTopRPM() - flywheels.getSmallSpeed());
    SmartDashboard.putNumber("Distance from target", distanceCalculator.calculate(Math.toRadians(Limelight.getYOffset()))); // base of the triangle
    if (Limelight.hasTarget()) {
      flywheels.setSpeed(16700, getTopRPM());
    } else {
      // TODO: handle
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return Math.abs(flywheels.getError()) < THRESHOLD_RPM;
    return Limelight.hasTarget(); // TODO: check error
  }
}
