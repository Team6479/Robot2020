/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TurretConstants;
import frc.robot.util.Angle;
import frc.robot.util.Sigmoid;
import frc.robot.util.Util;

/**
 * Subsystem for controlling a Turret
 *
 * @author Thomas Quillan
 */
public class Turret extends SubsystemBase {
  private final double ENCODER_UNITS = 4095; // Range should be 0 - 4095 (aka. 4096 units)
  private final double UNITS_PER_DEGREE = ENCODER_UNITS / 360;
  // private final Sigmoid percentOutSigmoid = new Sigmoid(1.0, 2.85, 1.5, true, 1.9755, -0.5);
  private final Sigmoid percentOutSigmoid = new Sigmoid(1.0, 4.5, 1.0, false, 10, -0.1);

  private final TalonSRX motor = new TalonSRX(TurretConstants.MOTOR);
  private double lowerLimit;
  private double upperLimit;
  private boolean correction = false;
  private double goal = 0;
  private ArrayList<BooleanSupplier> correctionResetConditions = new ArrayList<>();

  public Turret(double lowerLimit, double upperLimit) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;

    // Restore each talonSRX to factory defaults prior to configuration
    motor.configFactoryDefault();

    motor.setNeutralMode(NeutralMode.Brake);

    // Add Mag Encoders
    motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

    // Get the absolute pulse width position
    int pulseWidth = motor.getSensorCollection().getPulseWidthPosition();

    /**
     * Mask out the bottom 12 bits to normalize to [0,4095], or in other words, to stay within
     * [0,360) degrees
     */
    pulseWidth = pulseWidth & 0xFFF;

    if (pulseWidth / UNITS_PER_DEGREE > upperLimit) {
      pulseWidth += -360 * UNITS_PER_DEGREE;
    }

    motor.getSensorCollection().setQuadraturePosition(pulseWidth, 0);

    motor.setInverted(true);
    motor.setSensorPhase(true);

    motor.configAllowableClosedloopError(0, 3);

    motor.config_kP(0, 10.5);
    motor.config_kI(0, .0004);
    motor.config_kD(0, 50);

    // motor.config_kP(0, 10.5);
    // motor.config_kI(0, .0001);
    // motor.config_kD(0, 750);

    correctionResetConditions.add(() -> Util.inRange(goal, getCurrentAngle(), 2));

    ShuffleboardTab debug = Shuffleboard.getTab("Debug");
    debug.addNumber("Turret Encoder (Units)", motor::getSelectedSensorPosition);
    debug.addNumber("Turret Encoder (Angle)", this::getCurrentAngle);
    debug.addNumber("Turret Error", motor::getClosedLoopError);
    debug.addNumber("Turret Goal", () -> goal);
  }

  /**
   * Set the position with angle correction applied. See: {@link Turret#correctAngle}.
   *
   * @param angle Angle for Turret to turn to.
   */
  public void setPosition(double angle) {

    double angleNew = correctAngle(angle);
    if (angleNew != angle) {
      correction = true;
    }

    angle = angleNew;
    goal = angle;

    motor.set(ControlMode.Position, angle * UNITS_PER_DEGREE);
  }

  public void setPercentOutput(double speed) {
    if (speed > 0) {
      speed *= percentOutSigmoid.calculate(Util.getRange(upperLimit, getCurrentAngle()));
    } else if (speed < 0) {
      speed *= percentOutSigmoid.calculate(Math.abs(Util.getRange(getCurrentAngle(), lowerLimit)));
    }

    motor.set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    motor.set(ControlMode.PercentOutput, 0);
  }

  public void clearCorrection() {
    correction = false;
  }

  public void addClearCorrectionHook(BooleanSupplier condition) {
    correctionResetConditions.add(condition);
  }

  public boolean isCorrected() {
    return !correction;
  }

  /**
   * Gets the centered position of the turret by calculating the difference between the two limits
   */
  public int getCenter() {
    return Math.round((float)((upperLimit + lowerLimit)/2));
  }

  /**
   * Method which attempts to correct the angle of the turret. This is done by attempting to use the
   * positive/negative inverse angle if possible.
   *
   * @param angle The angle to correct.
   * @return The angle after corrections.
   */
  public double correctAngle(double angle) {
    // Values should remain between +/- 360
    angle %= 360;

    if (angle > upperLimit || angle < lowerLimit) {
      // double inverse = angle >= 0 ? -360 + angle : 360 + angle;

      return Angle.getShortestDistance(angle, upperLimit) < Angle.getShortestDistance(angle,
          upperLimit) ? upperLimit : lowerLimit;

      // if (inverse >= lowerLimit && inverse <= upperLimit) {
      // return inverse;
      // } else {
      // // If we cant use the inverse we return the closest limit
      // return Angle.getShortestDistance(inverse, upperLimit) < Angle.getShortestDistance(inverse,
      // upperLimit) ? upperLimit : lowerLimit;
      // }
    } else {
      return angle;
    }
  }

  public double getUpperLimit() {
    return upperLimit;
  }

  public double getLowerLimit() {
    return lowerLimit;
  }

  public double getCurrentAngle() {
    return motor.getSelectedSensorPosition() / UNITS_PER_DEGREE;
  }

  public double getAngleGoal() {
    return goal;
  }

  public int getPIDError() {
    // TODO: why (int)
    return (int)motor.getClosedLoopError();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (correction) {
      for (BooleanSupplier condition : correctionResetConditions) {
        if (condition.getAsBoolean()) {
          clearCorrection();
          break;
        }
      }
    }
  }
}
