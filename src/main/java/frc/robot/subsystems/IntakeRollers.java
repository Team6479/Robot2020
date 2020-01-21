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
import frc.robot.RobotMap;

public class IntakeRollers extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  private TalonSRX intakeRoller;

  private static boolean on; // Used for the toggle method for the rollers
  public IntakeRollers() {
    intakeRoller = new TalonSRX(RobotMap.INTAKE_ROLLER);

    intakeRoller.configFactoryDefault();

    on = false;

  }

  // Set the roller motor to on
  private void rollersOn(){
    intakeRoller.set(ControlMode.PercentOutput, 1.0);
    on = true;
  }
  // Set the roller motor to off
  private void rollersOff(){
    intakeRoller.set(ControlMode.PercentOutput, 0.0);
    on = false;
  }
  // Toggles the rollers off or on when called
  public void toggleRollers(){
    if(on)
      rollersOff();
    else
      rollersOn();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
