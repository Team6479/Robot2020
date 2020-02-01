/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.team6479.lib.commands.TeleopTankDrive;
import com.team6479.lib.controllers.CBXboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.TurnIntakeRollers;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.IntakeRollers;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final IntakeRollers intakeRollers = new IntakeRollers();
  private final IntakeArm intakeArm = new IntakeArm();
  private final Drivetrain drivetrain = new Drivetrain();

  private final CBXboxController xbox = new CBXboxController(0);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // The button bindings for TurnIntakeRollers and MoveIntakeArm are just random button assignments and can be changed later
    xbox.getButton(XboxController.Button.kY)
      .whenPressed(new TurnIntakeRollers(intakeRollers, intakeArm));
    xbox.getButton(XboxController.Button.kX)
      .whenPressed(new InstantCommand(intakeArm::toggleArm, intakeArm));

    drivetrain.setDefaultCommand(new TeleopTankDrive(drivetrain,
      () -> -xbox.getY(Hand.kLeft),
      () -> xbox.getX(Hand.kRight)));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    // TODO: Add autonomous command
    return null;
  }
}
