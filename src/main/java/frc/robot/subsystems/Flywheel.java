/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FlywheelConstants;

public class Flywheel extends SubsystemBase {
  private final int CPR = 4096;
  private final double VELOCITY_INTERVAL_PER_MIN = 0.1 * 60;

  private final TalonSRX topMotor = new TalonSRX(FlywheelConstants.FLYWHEEL_TOP);
  private final TalonSRX bottomMotor = new TalonSRX(FlywheelConstants.FLYWHEEL_BOTTOM);

  private boolean isOn;

  public Flywheel() {
    // Reset to defaults
    topMotor.configFactoryDefault();
    bottomMotor.configFactoryDefault();

    // Configure encoders
    topMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

    // Set motor directions
    topMotor.setInverted(false);
    bottomMotor.setInverted(true);

    // Set slave
    bottomMotor.follow(topMotor);

    //PID
    // topMotor.config_kP(0, 0);
    // topMotor.config_kI(0, 0);
    // topMotor.config_kP(0, 0);

    isOn = false;
  }

  public double getError() {
    return topMotor.getClosedLoopError() / CPR;
  }

  /**
   * Sets the desired speed of the flywheel
   */
  public void set(double speed) {
    isOn = (speed != 0);
    topMotor.set(ControlMode.Velocity, speed * CPR / VELOCITY_INTERVAL_PER_MIN);
  }

  public void off() {
    set(0);
  }

  public boolean getIsOn() {
    return isOn;
  }
}
