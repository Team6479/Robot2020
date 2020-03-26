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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeArm extends SubsystemBase {
  public enum Position {
    In(2300), Out(1200);

    public double value;
    private Position(double value) {
      this.value = value;
    }
  }

  private final TalonSRX intakeArm = new TalonSRX(IntakeConstants.INTAKE_ARM);
  private final DigitalInput limitSwitchFront = new DigitalInput(IntakeConstants.INTAKE_ARM_LIMIT_SWITCH_FRONT);
  private final DigitalInput limitSwitchBack = new DigitalInput(IntakeConstants.INTAKE_ARM_LIMIT_SWITCH_BACK);

  /**
   * This will be used in the future to reference
   */
  private Position position = Position.In;

  public IntakeArm() {
    intakeArm.configFactoryDefault();

    intakeArm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);

    intakeArm.setInverted(false);

    Shuffleboard.getTab("Debug").addNumber("Intake Position", intakeArm::getSelectedSensorPosition);
    Shuffleboard.getTab("Debug").addNumber("Intake Current", this::getCurrent);
    Shuffleboard.getTab("Debug").addBoolean("Front Limit Switch", () -> !limitSwitchFront.get());
  }

  public boolean isOut() {
    return position == Position.Out;
  }

  public void armStop() {
    intakeArm.set(ControlMode.PercentOutput, 0.0);
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public void set(double speed) {
    intakeArm.set(ControlMode.PercentOutput, speed);
  }

  public double getPosition() {
    return intakeArm.getSelectedSensorPosition();
  }

  public double getCurrent() {
    return intakeArm.getStatorCurrent();
  }

  public boolean isAtFrontLimit() {
    return !limitSwitchFront.get();
  }

  public boolean isAtBackLimit() {
    return !limitSwitchBack.get();
  }
}
