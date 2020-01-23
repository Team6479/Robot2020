/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeRollers;

public class TurnIntakeRollers extends CommandBase {
  /**
   * Creates a new TurnIntakeRollers.
   */
  private final IntakeRollers intakeRollers;
  private final IntakeArm intakeArm;
  private final Button toggleRollers;
  private final Button toggleArm;

  public TurnIntakeRollers(IntakeRollers intakeRollers, IntakeArm intakeArm, Button toggleRollers, Button toggleArm) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intakeRollers = intakeRollers;
    this.intakeArm = intakeArm;
    this.toggleRollers = toggleRollers;
    this.toggleArm = toggleArm;
    addRequirements(this.intakeRollers);
    addRequirements(this.intakeArm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(intakeArm.isUp()){
      if(intakeRollers.getSpeedValue() == 0.0)  // THIS IS NOT DONE DON'T THINK IT's DONE
      return;
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
