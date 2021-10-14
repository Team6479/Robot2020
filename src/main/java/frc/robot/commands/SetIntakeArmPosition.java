/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeArm.Position;

public class SetIntakeArmPosition extends CommandBase {
  private final double TOLLERANCE = 300;

  private final IntakeArm intakeArm;
  private final Position position;
  private final double SPEED;

  // private double prevPosition = 0;
  private double currentPosition = 0;

  /**
   * Creates a new SetIntakeArmPosition.
   */
  public SetIntakeArmPosition(IntakeArm intakeArm, Position position) {
    this.intakeArm = intakeArm;
    this.position = position;
    addRequirements(intakeArm);
    this.SPEED = 0.35;
  }

  public SetIntakeArmPosition(IntakeArm intakeArm, Position position, double speed) {
    this.intakeArm = intakeArm;
    this.position = position;
    addRequirements(intakeArm);
    this.SPEED = speed;
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
      DriverStation.reportWarning("Interrupted", false);
      this.cancel();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // prevPosition = currentPosition;

    if (position == Position.Out && !intakeArm.isAtFrontLimit()) {
      intakeArm.set(-SPEED);
    } else if(position == Position.In && !intakeArm.isAtBackLimit()) {
      intakeArm.set(SPEED);
    } else {
      intakeArm.set(0);
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
    // boolean finished = Math.abs(intakeArm.getCurrent()) >= 30;
    boolean finished = false;

    switch (position) {
      case In:
        // DriverStation.reportWarning("Current Position: " + intakeArm.getPosition() + ", Target: " + position.value, false);
        finished = finished || intakeArm.isAtBackLimit();
        break;
      case Out:
        finished = finished || intakeArm.isAtFrontLimit();
        break;
    }

    return finished;
  }
}
