/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int INTAKE_ROLLER_1 = 0; // Change later
    public static final int INTAKE_ROLLER_2 = 0; // Change later
    public static final int INTAKE_ARM = 0; // Change later

  public static final class DrivetrainConstants {
    public static final int motorLeftFront = 0;
    public static final int motorLeftBack = 1;
    public static final int motorRightFront = 2;
    public static final int motorRightBack = 3;
  }

  public static final class FlywheelConstants {
    public static final int FLYWHEEL_RIGHT = 7;
    public static final int FLYWHEEL_LEFT = 8;
    public static final int P = 0;
    public static final int I = 0;
    public static final int D = 0;
  public static final class IndexerConstants{
    public static final int MOTOR = 4;
  }

  public static final class AlignmentBeltConstants{
    public static final int MOTOR = 5;
  }
}
