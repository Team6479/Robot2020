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
import frc.robot.commands.StraightDrive;
import frc.robot.commands.TrenchSpinUpFlywheels;
import frc.robot.commands.TurnDrivetrain;
import frc.robot.commands.TurnDrivetrain.Direction;
import frc.robot.subsystems.AlignmentBelt;
import frc.robot.subsystems.Flywheels;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.IntakeArm.Position;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;
import frc.robot.commands.SetIntakeArmPosition;

public class DriveAimShootAuton extends SequentialCommandGroup {
  /**
   * Creates a new AimShootAuton.
   */
  public DriveAimShootAuton(Drivetrain drivetrain, NavX navX, Turret turret, Flywheels flywheel, Indexer indexer, AlignmentBelt alignmentBelt, IntakeArm intakeArm) {
    super(
      new StraightDrive(drivetrain, navX, -0.5, 40),
      new WaitCommand(0.5),
      new InstantCommand(() -> {
        Limelight.setLEDState(LEDState.Auto);
        Limelight.setCamMode(CamMode.VisionProcessor);
      }),
      new WaitUntilCommand(Limelight::hasTarget), // Only Aim and continue with shooting if we have a valid target
      new SetIntakeArmPosition(intakeArm, Position.Out),
      new AimTurret(turret),
      new TrenchSpinUpFlywheels(flywheel),
      new WaitCommand(2), // TODO: Look into why this wait is needed
      new ParallelCommandGroup(
        new InstantCommand(indexer::run, indexer),
        new InstantCommand(alignmentBelt::run, alignmentBelt)
      )
    );
  }
}
