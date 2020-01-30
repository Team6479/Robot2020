/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import java.util.function.DoubleSupplier;

/**
 * Calculates trajectories for a generic flywheel ball shooter
 * See the Desmos graph: https://www.desmos.com/calculator/sqaudpqtsz
 * 
 * @author Leo Wilson
 */
public class ShooterTrajectory implements DoubleSupplier {
    private final double G = -9.807; // MUST be negative, m/s^2

    private DoubleSupplier xf; // relative horizontal distance
    private double theta; // shooter angle (rad)
    private double yrel; // relative height
    private double r; // flywheel radius
    
    /**
     * Provides the necessary flywheel data
     * @param xDist Supplies the distance to the target in inches
     * @param shooterAngle The angle in radians at which the flywheel shoots the ball (e.g. hood angle)
     * @param initialHeight The height from which the ball is fired in inches
     * @param targetHeight The height at which the ball should enter the target in inches
     * @param flywheelRadius The radius of the flywheel in inches
     */
    public ShooterTrajectory(DoubleSupplier xDist, double shooterAngle, double initialHeight, double targetHeight, double flywheelRadius) {
        xf = xDist;
        theta = shooterAngle;
        yrel = targetHeight - initialHeight;
    }

    /**
     * @return The desired initial velocity in inches/sec based on the object's values
     */
    private double calcV0() {
        return xf.getAsDouble() / (Math.cos(theta) * Math.sqrt(
            (yrel - (xf.getAsDouble() * Math.tan(theta))) / (0.5 * G)
        ));
    }

    /**
     * @param v0 The desired initial velocity of the ball at the object's angle
     * @return The desired flywheel speed (RPM), assuming perfect physics
     */
    private double calcPerfectFlywheelSpeed(double v0) {
        // 60 sec/min
        // 2 * pi * r
        // 60 / 2 = 30
        return 30 * v0 / (Math.PI * r);
    }

    /**
     * @return The desired RPM of the flywheel, taking into account all object parameters.
     */
    public double getAsDouble() {
        return calcPerfectFlywheelSpeed(calcV0());
    }
}
