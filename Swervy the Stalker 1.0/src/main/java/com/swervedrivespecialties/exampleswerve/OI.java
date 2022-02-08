package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.PneumaticSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class OI {
    /*
       Add your joysticks and buttons here
     */
    private Joystick primaryJoystick = new Joystick(0);
    private Joystick secondaryJoystick = new Joystick(1);
    
    
    public OI() {
        // Back button zeroes the drivetrain
        new JoystickButton(primaryJoystick, 7).whenPressed(
                new InstantCommand(() -> DrivetrainSubsystem.getInstance().resetGyroscope())
        );
        //Toggle between Arm and Base
        //new JoystickButton(primaryJoystick, 1).whenPressed(
        //    new InstantCommand(() -> ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).toggleEnabled())
        //); 

        new JoystickButton(secondaryJoystick, 4).whenPressed(
                new InstantCommand(() -> PneumaticSubsystem.getInstance().closeGrip1Solenoid())
        ); 
        new JoystickButton(secondaryJoystick, 2).whenPressed(
            new InstantCommand(() -> PneumaticSubsystem.getInstance().closeGrip2Solenoid())
        ); 
        new JoystickButton(secondaryJoystick, 1).whenPressed(
                new InstantCommand(() -> PneumaticSubsystem.getInstance().openGrip1Solenoid())
        ); 
        new JoystickButton(secondaryJoystick, 3).whenPressed(
            new InstantCommand(() -> PneumaticSubsystem.getInstance().openGrip2Solenoid())
        ); 
        // Meed to add toggle control of Air compressor
        //new JoystickButton(secondaryJoystick, 8).whenPressed(
        //    new InstantCommand(() -> pcm.)
        //); 
    }

    public Joystick getPrimaryJoystick() {
        return primaryJoystick;
    }

    public Joystick getSecondaryJoystick() {
        return secondaryJoystick;
    }
}
