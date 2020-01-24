/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeRollers extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  private TalonSRX intakeRoller1;
  private TalonSRX intakeRoller2;

  private boolean on; // Used for the toggle method for the rollers
  public IntakeRollers() {
    intakeRoller1 = new TalonSRX(Constants.INTAKE_ROLLER_1);
    intakeRoller2 = new TalonSRX(Constants.INTAKE_ROLLER_2);

    intakeRoller1.configFactoryDefault();
    intakeRoller2.configFactoryDefault();

    intakeRoller1.setInverted(false);
    intakeRoller2.setInverted(true);

    intakeRoller2.follow(intakeRoller1);

    on = false;

  }

  // Set the roller motors to on
  public void rollersOn() {
    intakeRoller1.set(ControlMode.PercentOutput, 1.0);
    intakeRoller2.set(ControlMode.PercentOutput, 1.0);
    on = true;
  }
  public boolean isOn() {
    return on;
  }
  // Set the roller motor to off
  public void rollersOff() {
    intakeRoller1.set(ControlMode.PercentOutput, 0.0);
    intakeRoller2.set(ControlMode.PercentOutput, 0.0);
    on = false;
  }
  // Toggles the rollers off or on when called
  public void toggleRollers() {
    if(on) {
      rollersOff();
    } else {
      rollersOn();
    }
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
