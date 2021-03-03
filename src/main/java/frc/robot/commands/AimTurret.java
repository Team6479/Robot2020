/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.team6479.lib.util.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class AimTurret extends CommandBase {
  private final Turret turret;

  public AimTurret(Turret turret) {
    this.turret = turret;
    addRequirements(this.turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean isCorrected = turret.isCorrected();
    boolean hasTarget = Limelight.hasTarget();

    if (!isCorrected && !hasTarget) {
      turret.clearCorrection();
    }

    if (hasTarget && isCorrected) {
      turret.setPosition(turret.getCurrentAngle() + Limelight.getXOffset());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.clearCorrection();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(Limelight.getXOffset()) <= 0.5 && Limelight.hasTarget();
  }
}