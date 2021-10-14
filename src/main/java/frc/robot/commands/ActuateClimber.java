// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ActuateClimber extends CommandBase {

  private Climber climber;

  /** Creates a new ExtendClimber. */
  public ActuateClimber(Climber climber) {
    this.climber = climber;
    addRequirements(climber);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  /**
   * Moves the climber OUT the first time it's called, then moves it IN every subsequent time
   * 
   * <p>Degree values for OUT and IN are hard coded
   */
  @Override
  public void execute() {
    if(!climber.getActuated()) {
      climber.set(720); // degrees, change later. This is the OUT position of the climber
    } else {                                                                                                               
      climber.set(0); // This is the IN position of the climber after being already OUT
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
