/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.team6479.lib.util.Limelight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheels;
import frc.robot.util.DistanceCalculator;

public class FlywheelLogging extends CommandBase {
  /**
   * Creates a new FlywheelLogging.
   */

   private Flywheels flywheels;
   private DistanceCalculator distanceCalculator;

  public FlywheelLogging(Flywheels flywheels) {
    this.flywheels = flywheels;
    addRequirements(flywheels);
    distanceCalculator = new DistanceCalculator(21, 93, 0.4014); // inches inches radians
    // Use addRequirements() here to declare subsystem dependencies.
  }

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
    SmartDashboard.putNumber("Top FW Error", getTopRPM() - flywheels.getSmallSpeed());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
