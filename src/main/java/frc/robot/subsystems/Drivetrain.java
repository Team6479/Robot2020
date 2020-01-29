/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team6479.lib.subsystems.TankDrive;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase implements TankDrive {
  private TalonSRX motorLeftFront = new TalonSRX(DrivetrainConstants.motorLeftFront);
  private TalonSRX motorLeftBack = new TalonSRX(DrivetrainConstants.motorLeftBack);
  private TalonSRX motorRightFront = new TalonSRX(DrivetrainConstants.motorRightFront);
  private TalonSRX motorRightBack = new TalonSRX(DrivetrainConstants.motorRightBack);


  /**
   * Creates a new Drivetrain.
   */
  public Drivetrain() {
    // Reset to factory defaults to ensure no config carryover
    motorLeftFront.configFactoryDefault();
    motorLeftBack.configFactoryDefault();
    motorRightFront.configFactoryDefault();
    motorRightBack.configFactoryDefault();

    // Have back motors follow front motors
    motorLeftBack.follow(motorLeftFront);
    motorRightBack.follow(motorRightFront);

    // Setup encoders
    motorRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

    // Invert Right Motors
    motorRightFront.setInverted(true);
    motorLeftFront.setInverted(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void stop() {
    motorLeftFront.set(ControlMode.PercentOutput, 0);
    motorRightFront.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void arcadeDrive(double forward, double turn) {
    motorLeftFront.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, +turn);
    motorRightFront.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
  }

  @Override
  public void tankDrive(double leftSpeed, double rightSpeed) {
    motorLeftFront.set(ControlMode.PercentOutput, leftSpeed);
    motorRightFront.set(ControlMode.PercentOutput, rightSpeed);
  }
}
