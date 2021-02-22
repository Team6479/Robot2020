/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class IntakeConstants {
    public static final int INTAKE_ROLLER = 9;
    public static final int INTAKE_ARM = 8;
    public static final int INTAKE_ARM_LIMIT_SWITCH_FRONT = 0;
    public static final int INTAKE_ARM_LIMIT_SWITCH_BACK = 1;
    public static final int INTAKE_ARM_PDP = 5;
  }

  public static final class DrivetrainConstants {
    public static final int MOTOR_LEFT_FRONT = 0;
    public static final int MOTOR_LEFT_BACK = 1;
    public static final int MOTOR_RIGHT_FRONT = 2;
    public static final int MOTOR_RIGHT_BACK = 3;
  }

  public static final class FlywheelConstants {
    public static final int BIG_LEFT = 5;
    public static final int BIG_RIGHT = 6;
    public static final int SMALL_LEFT = 12;
    public static final int SMALL_RIGHT = 13;
  }

  public static final class TurretConstants {
    public static final int MOTOR = 4;
  }

  public static final class IndexerConstants {
    public static final int MOTOR = 7;
  }

  public static final class AlignmentBeltConstants {
    public static final int MOTOR = 10;
  }

  public static final class DriveConstants {
    public static final int leftMotor1Port = 0;
    public static final int leftMotor2Port = 1;
    public static final int rightMotor1Port = 2;
    public static final int rightMotor2Port = 3;

    public static final int[] leftEncoderPorts = new int[] { 0, 1 };
    public static final int[] rightEncoderPorts = new int[] { 2, 3 };
    public static final boolean leftEncoderReversed = true;
    public static final boolean rightEncoderReversed = false;

    //horizontal distance between the wheels
    public static final double trackwidthMeters = 0.65595;
    public static final DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(trackwidthMeters);

    public static final int encoderCPR = 4096;
    public static final double wheelDiameterMeters = 0.1524;
    public static final double encoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (wheelDiameterMeters * Math.PI) / (double) encoderCPR;

    public static final boolean gyroReversed = true;

    // These are example values only - DO NOT USE THESE FOR YOUR OWN ROBOT!
    // These characterization values MUST be determined either experimentally or
    // theoretically
    // for *your* robot's drive.
    // The Robot Characterization Toolsuite provides a convenient tool for obtaining
    // these
    // values for your robot.
    public static final double ksVolts = 0.89;
    public static final double kvVoltSecondsPerMeter = 2.67;
    public static final double kaVoltSecondsSquaredPerMeter = 0.467;

    // Example value only - as above, this must be tuned for your drive!
    public static final double pDriveVel = 0.00424;
  }

  public static final class AutoConstants {
    public static final double maxSpeedMetersPerSecond = 3;
    public static final double maxAccelerationMetersPerSecondSquared = 3;

    // Reasonable baseline values for a RAMSETE follower in units of meters and
    // seconds
    public static final double ramseteB = 2;
    public static final double ramseteZeta = 0.7;
  }
}
