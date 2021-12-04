package com.swervedrivespecialties.exampleswerve.commands;

import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import org.frcteam2910.common.robot.Utilities;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveCommand extends Command {

    public DriveCommand() {
        requires(DrivetrainSubsystem.getInstance());
    }

    double lastRotation=0;

    @Override
    protected void execute() {
        double forward = -Robot.getOi().getPrimaryJoystick().getRawAxis(1);
        forward = Utilities.deadband(forward);
        // Square the forward stick
        forward = Math.copySign(Math.pow(forward, 2.0), forward);

        double strafe = -Robot.getOi().getPrimaryJoystick().getRawAxis(0);
        strafe = Utilities.deadband(strafe);
        // Square the strafe stick
        strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);
        double x=Robot.getOi().getSecondaryJoystick().getX();
        double y=Robot.getOi().getSecondaryJoystick().getY();
        if (Math.abs(x)<.1) {
            x=0;
        }
        if (Math.abs(y)<.1) {
            y=0;
        }
        double rotation;
        if (!(x==0&&y==0)){
            rotation = /*Robot.getOi().getPrimaryJoystick().getRawAxis(4);*/Math.acos(x/Math.sqrt(Math.pow(x, 2.0)+Math.pow(y, 2.0)))-Math.PI/2;
        } else{
            rotation=lastRotation;
        }
        rotation = Utilities.deadband(rotation);
        // Square the rotation stick
        //rotation = Math.copySign(Math.pow(rotation, 2.0), rotation);
        lastRotation=rotation;
        SmartDashboard.putNumber("Target Angle", rotation);
        //rotation=0;

        DrivetrainSubsystem.getInstance().drive(new Translation2d(forward, strafe).div(3), rotation, true);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
