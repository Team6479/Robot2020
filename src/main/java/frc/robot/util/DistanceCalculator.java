package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistanceCalculator {
  private double h1;
  private double h2;
  private double a1;

  /**
   * @param h1 The height of the Limelight
   * @param h2 The height of the target
   * @param a1 The angle of the Limelight in radians
   */
  public DistanceCalculator(double h1, double h2, double a1) {
    this.h1 = h1;
    this.h2 = h2;
    this.a1 = a1;
  }

  /**
   * @param angle The vertical angle detected by the Limelight in radians
   * @return The estimated distance to the target
   */
  public double calculate(double angle) {
    SmartDashboard.putNumber("test value", Math.tan(a1 + angle));
    return (h2 - h1) / Math.tan(a1 + angle);
  }
}
