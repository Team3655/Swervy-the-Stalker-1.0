package com.swervedrivespecialties.exampleswerve.commands;

import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.robot.buttons.JSBAdapter;
import edu.wpi.first.math.geometry.Translation2d;
import org.frcteam2910.common.robot.Utilities;


public class DriveCommand extends Command {

    public DriveCommand() {
        requires(DrivetrainSubsystem.getInstance());
    }

    double lastRotation=0;
    boolean enabled = true;
    boolean fieldCentric = true;
    double translationMultiplier = .7;
    double rotationMultiplier = .75;
    boolean lock = false;

    @Override
    protected void execute() {
        if (DriverStation.isTeleopEnabled()) {
            if (!lock) {
                SmartDashboard.putBoolean("Locked", false);
                double forward = -(Robot.getRobot().getJSBAdapter().getY())*translationMultiplier;
                if (Robot.getRobot().getJSBAdapter().getPOV() == 0) {
                    forward = .4;
                } else if (Robot.getRobot().getJSBAdapter().getPOV() == 180) {
                    forward = -.4;
                }

                forward = Utilities.deadband(forward);
                // Square the forward stick
                forward = Math.copySign(Math.pow(forward, 2.0), forward);

                double strafe = -(Robot.getRobot().getJSBAdapter().getX())*translationMultiplier;
                strafe = Utilities.deadband(strafe);
                // Square the strafe stick
                strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);
                
                double rotation = 0;
                
                
                if (Robot.limelight.isEnabled() && rotation == 0){
                    rotation=Robot.limelight.getTurnOutput();
                    SmartDashboard.putNumber("rotation from limelight", rotation);
                } else {
                    rotation = -(Robot.getRobot().getJSBAdapter().getV())*rotationMultiplier;
                    rotation = Utilities.deadband(rotation);
                    SmartDashboard.putNumber("rotation from joystick", rotation);
                    // Square the rotation stick
                    rotation = Math.copySign(Math.pow(rotation, 2.0), rotation);

                }
                


                DrivetrainSubsystem.getInstance().drive(new Translation2d(forward, strafe), rotation, fieldCentric);
            } else {
                DrivetrainSubsystem.getInstance().wheelLock();
            }
        }
    }

    public void setLock(boolean it) {
        lock = it;
    }

    public void setRotationMultiplier(double d){
        rotationMultiplier=d;
    }

    public void setTranslationMultiplier(double d){
        translationMultiplier=d;
    }

    public void setFieldCentric(boolean b){
        fieldCentric=b;
    }

    public void toggleEnabled() {
     enabled=! enabled; 
    }
    
    
    
    @Override
    protected boolean isFinished() {
        return false;
    }
}
