/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.team6479.lib.util.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;
import frc.robot.util.DistanceCalculator;

public class SpinUpFlywheel extends CommandBase {

  private final double THRESHOLD_RPM = 3;

  private Flywheel flywheel;
  private DistanceCalculator distanceCalculator;

  /**
   * Creates a new SpinUpFlywheel.
   */
  public SpinUpFlywheel(Flywheel flywheel) {
    this.flywheel = flywheel;
    distanceCalculator = new DistanceCalculator(0, 0, 0);
    addRequirements(this.flywheel);
  }

  /**
   * @param distance The distance to the target in inches
   */
  private double calculate(double distance) {
    return 0;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (Limelight.hasTarget()) {
      flywheel.set(calculate(distanceCalculator.calculate(Math.toRadians(Limelight.getYOffset()))));
    } else {
      // TODO: handle
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(flywheel.getError()) < THRESHOLD_RPM;
  }
}
