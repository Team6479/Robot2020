/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;
import frc.robot.util.Sigmoid;

public class TurnDrivetrain extends CommandBase {
  public enum Direction {
    Left, Right
  }

  private final Drivetrain drivetrain;
  private final NavX navX;

  private final double GOAL;
  private final Direction DIRECTION;

  private final Sigmoid sigmoid = new Sigmoid(1, 0.2, 1, false, 0.5, 0);

  // private double prevAngle = 0;
  private double angle = 0;

  /**
   * Creates a new TurnDrivetrain.
   */
  public TurnDrivetrain(Drivetrain drivetrain, NavX navX, double angle, Direction direction) {
    this.drivetrain = drivetrain;
    this.navX = navX;
    this.GOAL = angle;
    this.DIRECTION = direction;

    addRequirements(drivetrain, navX);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    navX.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    angle = Math.abs(navX.getAngle());
    // double angleDiff = Math.abs(angle - prevAngle); // TODO: proper PIDs

    /**
     * Equation that decreases speed as the the robot approached the angle goal with precision
     *
     * 0.1 = min speed 0.75 = speed. (Increase for speed increase/ decrease for speed decrease) The
     * parentheses stuff is an equation that goes from 1 to 0 as the angle approaches the goal
     */
    double x = (GOAL - angle) / GOAL;
    double sigmoidValue = sigmoid.calculate(x);
    double speed = 0.1 + (0.75 * (sigmoidValue));

    if (DIRECTION == Direction.Left) {
      drivetrain.tankDrive(-speed, speed);
    } else {
      drivetrain.tankDrive(speed, -speed);
    }

    // prevAngle = angle;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
    DriverStation.reportError("Done", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return Math.abs(angle - GOAL) <= 2;
    return angle >= GOAL;
  }
}
