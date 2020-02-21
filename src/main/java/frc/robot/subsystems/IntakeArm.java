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
import frc.robot.Constants.IntakeConstants;

public class IntakeArm extends SubsystemBase {

  private final TalonSRX intakeArm;

  /**
   * This will be used in the future to reference
   */
  private boolean isOut;

  // Based on stall current draw for a BAG motor
  // https://www.vexrobotics.com/217-3351.html#Other_Info
  private final double AMPERAGE_SPIKE_THESHOLD = 40.0;

  public IntakeArm() {
    // configure motor controller, encoder, and pids (tentative)
    intakeArm = new TalonSRX(IntakeConstants.INTAKE_ARM);
    
    intakeArm.configFactoryDefault();

    intakeArm.setInverted(false);

    isOut = false;
  }

  public boolean isOut() {
    return isOut;
  }

  private void armStop() {
    intakeArm.set(ControlMode.PercentOutput, 0.0);
  }

  // As of now, positive value means out, negative means in, may change later
  public void armOut() {
    while (intakeArm.getSupplyCurrent() < this.AMPERAGE_SPIKE_THESHOLD) {
      intakeArm.set(ControlMode.PercentOutput, 0.5);
    }

    this.armStop();
    isOut = true;
  }

  public void armIn() {
    while (intakeArm.getSupplyCurrent() < this.AMPERAGE_SPIKE_THESHOLD) {
      intakeArm.set(ControlMode.PercentOutput, -0.5);
    }

    this.armStop();
    isOut = false;
  }
}
