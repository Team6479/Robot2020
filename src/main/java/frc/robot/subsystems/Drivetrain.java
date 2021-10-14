/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.team6479.lib.subsystems.TankDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase implements TankDrive {
  private final TalonSRX motorLeftFront = new TalonSRX(DrivetrainConstants.MOTOR_LEFT_FRONT);
  private final TalonSRX motorLeftBack = new TalonSRX(DrivetrainConstants.MOTOR_LEFT_BACK);
  private final TalonSRX motorRightFront = new TalonSRX(DrivetrainConstants.MOTOR_RIGHT_FRONT);
  private final TalonSRX motorRightBack = new TalonSRX(DrivetrainConstants.MOTOR_RIGHT_BACK);

  private DifferentialDriveOdometry odometry;
  private RamseteController ramseteController;
  private AHRS navX = new AHRS(SPI.Port.kMXP);


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

    this.resetEncoders();

    // Invert Right Motors
    motorLeftFront.setInverted(false);
    motorLeftBack.setInverted(false);
    motorRightFront.setInverted(true);
    motorRightBack.setInverted(true);

    motorLeftFront.setSensorPhase(true);
    motorRightFront.setSensorPhase(true);

    odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));
    ramseteController = new RamseteController(AutoConstants.ramseteB, AutoConstants.ramseteZeta);

  }

  @Override
  public void periodic() {
    odometry.update(Rotation2d.fromDegrees(getHeading()), getLeftEncoderPos(), getRightEncoderPos());
  }

  @Override
  public void stop() {
    motorLeftFront.set(ControlMode.PercentOutput, 0);
    motorRightFront.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void arcadeDrive(double forward, double turn) {
    SmartDashboard.putNumber("Actual turn value", turn);
    SmartDashboard.putNumber("Left encoder vel", getLeftEncoderVel());
    SmartDashboard.putNumber("Right encoder vel", getRightEncoderVel());
    motorLeftFront.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, +turn);
    motorRightFront.set(ControlMode.PercentOutput, forward, DemandType.ArbitraryFeedForward, -turn);
  }

  @Override
  public void tankDrive(double leftSpeed, double rightSpeed) {
    motorLeftFront.set(ControlMode.PercentOutput, leftSpeed);
    motorRightFront.set(ControlMode.PercentOutput, rightSpeed);
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftEncoderVel(), getRightEncoderVel());
  }

  public double getLeftEncoderPos() {
    return motorLeftFront.getSelectedSensorPosition(0) * DriveConstants.encoderDistancePerPulse;
  }

  public double getRightEncoderPos() {
    return motorRightFront.getSelectedSensorPosition(0) * DriveConstants.encoderDistancePerPulse;
  }

  public double getLeftEncoderVel() {
    return motorLeftFront.getSelectedSensorVelocity(0) * DriveConstants.encoderDistancePerPulse * 10;
  }

  public double getRightEncoderVel() {
    return motorRightFront.getSelectedSensorVelocity(0) * DriveConstants.encoderDistancePerPulse * 10;
  }

  //Returns the currently-estimated pose of the robot
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  //Controls the left and right sides of the drive directly with voltages
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    motorLeftFront.set(ControlMode.PercentOutput, leftVolts / RobotController.getBatteryVoltage());
    motorRightFront.set(ControlMode.PercentOutput, rightVolts / RobotController.getBatteryVoltage());
  }


  public double getPosition() {
    double leftSide = motorLeftFront.getSelectedSensorPosition();
    double rightSide = motorRightFront.getSelectedSensorPosition();

    return rightSide; // more reliable side
  }

  public void resetEncoders() {
    motorLeftFront.setSelectedSensorPosition(0, 0, 50);
    motorRightFront.setSelectedSensorPosition(0, 0, 50);
  }

  //Returns the heading of the robot from -180 to 180 degrees
  public double getHeading() {
    return Math.IEEEremainder(navX.getAngle(), 360) * (DriveConstants.gyroReversed ? -1.0 : 1.0);
  }

  public RamseteCommand getRamseteCommand(Trajectory trajectory) {
    return new RamseteCommand(trajectory.transformBy(getPose().minus(trajectory.getInitialPose())), this::getPose, ramseteController,
        new SimpleMotorFeedforward(DriveConstants.ksVolts, DriveConstants.kvVoltSecondsPerMeter,
            DriveConstants.kaVoltSecondsSquaredPerMeter),
        DriveConstants.driveKinematics, this::getWheelSpeeds, new PIDController(DriveConstants.pDriveVel, 0, 0),
        new PIDController(DriveConstants.pDriveVel, 0, 0), this::tankDriveVolts, this);
  }

}
