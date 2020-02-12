/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class StraightDrive extends CommandBase {
  private final Drivetrain drivetrain;
  private final double SPEED;
  private final double DISTANCE;

  /**
   * Creates a new StraightDrive.
   */
  public StraightDrive(Drivetrain drivetrain, double speed, double distance) {
    this.drivetrain = drivetrain;
    this.SPEED = speed;
    this.DISTANCE = distance;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.arcadeDrive(SPEED, RobotContainer.navX.getYaw() * 0.3);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drivetrain.getPosition() >= DISTANCE;
  }
}
