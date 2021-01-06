package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Simple wrapper for the {@link AHRS} class to make it work as a subsystem
 */
public class NavX extends AHRS implements Subsystem {
  public NavX() {
  }
}
