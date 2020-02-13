/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class TurnDrivetrain extends CommandBase {
  public enum Direction {
    Left, Right
  }

  private final Drivetrain drivetrain;
  private final double GOAL;
  private Direction DIRECTION;
  private PIDController pid = new PIDController(0, 0, 0);

  /**
   * Creates a new TurnDrivetrain.
   */
  public TurnDrivetrain(Drivetrain drivetrain, double angle, Direction direction) {
    this.drivetrain = drivetrain;
    this.GOAL = angle;
    this.DIRECTION = direction;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = pid.calculate(RobotContainer.navX.getYaw(), GOAL);

    if (DIRECTION == Direction.Left) {
      drivetrain.tankDrive(-speed, speed);
    }
    else {
      drivetrain.tankDrive(speed, -speed);
    }

    drivetrain.tankDrive(0, 0);
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