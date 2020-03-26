/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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
    topMotor.setInverted(true);
    bottomMotor.setInverted(true);

    topMotor.setSensorPhase(false);

    topMotor.setNeutralMode(NeutralMode.Brake);
    bottomMotor.setNeutralMode(NeutralMode.Brake);

    // Set slave
    bottomMotor.follow(topMotor);

    // topMotor.configVoltageCompSaturation(12);
    // bottomMotor.configVoltageCompSaturation(12);

    // topMotor.enableVoltageCompensation(true);
    // bottomMotor.enableVoltageCompensation(true);

    // PID
    // topMotor.config_kP(0, .4);
    // topMotor.config_kI(0, .00000275);
    // topMotor.config_kD(0, 4);
    // topMotor.config_kF(0, .0097);

    // topMotor.config_kP(0, .00161);
    topMotor.config_kP(0, .25);
    // topMotor.config_kI(0, .015);
    // topMotor.config_kD(0, 0);
    topMotor.config_kF(0, 0.013);
    // topMotor.config_kF(0, 0.011228);


    Shuffleboard.getTab("Main").addNumber("Flywheel Velocity", topMotor::getSelectedSensorVelocity);
    Shuffleboard.getTab("Debug").addNumber("Flywheel Percent", topMotor::getMotorOutputPercent);
    Shuffleboard.getTab("Debug").addNumber("Flywheel Error", topMotor::getClosedLoopError);
    Shuffleboard.getTab("Main").addBoolean("Turret State", () -> isOn);

    isOn = false;
  }

  public double getError() {
    return topMotor.getClosedLoopError();
  }

  /**
   * Sets the desired speed of the flywheel
   */
  public void set(double speed) {
    isOn = (speed != 0);
    topMotor.set(ControlMode.Velocity, speed);
  }

  public void off() {
    topMotor.set(ControlMode.Velocity, 13333);
    isOn = false;
  }

  public boolean getIsOn() {
    return isOn;
  }

  public boolean isAtSpeed() {
    return topMotor.getClosedLoopError() <= 200;
  }
}
