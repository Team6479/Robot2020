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
public class TrenchPickupAuton extends SequentialCommandGroup {
  /**
   * Creates a new TrenchPickupAuton.
   */
  public TrenchPickupAuton(Drivetrain drivetrain, NavX navX, IntakeArm intakeArm, IntakeRollers intakeRollers, Turret turret,
    Flywheels flywheels, Indexer indexer, AlignmentBelt alignmentBelt) {
    super(
      new SetIntakeArmPosition(intakeArm, Position.Out),
      new InstantCommand(intakeRollers::rollersOn, intakeRollers),
      new StraightDrive(drivetrain, navX, 0.5, 124), // drive towards the trench
      new WaitCommand(0.75), // Wait a little to ensure the last ball gets taken
      new InstantCommand(intakeRollers::rollersOff, intakeRollers),
      new TurnDrivetrain(drivetrain, navX, 145, Direction.Right),
      new InstantCommand(drivetrain::resetEncoders, drivetrain),
      new WaitCommand(0.5),
      new StraightDrive(drivetrain, navX, 0.5, 40),
      new WaitCommand(0.5),
      new InstantCommand(() -> turret.setPosition(turret.getCenter()), turret), // reset turret position
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
