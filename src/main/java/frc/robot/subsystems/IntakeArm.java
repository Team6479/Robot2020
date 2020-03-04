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
  private final TalonSRX intakeArm = new TalonSRX(IntakeConstants.INTAKE_ARM);

  /**
   * This will be used in the future to reference
   */
  private boolean isOut;

  // Based on stall current draw for a BAG motor
  // https://www.vexrobotics.com/217-3351.html#Other_Info
  private final double AMPERAGE_SPIKE_THESHOLD = 40.0;

  public IntakeArm() {
    intakeArm.configFactoryDefault();

    intakeArm.setInverted(false);
    
    isOut = false;
  }

  public boolean isOut() {
    return isOut;
  }

  public void setIsOut(boolean state) {
    this.isOut = state;
  }

  public void armStop() {
    intakeArm.set(ControlMode.PercentOutput, 0.0);
  }

  public void set(double speed) {
    intakeArm.set(ControlMode.PercentOutput, speed);
  }
}
