/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheels;

public class ManualSpeedFlywheel extends CommandBase {
  private final Flywheels flywheels;

  public ManualSpeedFlywheel(Flywheels flywheels) {
    this.flywheels = flywheels;
    addRequirements(this.flywheels);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Big Flywheel c/ds", 0);
    SmartDashboard.putNumber("Small Flywheel RPM", 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    flywheels.setSpeed(
        SmartDashboard.getNumber("Big Flywheel c/ds", 0),
        SmartDashboard.getNumber("Small Flywheel RPM", 0));
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
