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
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team6479.lib.subsystems.TankDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase implements TankDrive {
  private final TalonSRX motorLeftFront = new TalonSRX(DrivetrainConstants.MOTOR_LEFT_FRONT);
  private final TalonSRX motorLeftBack = new TalonSRX(DrivetrainConstants.MOTOR_LEFT_BACK);
  private final TalonSRX motorRightFront = new TalonSRX(DrivetrainConstants.MOTOR_RIGHT_FRONT);
  private final TalonSRX motorRightBack = new TalonSRX(DrivetrainConstants.MOTOR_RIGHT_BACK);

  public Drivetrain() {
    // Reset to factory defaults to ensure no config carryover
    motorLeftFront.configFactoryDefault();
    motorLeftBack.configFactoryDefault();
    motorRightFront.configFactoryDefault();
    motorRightBack.configFactoryDefault();

    // Have back motors follow front motors
    motorLeftBack.follow(motorLeftFront);
    motorRightBack.follow(motorRightFront);

    // Set the neutral mode
    motorLeftFront.setNeutralMode(NeutralMode.Brake);
    motorLeftBack.setNeutralMode(NeutralMode.Brake);
    motorRightFront.setNeutralMode(NeutralMode.Brake);
    motorRightBack.setNeutralMode(NeutralMode.Brake);

    // Setup encoders
    motorRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    motorLeftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

    // Invert Right Motors
    motorLeftFront.setInverted(false);
    motorLeftBack.setInverted(false);
    motorRightFront.setInverted(true);
    motorRightBack.setInverted(true);

    motorLeftFront.setSensorPhase(true);
    motorRightFront.setSensorPhase(true);
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

  public double getPosition() {
    double leftSide = motorLeftFront.getSelectedSensorPosition();
    double rightSide = motorRightFront.getSelectedSensorPosition();

    return (leftSide + rightSide) / 2;
  }

  public void resetEncoders() {
    motorLeftFront.setSelectedSensorPosition(0, 0, 10);
    motorRightFront.setSelectedSensorPosition(0, 0, 10);
  }
}
