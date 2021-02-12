package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FlywheelConstants;

public class Flywheels extends SubsystemBase {

  private CANSparkMax smallFlywheel1;
  private CANSparkMax smallFlywheel2;
  private TalonFX bigFlywheelMotor1;
  private TalonFX bigFlywheelMotor2;
  private boolean isBigOn, isSmallOn;

  public Flywheels() {
    smallFlywheel1 = new CANSparkMax(FlywheelConstants.SMALL_LEFT, MotorType.kBrushless);
    smallFlywheel2 = new CANSparkMax(FlywheelConstants.SMALL_RIGHT, MotorType.kBrushless);
  
    smallFlywheel1.restoreFactoryDefaults();
    smallFlywheel2.restoreFactoryDefaults();
  
    smallFlywheel1.setInverted(false);
    smallFlywheel1.setInverted(true);
  
    bigFlywheelMotor1 = new TalonFX(FlywheelConstants.BIG_LEFT);
    bigFlywheelMotor2 = new TalonFX(FlywheelConstants.BIG_RIGHT);
  
    bigFlywheelMotor1.configFactoryDefault();
    bigFlywheelMotor2.configFactoryDefault();
  
    bigFlywheelMotor1.setInverted(false);
    bigFlywheelMotor2.setInverted(true);
  
    smallFlywheel2.follow(smallFlywheel1);
    bigFlywheelMotor2.follow(bigFlywheelMotor1);

    isBigOn = false;
    isSmallOn = false;
  }

  public void setSmallFlywheelRawSpeed(double speed) {
    smallFlywheel1.set(speed);
  }

  public void setBigFlywheelRawSpeed(double speed) {
    bigFlywheelMotor1.set(ControlMode.PercentOutput, speed);
  }

  public void off() {
    setBigFlywheelRawSpeed(0);
    isBigOn = false;
    setSmallFlywheelRawSpeed(0);
    isSmallOn = false;
  }

  public void setRawSpeed(double big, double small) {
    setBigFlywheelRawSpeed(big);
    isBigOn = (big != 0);
    setSmallFlywheelRawSpeed(small);
    isSmallOn = (small != 0);
  }

  public boolean getIsOn() {
    return isBigOn || isSmallOn;
  }

}
