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
  }

  // Called every time the scheduler runs while the command is scheduled.
  /**
   * Controls the entire intake subsystem with one button/command. Toggles the intake's arm with {@link IntakeArm}
   * which will automatically toggle the {@link IntakeRollers} depending on the position of the arm after 
   */
  @Override
  public void execute() {
    intakeArm.toggleArm();
    if(intakeArm.isOut()) {
      intakeRollers.rollersOn();
    } else {
      intakeRollers.rollersOff();
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
