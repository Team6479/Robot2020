/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.autons;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AimTurret;
import frc.robot.commands.SpinUpFlywheel;
import frc.robot.commands.StraightDrive;
import frc.robot.commands.ToggleIntakeArm;
import frc.robot.commands.TurnDrivetrain;
import frc.robot.commands.TurnDrivetrain.Direction;
import frc.robot.subsystems.AlignmentBelt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeRollers;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Turret;

// NOTE: Consider using this command inline, rather than writing a subclass. For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Auto extends SequentialCommandGroup {
  /**
   * Creates a new Auto.
   */
  public Auto(Drivetrain drivetrain, NavX navX, Turret turret, Flywheel flywheel, Indexer indexer,
      AlignmentBelt alignmentBelt, IntakeRollers intakeRollers, IntakeArm intakeArm) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    addCommands(
        // START OF AUTO 1
        new StraightDrive(drivetrain, navX, 0, 0),
        new AimTurret(turret),
        new SpinUpFlywheel(flywheel),
        new InstantCommand(indexer::run, indexer),
        new InstantCommand(alignmentBelt::run, alignmentBelt),
        new WaitCommand(4), // TODO: Test and refine timing
        new InstantCommand(alignmentBelt::stop, alignmentBelt),
        new InstantCommand(indexer::stop, indexer),
        new InstantCommand(flywheel::off, flywheel),
        // START OF AUTO 2
        new TurnDrivetrain(drivetrain, navX, 0, Direction.Left),
        new StraightDrive(drivetrain, navX, 0, 0),
        new ParallelCommandGroup(new SequentialCommandGroup(new InstantCommand(() -> {
          if (intakeRollers.getSpeed() > 0) {
            intakeRollers.rollersOff();
          } else {
            intakeRollers.rollersOn();
          }
        }, intakeRollers), new ToggleIntakeArm(intakeArm),
            new TurnDrivetrain(drivetrain, navX, 0, Direction.Left))),
        new StraightDrive(drivetrain, navX, 0, 0),
        new ParallelCommandGroup(new SequentialCommandGroup(new InstantCommand(() -> {
          if (intakeRollers.getSpeed() > 0) {
            intakeRollers.rollersOff();
          } else {
            intakeRollers.rollersOn();
          }
        }, intakeRollers), new ToggleIntakeArm(intakeArm))),
        // START OF AUTO 3
        new AimTurret(turret),
        new SpinUpFlywheel(flywheel),
        new InstantCommand(indexer::run, indexer),
        new InstantCommand(alignmentBelt::run, alignmentBelt),
        new WaitCommand(4), // TODO: Test and refine timing
        new InstantCommand(alignmentBelt::stop, alignmentBelt),
        new InstantCommand(indexer::stop, indexer),
        new InstantCommand(flywheel::off, flywheel));
  }
}
