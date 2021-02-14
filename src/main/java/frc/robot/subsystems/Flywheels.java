package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FlywheelConstants;

public class Flywheels extends SubsystemBase {

  private CANSparkMax smallFlywheel1;
  private CANSparkMax smallFlywheel2;
  private CANPIDController smallPIDController;
  private TalonFX bigFlywheelMotor1;
  private TalonFX bigFlywheelMotor2;
  private boolean isBigOn, isSmallOn;

  // all this is unnecessary for small flywheel since Sparks use RPM natively
  private static final double BIG_CPR = 2048; // encoder cycles per rotation
  private static final double BIG_PERIOD_PER_MIN = 60 * 10; // 60s/min * 10ds/s; TalonFX uses encoder units / 100ms
  private static final double BIG_RPM_PER_NATIVE = BIG_PERIOD_PER_MIN / BIG_CPR; // conversion rate from RPM to TalonFX native units
  private static final double BIG_NATIVE_PER_RPM = BIG_CPR / BIG_PERIOD_PER_MIN; // inverse of BIG_RPM_PER_NATIVE, multiplication is faster than division

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

    bigFlywheelMotor1.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30); // source: ctre examples

    // PIDF tuning constants
    bigFlywheelMotor1.config_kF(0, 0);
		bigFlywheelMotor1.config_kP(0, 1);
		bigFlywheelMotor1.config_kI(0, 0);
		bigFlywheelMotor1.config_kD(0, 0);

    // REV uses a seperate PID controller object
    smallPIDController = smallFlywheel1.getPIDController();

    smallPIDController.setP(1);
    smallPIDController.setI(0);
    smallPIDController.setD(0);
    smallPIDController.setIZone(1);
    smallPIDController.setFF(0);

    isBigOn = false;
    isSmallOn = false;
  }

  public static double bigNativeToRPM(double nativeUnits) {
    return nativeUnits * BIG_RPM_PER_NATIVE;
  }
  public static double bigRPMToNative(double rpm) {
    return rpm * BIG_NATIVE_PER_RPM;
  }

  public void setSmallFlywheelRawSpeed(double speed) {
    smallFlywheel1.set(speed);
    isSmallOn = (speed != 0);
  }

  public void setBigFlywheelRawSpeed(double speed) {
    bigFlywheelMotor1.set(ControlMode.PercentOutput, speed);
    isBigOn = (speed != 0);
  }

  /**
   * Sets the PIDF setpoint of the small flywheel
   * @param speed The rotational velocity in RPM
   */
  public void setSmallFlywheelSpeed(double speed) {
    // REV uses RPM natively
    smallPIDController.setReference(speed, ControlType.kVelocity);
    isSmallOn = (speed != 0);
  }

  /**
   * Sets the PIDF setpoint of the large flywheel
   * @param speed The rotational velocity in RPM
   */
  public void setBigFlywheelSpeed(double speed) {
    // CTRE uses cycles/decisecond natively, hence the conversion
    bigFlywheelMotor1.set(ControlMode.Velocity, bigRPMToNative(speed));
    isBigOn = (speed != 0);
  }

  public void off() {
    setBigFlywheelRawSpeed(0);
    isBigOn = false;
    setSmallFlywheelRawSpeed(0);
    isSmallOn = false;
  }

  public void setRawSpeed(double big, double small) {
    setBigFlywheelRawSpeed(big);
    setSmallFlywheelRawSpeed(small);
  }

  /**
   * Sets the PIDF setpoint of both flywheels
   * @param big The rotational velocity of the large flywheel in RPM
   * @param small The rotational velocity of the small flywheel in RPM
   */
  public void setSpeed(double big, double small) {
    setBigFlywheelSpeed(big);
    setSmallFlywheelSpeed(small);
  }

  public boolean getIsOn() {
    return isBigOn || isSmallOn;
  }

}
