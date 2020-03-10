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
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.SetIntakeArmPosition;
import frc.robot.commands.StraightDrive;
import frc.robot.commands.ToggleFlywheel;
import frc.robot.subsystems.AlignmentBelt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeArm.Position;
import frc.robot.subsystems.IntakeRollers;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DeadreckonShotAuton extends SequentialCommandGroup {
  /**
   * Creates a new DeadreckonShotAuton.
   */
  public DeadreckonShotAuton(Drivetrain drivetrain, NavX navX, Turret turret, Flywheel flywheel, Indexer indexer,
    AlignmentBelt alignmentBelt, IntakeArm intakeArm, IntakeRollers IntakeRollers) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new ParallelCommandGroup(
        new StraightDrive(drivetrain, navX, -0.5, 24),
        new InstantCommand(() -> turret.setPosition(51), turret),
        new SetIntakeArmPosition(intakeArm, Position.Out)
      ),
      new InstantCommand(IntakeRollers::rollersOn, IntakeRollers),
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
