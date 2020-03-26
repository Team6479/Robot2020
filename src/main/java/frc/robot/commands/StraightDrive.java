/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;

public class StraightDrive extends CommandBase {
  private final Drivetrain drivetrain;
  private final NavX navX;
  private final double SPEED;
  private final double DISTANCE;

  /**
   * Creates a new StraightDrive.
   */
  public StraightDrive(Drivetrain drivetrain, NavX navX, double speed, double distance) {
    this.drivetrain = drivetrain;
    this.navX = navX;
    this.SPEED = speed;
    this.DISTANCE = ((double) distance / (Math.PI * 5.0)) * 4096.0;
    addRequirements(drivetrain, navX);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    navX.reset();
    drivetrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.arcadeDrive(SPEED, -navX.getYaw() * 0.05);
    System.out.println("drive position: " + drivetrain.getPosition());
    System.out.println("distance: " + DISTANCE);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(drivetrain.getPosition()) >= DISTANCE;
  }
}
