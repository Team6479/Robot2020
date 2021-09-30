/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;

public class TeleopDrive extends CommandBase {

  private Drivetrain drivetrain;
  private NavX navX;
  private DoubleSupplier forward;
  private DoubleSupplier turn;
  private double navXStartPosition;

  private final double STICK_TOLERANCE = 0.1;

  /**
   * Creates a new TeleopDrive.
   * 
   * <p>Uses a NavX to correct for L/R turning while driving straight
   */
  public TeleopDrive(Drivetrain drivetrain, NavX navX, double navXStartPosition, DoubleSupplier forward, DoubleSupplier turn) {
    this.drivetrain = drivetrain;
    this.navX = navX;

    this.forward = forward;
    this.turn = turn;

    this.navXStartPosition = navXStartPosition;

    addRequirements(drivetrain);
    addRequirements(navX);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // prevents error while driving straight within a tolerance

    // 0.05 is an arbitrary value so the actual speed is set to between -1 and 1
    // if(Math.abs(turn.getAsDouble()) < STICK_TOLERANCE && Math.abs(forward.getAsDouble()) > STICK_TOLERANCE) {
    //   drivetrain.arcadeDrive(forward.getAsDouble(), 0.05 * -Math.abs(navXStartPosition - navX.getYaw()));
    // } else {
    //   navXStartPosition = navX.getYaw();
    //   drivetrain.arcadeDrive(forward.getAsDouble(), turn.getAsDouble());
    // }

    drivetrain.arcadeDrive(forward.getAsDouble(), turn.getAsDouble()); // old version, no error correction
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
