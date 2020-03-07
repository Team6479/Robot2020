/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import com.team6479.lib.util.Limelight;
import com.team6479.lib.util.Limelight.CamMode;
import com.team6479.lib.util.Limelight.LEDState;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.AimTurret;
import frc.robot.commands.ToggleFlywheel;
import frc.robot.subsystems.AlignmentBelt;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Turret;

public class AimShootAuton extends SequentialCommandGroup {
  /**
   * Creates a new AimShootAuton.
   */
  public AimShootAuton(Turret turret, Flywheel flywheel, Indexer indexer, AlignmentBelt alignmentBelt) {
    super(
      new InstantCommand(() -> {
        Limelight.setLEDState(LEDState.Auto);
        Limelight.setCamMode(CamMode.VisionProcessor);
      }),
      new WaitUntilCommand(Limelight::hasTarget), // Only Aim and continue with shooting if we have a valid target
      new AimTurret(turret),
      new ToggleFlywheel(flywheel),
      new WaitCommand(0.5), // TODO: Look into why this wait is needed
      new WaitUntilCommand(flywheel::isAtSpeed),
      new ParallelCommandGroup(
        new InstantCommand(indexer::run, indexer),
        new InstantCommand(alignmentBelt::run, alignmentBelt)
      )
    );
  }
}
