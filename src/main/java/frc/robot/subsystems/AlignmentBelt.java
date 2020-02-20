/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AlignmentBeltConstants;

public class AlignmentBelt extends SubsystemBase {

  private VictorSPX alignmentBeltMotor;

  /**
   * Creates a new AllignmentBelt.
   */
  public AlignmentBelt() {
    alignmentBeltMotor = new VictorSPX(AlignmentBeltConstants.MOTOR);

    alignmentBeltMotor.configFactoryDefault();

    //set neutral mode
    alignmentBeltMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void run(){
    alignmentBeltMotor.set(ControlMode.PercentOutput, 1.0);
  }

  public void stop(){
    alignmentBeltMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void toggle(){
    if(alignmentBeltMotor.getMotorOutputPercent() > 0){
      stop();
    }
    else{
      run();
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
