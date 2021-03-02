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

public class TrenchSpinUpFlywheels extends CommandBase {
  private final double THRESHOLD_RPM = 3;

  private final Flywheels flywheels;

  public TrenchSpinUpFlywheels(Flywheels flywheels) {
    this.flywheels = flywheels;
    addRequirements(this.flywheels);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    flywheels.setSpeed(15000, 800);
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
    // return Math.abs(flywheels.getError()) < THRESHOLD_RPM;
    return true; // TODO: check error
  }
}
