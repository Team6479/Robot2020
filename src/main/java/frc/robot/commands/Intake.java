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

  private boolean hasBegun; // make sure that the arm doesn't immediately stop when started

  public Intake(IntakeArm intakeArm, IntakeRollers intakeRollers) {
    this.intakeRollers = intakeRollers;
    this.intakeArm = intakeArm;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.intakeArm);
    addRequirements(this.intakeRollers);

    hasBegun = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakeArm.initLimitCounters();
    hasBegun = false;
  }

  // Called every time the scheduler runs while the command is scheduled.

  // Checks which limit switch is currently pressed and moves the arm and rollers based on that (may change later)
  @Override
  public void execute() {
    if(intakeArm.isSet(LimitSwitch.SWITCHOUT)) {
      intakeArm.armIn();
      intakeRollers.rollersOff();
      hasBegun = true;
    } else if(intakeArm.isSet(LimitSwitch.SWITCHIN)) {
      intakeArm.armOut();
      intakeRollers.rollersOn();
      hasBegun = true;
    }
  }

  // Called once the command ends or is interrupted.
  // Stop the arm when the switch is hit
  @Override
  public void end(boolean interrupted) {
    intakeArm.armStop();
  }

  // Returns true when the command should end.
  // Stop command when either switch is pressed
  @Override
  public boolean isFinished() {
    return (intakeArm.isSet(LimitSwitch.SWITCHOUT) || intakeArm.isSet(LimitSwitch.SWITCHIN)) && hasBegun;
  }
}
