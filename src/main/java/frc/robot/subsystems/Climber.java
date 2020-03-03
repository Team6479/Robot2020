/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {

  public enum State {
    UP(0.75), DOWN(-1), LOCK(0.1), STOP(0);

    public double speed;
    private State(double speed) {
      this.speed = speed;
    }
  }

  private final TalonSRX motor = new TalonSRX(ClimberConstants.MOTOR);

  //bye sigmoid
  private final DoubleSolenoid piston = new DoubleSolenoid(ClimberConstants.LOCK_PISTON_0, ClimberConstants.LOCK_PISTON_1);


  /**
   * Creates a new Climber.
   */

  public Climber() {
    // reset to factory default
    motor.configFactoryDefault();

    //Set Neutral Mode
    motor.setNeutralMode(NeutralMode.Brake);

    //Configure the encoder(absolute)
    motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);

    // Set Inversion and Sensor phase
    motor.setInverted(false);
    motor.setSensorPhase(false);

    //Set piston to the unlocked position
    piston.set(DoubleSolenoid.Value.kReverse);
  }

  /**
   * @param direction (@link Direction) direction to go in
   */
  public void setState(State state){
    motor.set(ControlMode.PercentOutput, state.speed);
  }

  public void stop() {
    setState(State.STOP);
  }


  public void lock(){
    piston.set(DoubleSolenoid.Value.kForward);
  }

  public void unlock(){
    piston.set(DoubleSolenoid.Value.kReverse);
  }

  public boolean isLocked(){
    return piston.get() == DoubleSolenoid.Value.kForward;
  }

  public void toggleLock(){
    if(piston.get() == DoubleSolenoid.Value.kForward){
      piston.set(DoubleSolenoid.Value.kReverse);
    }
    else{
      piston.set(DoubleSolenoid.Value.kForward);
    }
  }

  public double getCurrentDraw() {
    return motor.getStatorCurrent();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


}
