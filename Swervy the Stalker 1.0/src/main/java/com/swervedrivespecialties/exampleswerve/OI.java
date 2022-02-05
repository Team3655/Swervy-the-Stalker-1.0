package com.swervedrivespecialties.exampleswerve;
import com.swervedrivespecialties.exampleswerve.commands.DriveCommand;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class OI {
    /*
       Add your joysticks and buttons here
     */
    private Joystick primaryJoystick = new Joystick(0);
    //private final Joystick secondaryJoystick = new Joystick(1);

    public OI() {
        // Back button zeroes the drivetrain
        new JoystickButton(primaryJoystick, 7).whenPressed(
                new InstantCommand(() -> DrivetrainSubsystem.getInstance().resetGyroscope())
        );
        //Toggle between Arm and Base
        new JoystickButton(primaryJoystick, 1).whenPressed(
            new InstantCommand(() -> ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).toggleEnabled())
        ); 
    }

    public Joystick getPrimaryJoystick() {
        return primaryJoystick;
    }

    //public Joystick getSecondaryJoystick() {
    //    return secondaryJoystick;
    //}
}
