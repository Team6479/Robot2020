package frc.robot.autons;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.StraightDrive;
import frc.robot.commands.TurnDrivetrain;
import frc.robot.commands.TurnDrivetrain.Direction;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;

public class DriveSquare extends SequentialCommandGroup { 

    public DriveSquare(Drivetrain drivetrain, NavX navX){ 
    
        super(

        new StraightDrive(drivetrain, navX, .5, 24),
        new InstantCommand(() -> drivetrain.resetEncoders(), drivetrain),
        new WaitCommand(.5),
        new TurnDrivetrain(drivetrain, navX, 80, Direction.Right),
        new WaitCommand(.5),
        new StraightDrive(drivetrain, navX, .5, 24),
        new InstantCommand(() -> drivetrain.resetEncoders(), drivetrain),
        new WaitCommand(.5),
        new TurnDrivetrain(drivetrain, navX, 80, Direction.Right),
        new WaitCommand(.5),
        new StraightDrive(drivetrain, navX, .5, 24),
        new InstantCommand(() -> drivetrain.resetEncoders(), drivetrain),
        new WaitCommand(.5),
        new TurnDrivetrain(drivetrain, navX, 80, Direction.Right),
        new WaitCommand(.5),
        new StraightDrive(drivetrain, navX, .5, 24),
        new InstantCommand(() -> drivetrain.resetEncoders(), drivetrain),
        new WaitCommand(.5),
        new TurnDrivetrain(drivetrain, navX, 80, Direction.Right)






        );

    }





}