/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeRollers;
import frc.robot.subsystems.IntakeArm.LimitSwitch;

public class Intake extends CommandBase {
  /**
   * Creates a new Intake.
   */
  private final IntakeRollers intakeRollers;
  private final IntakeArm intakeArm;

  public Intake(IntakeArm intakeArm, IntakeRollers intakeRollers) {
    this.intakeRollers = intakeRollers;
    this.intakeArm = intakeArm;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.intakeArm);
    addRequirements(this.intakeRollers);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakeArm.initLimitCounters();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(intakeArm.isSet(LimitSwitch.SWITCHOUT)) {
      intakeArm.armIn();
      intakeRollers.rollersOff();
    } else if(intakeArm.isSet(LimitSwitch.SWITCHIN)) {
      intakeArm.armOut();
      intakeRollers.rollersOn();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return intakeArm.isSet(LimitSwitch.SWITCHOUT) || intakeArm.isSet(LimitSwitch.SWITCHIN);
  }
}
