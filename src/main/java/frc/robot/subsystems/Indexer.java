/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexerConstants;

public class Indexer extends SubsystemBase {

  private TalonSRX indexerMotor;

  /**
   * Creates a new Indexer.
   */
  public Indexer() {
    indexerMotor = new TalonSRX(IndexerConstants.MOTOR);

    indexerMotor.configFactoryDefault();

    //set neutral mode
    indexerMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void run(){
    indexerMotor.set(ControlMode.PercentOutput, 1.0);
  }

  public void toggle(){
    if(indexerMotor.getMotorOutputPercent() > 0){
      stop();
    }
    else{
      run();
    }
  }

  public void stop(){
    indexerMotor.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}