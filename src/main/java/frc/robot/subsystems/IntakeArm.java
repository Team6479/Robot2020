/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class IntakeArm extends SubsystemBase {
  /**
   * Creates a new IntakeArm.
   */
  private TalonSRX intakeArm;
  /**
   * This will be used in the future to reference
   */
  private boolean up;

  public final double kP = 1;
  public final double kI = 0;
  public final double kD = 0;
  public final double kF = 0;
  public final double kPeakOutput = 1;
  public final int timeoutMs = 0;
  public final int pidID = 0;

  public IntakeArm() {
    intakeArm = new TalonSRX(RobotMap.INTAKE_ARM);

    intakeArm.configFactoryDefault();

    configTalonPID(intakeArm, kP, kI, kD, kF);

    up = false;
  }
  
  public boolean isUp(){
    return up;
  }

  private void configTalonPID(TalonSRX talon, double p, double i, double d, double f){
    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, pidID, timeoutMs);

    talon.configNominalOutputForward(0, timeoutMs);
    talon.configNominalOutputReverse(0, timeoutMs);
    talon.configPeakOutputForward(1, timeoutMs);
    talon.configPeakOutputReverse(-1, timeoutMs);

    talon.config_kP(pidID, p, timeoutMs);
    talon.config_kI(pidID, i, timeoutMs);
    talon.config_kD(pidID, d, timeoutMs);
    talon.config_kF(pidID, f, timeoutMs);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
