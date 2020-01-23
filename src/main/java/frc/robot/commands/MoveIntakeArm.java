/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeRollers;

public class MoveIntakeArm extends CommandBase {
  /**
   * Creates a new MoveIntakeArm.
   */
  private final IntakeRollers intakeRollers;
  private final IntakeArm intakeArm;

  public MoveIntakeArm(IntakeRollers intakeRollers, IntakeArm intakeArm) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intakeRollers = intakeRollers;
    this.intakeArm = intakeArm;
    addRequirements(this.intakeArm);
    addRequirements(this.intakeRollers);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // toggles the rollers off when moving the arm up
    if(intakeRollers.getSpeedValue() == 1.0){
      intakeRollers.toggleRollers();
    }
    intakeArm.toggleArm();
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
