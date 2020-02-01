package frc.robot.util;

public class DistanceCalculator {
  private double h1;
  private double h2;
  private double a1;

  public DistanceCalculator(double h1, double h2, double a1) {
    this.h1 = h1;
    this.h2 = h2;
    this.a1 = a1;
  }

  public double calculate(double angle) {
    return (h1 - h2) / Math.tan(Math.toRadians(a1 + angle));
  }
}
