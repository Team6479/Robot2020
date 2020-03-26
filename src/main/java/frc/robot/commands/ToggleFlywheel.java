/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Flywheel;

// NOTE: Consider using this command inline, rather than writing a subclass. For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ToggleFlywheel extends InstantCommand {
  private final double GENERIC_RPM = 41000;

  private final Flywheel flywheel;

  public ToggleFlywheel(Flywheel flywheel) {
    this.flywheel = flywheel;
    addRequirements(this.flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    // if the flywheel is on, turn if off; if it's off, turn it on
    if (flywheel.getIsOn()) {
      // TODO: investigate possible issue with toggling off while shooting
      flywheel.off();
    } else {
      flywheel.set(GENERIC_RPM);
    }
  }
}
