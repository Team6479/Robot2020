package frc.robot.util;

public final class Util {
    /**
     * Don't let anyone instantiate this class.
     */
    private Util() {}

    public static double getRange(double x, double y) {
      return x - y;
    }

    public static boolean inRange(double x, double y, double range) {
        return Math.abs(getRange(x, y)) <= range;
    }
}
