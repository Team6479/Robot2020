/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AlignmentBeltConstants;

public class AlignmentBelt extends SubsystemBase {

  private TalonSRX alignmentBeltMotor;
  private boolean running;

  /**
   * Creates a new AllignmentBelt.
   */
  public AlignmentBelt() {
    alignmentBeltMotor = new TalonSRX(AlignmentBeltConstants.ALIGNMENT_BELT);

    alignmentBeltMotor.configFactoryDefault();

    //set neutral mode
    alignmentBeltMotor.setNeutralMode(NeutralMode.Brake);

    running = false;

  }

  public void run(){
    alignmentBeltMotor.set(ControlMode.PercentOutput, 1.0);
    running = true;
  }

  public void stop(){
    alignmentBeltMotor.set(ControlMode.PercentOutput, 0.0);
    running = false;
  }

  public void toggle(){
    if(running){
      alignmentBeltMotor.set(ControlMode.PercentOutput, 0.0);
      running = false;
    }
    else{
      alignmentBeltMotor.set(ControlMode.PercentOutput, 1.0);
      running = true;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
