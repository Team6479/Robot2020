package frc.robot.autons;

import com.team6479.lib.util.Limelight;
import com.team6479.lib.util.Limelight.CamMode;
import com.team6479.lib.util.Limelight.LEDState;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AimTurret;
import frc.robot.commands.SetIntakeArmPosition;
import frc.robot.commands.StraightDrive;
import frc.robot.commands.Turn180Encoders;
import frc.robot.subsystems.AlignmentBelt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheels;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeRollers;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.IntakeArm.Position;

public class TrenchPickupAuton extends SequentialCommandGroup {
  public TrenchPickupAuton(Turret turret, IntakeArm intakeArm, IntakeRollers intakeRollers,
      Flywheels flywheels, Indexer indexer, AlignmentBelt alignmentBelt, Drivetrain drivetrain) {
    super(
      new SetIntakeArmPosition(intakeArm, Position.Out), // extend intake to avoid collision with turret
      new InstantCommand(() -> { // limelight on
        Limelight.setCamMode(CamMode.VisionProcessor);
        Limelight.setLEDState(LEDState.Auto);
      }),
      new AimTurret(turret), // aimturret stops once it has a target
      new InstantCommand(() -> flywheels.setSpeed(16000, 0), flywheels), // tune for starting line
      new WaitCommand(0.5), // duration to spin up flywheel
      new InstantCommand(indexer::run, indexer),
      new InstantCommand(alignmentBelt::run, alignmentBelt),
      new WaitCommand(3), // duration to shoot 3 balls
      new InstantCommand(alignmentBelt::stop, alignmentBelt),
      new InstantCommand(indexer::stop, indexer),
      new InstantCommand(() -> { // limelight off
        Limelight.setCamMode(CamMode.DriverCamera);
        Limelight.setLEDState(LEDState.Off);
      }),
      new WaitCommand(0.1), // make sure the limelight is actually off
      new SetIntakeArmPosition(intakeArm, Position.In), // make sure it doesn't have issues turning with intake out
      new Turn180Encoders(drivetrain),
      new SetIntakeArmPosition(intakeArm, Position.Out), // move intake back out
      new InstantCommand(intakeRollers::rollersOn, intakeRollers),
      new StraightDrive(drivetrain, 50749), // distance in encoder units
      new InstantCommand(intakeRollers::rollersOff, intakeRollers),
      new SetIntakeArmPosition(intakeArm, Position.In), // intake might hit the trench otherwise, can remove if too many balls
      new Turn180Encoders(drivetrain),
      new SetIntakeArmPosition(intakeArm, Position.Out),
      new InstantCommand(() -> turret.setPosition(0)), // change to the centrepoint such that the limelight can see
      new InstantCommand(() -> { // limelight on
        Limelight.setCamMode(CamMode.VisionProcessor);
        Limelight.setLEDState(LEDState.Auto);
      }),
      new WaitCommand(0.1), // make sure the limelight is actually off
      new AimTurret(turret),
      new InstantCommand(() -> flywheels.setSpeed(16000, 800), flywheels), // tune for under trench
      new WaitCommand(0.5), // duration to spin up flywheel
      new InstantCommand(indexer::run, indexer),
      new InstantCommand(alignmentBelt::run, alignmentBelt),
      new WaitCommand(5), // duration to shoot 5 balls
      new InstantCommand(alignmentBelt::stop, alignmentBelt),
      new InstantCommand(indexer::stop, indexer),
      new InstantCommand(() -> { // limelight off
        Limelight.setCamMode(CamMode.DriverCamera);
        Limelight.setLEDState(LEDState.Off);
      }),
      new SetIntakeArmPosition(intakeArm, Position.In) // end with intake in just for fun
    );
  }
}