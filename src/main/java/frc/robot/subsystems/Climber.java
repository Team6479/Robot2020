// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants.ClimberConstants;


public class Climber extends SubsystemBase {
  /** Creates a new Climber. */

  private TalonFX climberMotor;
  private final double ENCODER_UNITS = 4095;
  private final double UNITS_PER_DEGREE = ENCODER_UNITS / 360;
  private boolean climberActuated;

  public Climber() {
    climberActuated = false;
  
    climberMotor = new TalonFX(ClimberConstants.CLIMBER);
    climberMotor.setNeutralMode(NeutralMode.Brake);
    climberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    climberMotor.setSelectedSensorPosition(0, 0, 20); // reset position, assumes climber is in the IN state on robot enable

    climberMotor.setSensorPhase(true);
    climberMotor.configAllowableClosedloopError(0, 3); // copied from turret because it works

    climberMotor.config_kP(0, 0.0);
    climberMotor.config_kI(0, 0.0);
    climberMotor.config_kD(0, 0.0);
    climberMotor.config_kF(0, 0.0);

  }

  public void set(double position) {
    climberMotor.set(ControlMode.Position, position * UNITS_PER_DEGREE);
    this.climberActuated = true;
  }

  public boolean getActuated() {
    return this.climberActuated;
  }

}
