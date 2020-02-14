/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.TurnDrivetrain.Direction;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Turret;

// NOTE: Consider using this command inline, rather than writing a subclass. For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Auto extends SequentialCommandGroup {
  /**
   * Creates a new Auto.
   */
  public Auto(Drivetrain drivetrain, Turret turret) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    addCommands(
        // START OF AUTO 1
        new StraightDrive(drivetrain, 0, 0),
        new AimTurret(turret),
        // TODO: Shooting command
        // TODO: START OF AUTO 2
        new TurnDrivetrain(drivetrain, 0, Direction.Left),
        new StraightDrive(drivetrain, 0, 0),
        // TODO: Move intake down
        new TurnDrivetrain(drivetrain, 0, Direction.Left),
        new StraightDrive(drivetrain, 0, 0),
        // TODO: Pull up intake
        // TODO: START OF AUTO 3
        new AimTurret(turret)
        // TODO: Shooting command
    );
  }
}
