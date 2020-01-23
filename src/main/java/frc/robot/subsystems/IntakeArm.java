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
import frc.robot.Constants;

public class IntakeArm extends SubsystemBase {
  /**
   * Creates a new IntakeArm.
   */
  private TalonSRX intakeArm;
  /**
   * This will be used in the future to reference
   */
  private boolean up; //used to check if the arm is up in the command

  // pid tuning constants
  public final double kP = 1;
  public final double kI = 0;
  public final double kD = 0;
  public final double kF = 0;
  public final double kPeakOutput = 1;
  public final int timeoutMs = 0;
  public final int pidID = 0;

  public IntakeArm() {
    // configure motor controller, encoder, and pids
    intakeArm = new TalonSRX(Constants.INTAKE_ARM);
    
    intakeArm.configFactoryDefault();

    intakeArm.setInverted(false);

    configTalonPID(intakeArm, kP, kI, kD, kF);

    up = false; //set up to false, may change later
  }
  
  public void toggleArm(){
    // Add code here when mechanical is definite, it will be used to change the arm from down to up or vice versa
    // every time it is executed. May change functionality later once mechanical is definite.
  }

  public boolean isUp(){
    return up;
  }

  private void configTalonPID(TalonSRX talon, double p, double i, double d, double f){
    // configure mag encoder
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
