/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.AlignmentBelt;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Indexer;

public class ShooterDump extends SequentialCommandGroup {
  private final double DURATION = 5; // number of seconds for the shooter to dump all five balls, based on testing

  /**
   * Creates a new ShooterDump.
   */
  public ShooterDump(Flywheel flywheel, Indexer indexer, AlignmentBelt alignmentBelt) {
    super(new SpinUpFlywheel(flywheel), new InstantCommand(indexer::run, indexer), new InstantCommand(alignmentBelt::run, alignmentBelt));
    withTimeout(DURATION);
  }
}
