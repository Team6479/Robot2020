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
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;

public class StraightDrive extends CommandBase {
  private final Drivetrain drivetrain;
  private final NavX navX;
  private final double SPEED;
  private final double DISTANCE;
  private double navXStartPosition;

  /**
   * Creates a new StraightDrive.
   * 
   * @param speed Motor speed, from -1 to 1
   * @param distance Distance in inches
   */
  public StraightDrive(Drivetrain drivetrain, NavX navX, double speed, double distance) {
    this.drivetrain = drivetrain;
    this.navX = navX;
    this.SPEED = speed;
    this.DISTANCE = (distance / (Math.PI * 5.0)) * 4096.0; // 5.0 is wheel diameter in inches
    addRequirements(drivetrain, navX);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriverStation.reportError("SD Started", false);
    this.navX.reset();
    this.navXStartPosition = navX.getYaw();
    drivetrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // drivetrain.arcadeDrive(SPEED, -navX.getYaw() * 0.05);
    SmartDashboard.putNumber("SD NavX offset", Math.abs(navXStartPosition - navX.getYaw()));
    drivetrain.arcadeDrive(SPEED, 0.05 * (navXStartPosition - navX.getYaw()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriverStation.reportError("SD Done", false);
    drivetrain.resetEncoders();
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    SmartDashboard.putNumber("StraightDrive distance", drivetrain.getPosition());
    
    return Math.abs(drivetrain.getPosition()) > DISTANCE;
  }
}
