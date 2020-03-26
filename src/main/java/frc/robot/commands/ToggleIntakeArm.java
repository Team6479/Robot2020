/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeArm.Position;

public class ToggleIntakeArm extends CommandBase {
  private final double AMPERAGE_SPIKE_THESHOLD = 30;

  private static final PowerDistributionPanel pdp = new PowerDistributionPanel();
  private final IntakeArm intakeArm;

  private double speed = 0;

  /**
   * Creates a new MoveIntakeArm.
   */
  public ToggleIntakeArm(IntakeArm intakeArm) {
    this.intakeArm = intakeArm;
    addRequirements(intakeArm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (intakeArm.isOut()) {
      speed = -1;
    } else {
      speed = 1;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intakeArm.set(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (intakeArm.isOut()) {
      intakeArm.setPosition(Position.In);
    } else {
      intakeArm.setPosition(Position.Out);
    }
    intakeArm.armStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(pdp.getCurrent(IntakeConstants.INTAKE_ARM_PDP)) >= AMPERAGE_SPIKE_THESHOLD;
  }
}
