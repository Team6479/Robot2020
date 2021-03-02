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
import frc.robot.commands.SpinUpFlywheels;
import frc.robot.commands.StraightDrive;
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
      new ParallelCommandGroup(
        new SetIntakeArmPosition(intakeArm, Position.Out),
        new SequentialCommandGroup(
          new StraightDrive(drivetrain, navX, 0.5, 114),
          new WaitCommand(0.5) // Wait a little to ensure the last ball gets taken
        ),
        new InstantCommand(intakeRollers::rollersOn, intakeRollers)
      ),
      new ParallelCommandGroup(
        new InstantCommand(intakeRollers::rollersOff, intakeRollers), // for reference: this was commented out 
        new SequentialCommandGroup(
          new TurnDrivetrain(drivetrain, navX, 180, Direction.Right),
          new StraightDrive(drivetrain, navX, 0.5, 40)),
        new InstantCommand(() -> Limelight.setCamMode(CamMode.VisionProcessor)),
        new SequentialCommandGroup(
          new InstantCommand(() -> turret.setPosition(-95), turret),
          new InstantCommand(() -> {
            Limelight.setLEDState(LEDState.Auto);
          })
        )
      ),
      new InstantCommand(() -> {
        DriverStation.reportWarning("Waiting For Target...", false);
      }),
      new WaitUntilCommand(Limelight::hasTarget), // Only Aim and continue with shooting if we have a valid target
      new InstantCommand(() -> {
        DriverStation.reportWarning("Target Found. Continuing.", false);
      }),
      new ParallelCommandGroup(
        new SequentialCommandGroup(
          new AimTurret(turret),
          new SpinUpFlywheels(flywheels),
          // might need a WaitCommand here --> if there isn't enough time for the RPM to reach
          // what it should be, then put a WaitCommand and investigate the isFinished() method
          // of SpinUpFlywheel (which currently returns true!!)
          new ParallelCommandGroup(
            new InstantCommand(indexer::run, indexer),
            new InstantCommand(alignmentBelt::run, alignmentBelt)
          )
        ),
        new SequentialCommandGroup( 
          new WaitCommand(5),
          new SetIntakeArmPosition(intakeArm, Position.In) // if needed, this can be taken 
          // out (no reason to bring the intake back in)
        )
      )
    );
  }
}
