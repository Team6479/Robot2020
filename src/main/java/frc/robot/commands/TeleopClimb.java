/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.State;

public class TeleopClimb extends CommandBase {
  private final double CURRENT_LIMIT = 3;

  private final Climber climber;
  private final Trigger upTrigger;
  private final Trigger downTrigger;
  private final Trigger canClimbTrigger;

  private boolean climbState = false;

  /**
   * Creates a new TeleopClimb.
   */
  public TeleopClimb(Climber climber, Trigger upTrigger, Trigger downTrigger, Trigger canClimbTrigger) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.climber = climber;
    this.upTrigger = upTrigger;
    this.downTrigger = downTrigger;
    this.canClimbTrigger = canClimbTrigger;
    addRequirements(climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(canClimbTrigger.get() && !climbState){
      climbState = true;
    }
    else if(canClimbTrigger.get() && climbState){
      climber.toggleLock();
      return;
    }

    if(climbState && !climber.isLocked()){
      if(upTrigger.get()){
        climber.setState(State.UP);
      }
      else if(downTrigger.get()){
        climber.setState(State.DOWN);
      }
    }
    else if(climber.isLocked()){
      climber.setState(State.LOCK);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return climber.getCurrentDraw() >= CURRENT_LIMIT;
  }
}
