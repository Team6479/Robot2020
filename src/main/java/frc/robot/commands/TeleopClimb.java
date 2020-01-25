/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.Direction;

public class TeleopClimb extends CommandBase {
  private final Climber climber;
  private final Trigger upTrigger;
  private final Trigger downTrigger;
  private final Trigger canClimb;

  private boolean climbState = false;

  /**
   * Creates a new TeleopClimb.
   */
  public TeleopClimb(Climber climber, Trigger upTrigger, Trigger downTrigger, Trigger canClimb) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climber = climber;
    this.upTrigger = upTrigger;
    this.downTrigger = downTrigger;
    this.canClimb = canClimb;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(canClimb.get() && !climbState){
      climbState = true;
    }

    if(climbState){
      if(upTrigger.get()){
        climber.setDirection(Direction.UP);
      }
      else if(downTrigger.get()){
        climber.setDirection(Direction.DOWN);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
