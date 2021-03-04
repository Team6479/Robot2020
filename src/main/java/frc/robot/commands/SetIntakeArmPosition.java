/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeArm.Position;

public class SetIntakeArmPosition extends CommandBase {
  private final double TOLLERANCE = 300;

  private final IntakeArm intakeArm;
  private final Position position;

  // private double prevPosition = 0;
  private double currentPosition = 0;

  /**
   * Creates a new SetIntakeArmPosition.
   */
  public SetIntakeArmPosition(IntakeArm intakeArm, Position position) {
    this.intakeArm = intakeArm;
    this.position = position;
    addRequirements(intakeArm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intakeArm.setPosition(position);

    boolean interrupt = false;
    switch (position) {
      case In:
        interrupt = intakeArm.isAtBackLimit();
        break;
      case Out:
        interrupt = intakeArm.isAtFrontLimit();
    }
    if (interrupt) {
      this.cancel();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // prevPosition = currentPosition;
    currentPosition = intakeArm.getPosition();

    if (currentPosition > position.value) {
      intakeArm.set(-0.3);
    } else {
      intakeArm.set(0.3);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intakeArm.armStop();
    DriverStation.reportWarning("Finished!", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println(intakeArm.getCurrent());
    // || Math.abs(intakeArm.getCurrent()) >= 3 || Math.abs(prevPosition - currentPosition) <= 5
    boolean finished = Math.abs(intakeArm.getCurrent()) >= 30;

    switch (position) {
      case In:
        finished = finished || currentPosition >= position.value || intakeArm.isAtBackLimit();
        break;
      case Out:
        finished = finished || currentPosition <= position.value || intakeArm.isAtFrontLimit();
        break;
      default:
        finished = finished || Math.abs(currentPosition - position.value) <= TOLLERANCE;
        break;
    }

    return finished;
  }
}
