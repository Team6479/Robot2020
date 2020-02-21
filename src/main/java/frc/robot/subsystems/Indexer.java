/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IndexerConstants;

public class Indexer extends SubsystemBase {

  private final VictorSPX indexerMotor;

  public Indexer() {
    indexerMotor = new VictorSPX(IndexerConstants.MOTOR);

    indexerMotor.configFactoryDefault();

    indexerMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void run(){
    indexerMotor.set(ControlMode.PercentOutput, 1.0);
  }

  public void stop(){
    indexerMotor.set(ControlMode.PercentOutput, 0.0);
  }
}
