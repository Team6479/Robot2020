/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.subsystems.IntakeArm;

public class TeleopIntakeArm extends CommandBase {
  private final IntakeArm intakeArm;

  private final Button outButton;
  private final Button inButton;

  /**
   * Creates a new TeleopIntake.
   */
  public TeleopIntakeArm(IntakeArm intakeArm, Button outButton, Button inButton) {
    this.intakeArm = intakeArm;
    addRequirements(intakeArm);

    this.outButton = outButton;
    this.inButton = inButton;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (outButton.get()) {
      intakeArm.set(-0.25);
    } else if (inButton.get()) {
      intakeArm.set(0.25);
    } else {
      intakeArm.set(0);
    }
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
