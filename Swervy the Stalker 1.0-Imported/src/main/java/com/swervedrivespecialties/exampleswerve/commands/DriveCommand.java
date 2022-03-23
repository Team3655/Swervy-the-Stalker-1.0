package com.swervedrivespecialties.exampleswerve.commands;

import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.math.geometry.Translation2d;
import org.frcteam2910.common.robot.Utilities;


public class DriveCommand extends Command {

    public DriveCommand() {
        requires(DrivetrainSubsystem.getInstance());
    }

    double lastRotation=0;
    boolean enabled = true;

    @Override
    protected void execute() {
        //if (Robot.getRobot().isTeleopEnabled()){
            double forward = -(Robot.getOi().getPrimaryJoystick().getRawAxis(1))*.6;
            if (Robot.getOi().getPrimaryJoystick().getPOV() == 0) {
                forward = .4;
            } else if (Robot.getOi().getPrimaryJoystick().getPOV() == 180) {
                forward = -.4;
            }
            forward = Utilities.deadband(forward);
            // Square the forward stick
            forward = Math.copySign(Math.pow(forward, 2.0), forward);

            double strafe = -(Robot.getOi().getPrimaryJoystick().getRawAxis(0))*.6;
            strafe = Utilities.deadband(strafe);
            // Square the strafe stick
            strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);

            double rotation = -(Robot.getOi().getPrimaryJoystick().getRawAxis(4))*.6;
            rotation = Utilities.deadband(rotation);
            // Square the rotation stick
            rotation = Math.copySign(Math.pow(rotation, 2.0), rotation);

            DrivetrainSubsystem.getInstance().drive(new Translation2d(forward, strafe), rotation, true);
        //}
    }

    public void toggleEnabled() {
     enabled=! enabled; 
    }
    
    
    
    @Override
    protected boolean isFinished() {
        return false;
    }
}
