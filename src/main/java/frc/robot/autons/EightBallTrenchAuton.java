/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import com.team6479.lib.util.Limelight;
import com.team6479.lib.util.Limelight.CamMode;
import com.team6479.lib.util.Limelight.LEDState;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.AimTurret;
import frc.robot.commands.SetIntakeArmPosition;
import frc.robot.commands.StraightDrive;
import frc.robot.commands.TeleopIntakeArm;
import frc.robot.commands.TrenchSpinUpFlywheels;
import frc.robot.commands.TurnDrivetrain;
import frc.robot.commands.TurnDrivetrain.Direction;
import frc.robot.subsystems.AlignmentBelt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheels;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeArm.Position;
import frc.robot.subsystems.IntakeRollers;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Turret;

/**
 * This routine will pick up the first two balls from trench and shoot 5 into the outer port
 */
public class EightBallTrenchAuton extends SequentialCommandGroup {
  /**
   * Creates a new TrenchPickupAuton.
   */
  public EightBallTrenchAuton(Drivetrain drivetrain, NavX navX, IntakeArm intakeArm, IntakeRollers intakeRollers, Turret turret,
    Flywheels flywheels, Indexer indexer, AlignmentBelt alignmentBelt) {
    super(
      new StraightDrive(drivetrain, navX, -0.7, 40), // drive towards the trench (backwards)
      new WaitCommand(0.55), // wait for error correction
      new InstantCommand(drivetrain::resetEncoders, drivetrain),
      new InstantCommand(() -> turret.setPosition(-190), turret), // reset turret position
      new InstantCommand(() -> {
        Limelight.setLEDState(LEDState.Auto);
      }),
      new InstantCommand(() -> Limelight.setCamMode(CamMode.VisionProcessor)),
      new InstantCommand(() -> {
        DriverStation.reportWarning("Waiting For Target...", false);
      }),
      new WaitUntilCommand(Limelight::hasTarget), // Only Aim and continue with shooting if we have a valid target
      new InstantCommand(() -> {
        DriverStation.reportWarning("Target Found. Continuing.", false);
      }),
      new SequentialCommandGroup(
        new WaitCommand(0.25),
        new AimTurret(turret),
        new TrenchSpinUpFlywheels(flywheels),
        // might need a WaitCommand here --> if there isn't enough time for the RPM to reach
        // what it should be, then put a WaitCommand and investigate the isFinished() method
        // of SpinUpFlywheel (which currently returns true!!)
        new WaitCommand(1.5),
        new SequentialCommandGroup(
          new InstantCommand(intakeRollers::rollersOn, intakeRollers),
          new SetIntakeArmPosition(intakeArm, Position.Out, 0.7),
          new InstantCommand(alignmentBelt::run, alignmentBelt),
          new InstantCommand(indexer::run, indexer)
         ),
         new WaitCommand(2.0),
         new SequentialCommandGroup(
          new InstantCommand(flywheels::off, flywheels),
          new InstantCommand(intakeRollers::rollersOff, intakeRollers),
          new SetIntakeArmPosition(intakeArm, Position.In, 0.7),
          new InstantCommand(alignmentBelt::stop, alignmentBelt),
          new InstantCommand(indexer::stop, indexer)
         )
      ),
      new InstantCommand(() -> Limelight.setCamMode(CamMode.DriverCamera)),
      new InstantCommand(() -> Limelight.setLEDState(LEDState.Off)),
      new TurnDrivetrain(drivetrain, navX, 169, Direction.Left),
      new WaitCommand(0.5),
      new SetIntakeArmPosition(intakeArm, Position.Out, 0.7),
      new InstantCommand(intakeRollers::rollersOn, intakeRollers),
      new InstantCommand(drivetrain::resetEncoders, drivetrain),
      new StraightDrive(drivetrain, navX, 0.7, 120),
      new WaitCommand(0.25),
      new InstantCommand(intakeRollers::rollersOff, intakeRollers),
      new InstantCommand(drivetrain::resetEncoders, drivetrain),
      new StraightDrive(drivetrain, navX, -0.7, 120),
      new WaitCommand(0.25),
      new InstantCommand(drivetrain::resetEncoders, drivetrain),
      new TurnDrivetrain(drivetrain, navX, 160, Direction.Right),
      new InstantCommand(() -> turret.setPosition(-190), turret), // reset turret position
      new InstantCommand(() -> {
        Limelight.setLEDState(LEDState.Auto);
      }),
      new InstantCommand(() -> Limelight.setCamMode(CamMode.VisionProcessor)),
      new InstantCommand(() -> {
        DriverStation.reportWarning("Waiting For Target...", false);
      }),
      new WaitUntilCommand(Limelight::hasTarget), // Only Aim and continue with shooting if we have a valid target
      new InstantCommand(() -> {
        DriverStation.reportWarning("Target Found. Continuing.", false);
      }),
      new ParallelCommandGroup( // why is this needed
        new SequentialCommandGroup(
          new WaitCommand(0.25),
          new AimTurret(turret),
          new TrenchSpinUpFlywheels(flywheels),
          // might need a WaitCommand here --> if there isn't enough time for the RPM to reach
          // what it should be, then put a WaitCommand and investigate the isFinished() method
          // of SpinUpFlywheel (which currently returns true!!)
          new WaitCommand(2.0),
          new ParallelCommandGroup(
            new SequentialCommandGroup(
              // ignore this, it's fine
              new InstantCommand(alignmentBelt::run, alignmentBelt),
              new InstantCommand(indexer::run, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::stop, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::run, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::stop, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::run, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::stop, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::run, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::stop, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::run, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::stop, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::run, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::stop, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::run, indexer),
              new WaitCommand(0.75),
              new InstantCommand(indexer::stop, indexer)
            ),
            new SequentialCommandGroup(
              new WaitCommand(1.0), // wait for some balls to fire before enabling rollers
              new InstantCommand(intakeRollers::rollersOn, intakeRollers),
              new WaitCommand(0.5),
              new InstantCommand(intakeRollers::rollersOff, intakeRollers),
              new WaitCommand(0.25),
              new InstantCommand(intakeRollers::rollersOn, intakeRollers),
              new WaitCommand(1.5),
              new InstantCommand(intakeRollers::rollersOff, intakeRollers)
            )
          )
        )
      )
    );
  }
}
