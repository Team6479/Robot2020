/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeArm extends SubsystemBase {
  /**
   * Creates a new IntakeArm.
   */

  public enum LimitSwitch {
    SWITCHOUT, SWITCHIN
  }

  private TalonSRX intakeArm;

  private DigitalInput limitSwitchOut;
  private DigitalInput limitSwitchIn;
  private Counter counterOut;
  private Counter counterIn;
  /**
   * This will be used in the future to reference
   */
  private boolean out; //used to check if the arm is out in the command

  public IntakeArm() {
    // configure motor controller, encoder, and pids (tentative)
    intakeArm = new TalonSRX(IntakeConstants.INTAKE_ARM);
    
    intakeArm.configFactoryDefault();

    intakeArm.setInverted(false);

    limitSwitchIn = new DigitalInput(IntakeConstants.INTAKE_LIMIT_SWITCH_IN);
    limitSwitchOut = new DigitalInput(IntakeConstants.INTAKE_LIMIT_SWITCH_OUT);
    counterOut = new Counter(limitSwitchOut);
    counterIn = new Counter(limitSwitchIn);

    out = false; //set out to false, may change later
  }

  public void initLimitCounters() {
    counterOut.reset();
    counterIn.reset();
  }

  public boolean isSet(LimitSwitch limitSwitch) {
    switch(limitSwitch) {
    case SWITCHOUT:
      return counterOut.get() > 0;
    case SWITCHIN:
      return counterIn.get() > 0;
    }
    return false;
  }
  
  // As of now, positive value means out, negative means in, may change later
  public void armOut() {
    intakeArm.set(ControlMode.PercentOutput, 0.5);
  }

  public void armIn() {
    intakeArm.set(ControlMode.PercentOutput, -0.5);
  }

  public void armStop() {
    intakeArm.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean isOut() {
    return out;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
