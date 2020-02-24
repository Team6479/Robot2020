/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeRollers extends SubsystemBase {
  private final VictorSPX intakeRoller = new VictorSPX(IntakeConstants.INTAKE_ROLLER);

  public IntakeRollers() {
    intakeRoller.configFactoryDefault();

    intakeRoller.setInverted(false);
  }

  // Set the roller motors to on
  public void rollersOn() {
    intakeRoller.set(ControlMode.PercentOutput, .9);
  }

  // Set the roller motor to off
  public void rollersOff() {
    intakeRoller.set(ControlMode.PercentOutput, 0.0);
  }

  public double getSpeed() {
    return intakeRoller.getMotorOutputPercent();
  }
}
