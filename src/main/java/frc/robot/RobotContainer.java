/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.team6479.lib.commands.TeleopTankDrive;
import com.team6479.lib.controllers.CBJoystick;
import com.team6479.lib.controllers.CBXboxController;
import com.team6479.lib.pathing.TrajectoryFileHandler;
import frc.robot.commands.SpinUpFlywheels;
import com.team6479.lib.util.Limelight;
import com.team6479.lib.util.Limelight.CamMode;
import com.team6479.lib.util.Limelight.LEDState;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.commands.ManualSpeedFlywheel;
import frc.robot.commands.TeleopIntakeArm;
import frc.robot.commands.TeleopTurretControl;
import frc.robot.commands.NavXLogging;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.AlignmentBelt;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheels;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeRollers;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Turret;
import frc.robot.autons.TrenchPickupAuton;
import frc.robot.autons.OppositeTrenchPickupAuton;
import frc.robot.autons.DriveAimShootAuton;
import frc.robot.commands.StraightDrive;
/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final NavX navX = new NavX();

  private final Drivetrain drivetrain = new Drivetrain();

  private final Turret turret = new Turret(-10, 50);

  private final IntakeRollers intakeRollers = new IntakeRollers();
  private final IntakeArm intakeArm = new IntakeArm();

  private final Indexer indexer = new Indexer();
  private final AlignmentBelt alignmentBelt = new AlignmentBelt();
  private final Flywheels flywheels = new Flywheels();

  private final CBXboxController xbox = new CBXboxController(0);
  private final CBJoystick joystick = new CBJoystick(1);

  private final SendableChooser<Command> autonChooser = new SendableChooser<>();

  private SendableChooser<Trajectory> tChooser;

  private double navXStartPosition;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    navX.reset(); // reset position on enable, remove later if necessary
    navXStartPosition = navX.getYaw();

    //flywheels.setDefaultCommand(new ManualSpeedFlywheel(flywheels));

    autonChooser.setDefaultOption("Trench Pickup",
        new TrenchPickupAuton(drivetrain, navX, intakeArm, intakeRollers, turret, flywheels, indexer, alignmentBelt));
    // autonChooser.addOption("Base Shoot Auto", new AimShootAuton(turret, flywheels, indexer, alignmentBelt));
    // autonChooser.addOption("Dead Rekon",
    //     new DeadreckonShotAuton(drivetrain, navX, turret, flywheels, indexer, alignmentBelt, intakeArm, intakeRollers));
    autonChooser.addOption("Opposite Trench Pickup", 
      new OppositeTrenchPickupAuton(drivetrain, navX, intakeArm, intakeRollers, turret, flywheels, indexer, alignmentBelt)
    );
    autonChooser.addOption("Do nothing", new InstantCommand());
    autonChooser.addOption("Drive forward", new StraightDrive(drivetrain, navX, 0.5, 30));
    autonChooser.addOption("Drive backwards, Aim, Shoot", new DriveAimShootAuton(drivetrain, navX, turret, flywheels, indexer, alignmentBelt, intakeArm));
    Shuffleboard.getTab("Main").add("Auton", autonChooser);

    // Configure the button bindings
    configureButtonBindings();

    // Get the trajectories in a sendable chooser
    tChooser = TrajectoryFileHandler.getTrajectories();

    SmartDashboard.putData("Paths", tChooser);


    Shuffleboard.getTab("Main").addBoolean("Target Alligned",
        () -> Limelight.getXOffset() <= 0.5 && Limelight.hasTarget());

    // DistanceCalculator limelightDist = new DistanceCalculator(20.25, 122.25,
    // Math.toRadians(Limelight.getSkew()));

    // Shuffleboard.getTab("Debug").addNumber("Distance From Target", () ->
    // limelightDist.calculate(Math.toRadians(Limelight.getYOffset())));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    xbox.getButton(XboxController.Button.kBumperRight)
        .whenPressed(new SequentialCommandGroup(new SequentialCommandGroup(
             new SpinUpFlywheels(flywheels), 
            // new ToggleFlywheel(flywheel), // TODO: Remove this when tuning is done
            //  new WaitUntilCommand(() -> flywheels.isAtSpeed() && flywheels.getIsOn()),
            new WaitCommand(1.5), // this should eventually be replaced with the line above, or equivalent code
            new InstantCommand(indexer::run, indexer), new InstantCommand(alignmentBelt::run, alignmentBelt)
            )))
        .whenReleased(new SequentialCommandGroup(new InstantCommand(alignmentBelt::stop, alignmentBelt),
            new InstantCommand(indexer::stop, indexer),
            new InstantCommand(flywheels::off, flywheels)
        ));

    xbox.getButton(XboxController.Button.kA).whenPressed(new SequentialCommandGroup(new InstantCommand(() -> {
      if (intakeRollers.getSpeed() > 0) {
        intakeRollers.rollersOff();
      } else {
        intakeRollers.rollersOn();
      }
    }, intakeRollers)));

    xbox.getButton(XboxController.Button.kB)
        .whenPressed(new SequentialCommandGroup(new InstantCommand(indexer::reverse, indexer),
            new InstantCommand(alignmentBelt::reverse, alignmentBelt)))
        .whenReleased(new SequentialCommandGroup(new InstantCommand(alignmentBelt::stop, alignmentBelt),
            new InstantCommand(indexer::stop, indexer)));

    intakeArm.setDefaultCommand(new TeleopIntakeArm(intakeArm, new Button(() -> xbox.getTriggerAxis(Hand.kRight) > 0),
        new Button(() -> xbox.getTriggerAxis(Hand.kLeft) > 0)));

    // joystick.getButton(7).whenPressed(new ToggleFlywheel(flywheel));

    // Toggle Limelight
    joystick.getButton(7).whenPressed(new InstantCommand(this::toggleLimelight));
    joystick.getButton(8).whenPressed(new InstantCommand(this::toggleLimelight));
    joystick.getButton(9).whenPressed(new InstantCommand(this::toggleLimelight));
    joystick.getButton(10).whenPressed(new InstantCommand(this::toggleLimelight));
    joystick.getButton(11).whenPressed(new InstantCommand(this::toggleLimelight));
    joystick.getButton(12).whenPressed(new InstantCommand(this::toggleLimelight));



    drivetrain.setDefaultCommand(
          new TeleopDrive(drivetrain, navX, navXStartPosition, () -> -xbox.getY(Hand.kLeft), () -> xbox.getX(Hand.kRight)));

    navX.setDefaultCommand(
      new NavXLogging(navX)
    );
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autonChooser.getSelected();

    /*
     * code for getting the autonomous routine through ramsete
    return drivetrain.getRamseteCommand(tChooser.getSelected()).andThen(() -> drivetrain.tankDriveVolts(0, 0));
    */

  }

  public void toggleLimelight() {
    LEDState ledState = com.team6479.lib.util.Limelight.getLEDState();
    if (ledState != LEDState.Auto) {
      Limelight.setLEDState(LEDState.Auto);
      Limelight.setCamMode(CamMode.VisionProcessor);
    } else if (ledState != LEDState.Off) {
      Limelight.setLEDState(LEDState.Off);
      Limelight.setCamMode(CamMode.DriverCamera);
    }
  }

  public void setDefaultStates() {
    intakeArm.armStop();
    intakeRollers.rollersOff();

    indexer.stop();
    alignmentBelt.stop();
    flywheels.off();
  }

  public void teleopInit() {
    turret.setDefaultCommand(new TeleopTurretControl(turret, joystick::getZ, joystick.getButton(1)));
  }

  public void disabledInit() {
    // We don't want any lingering corrections after disabling
    turret.clearCorrection();
    drivetrain.resetEncoders();
  }
}
