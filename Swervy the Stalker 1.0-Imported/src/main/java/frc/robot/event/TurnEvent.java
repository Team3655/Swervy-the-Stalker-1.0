package frc.robot.event;


import javax.swing.text.AbstractDocument.BranchElement;

import com.revrobotics.CANSparkMax.ControlType;
import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import org.frcteam2910.common.drivers.Gyroscope;
import org.frcteam2910.common.drivers.SwerveModule;
import org.frcteam2910.common.math.Rotation2;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnEvent extends Event{
    int state=0;
    double[] target;
    short[] inverted;
    double angle;
    double maxSpeed=1;
    SwerveModule[] swerveModules;
    static double P = .01;

    public TurnEvent(double angle){
        init(angle);
    }
    
    public TurnEvent(double angle, long delay){
        super(delay);
        init(angle);
    }

    public TurnEvent(double angle,  double maxSpeed, long delay){
        super(delay);
        init(angle, maxSpeed);
        
    }

    public TurnEvent(double angle, double maxSpeed){
        init(angle, maxSpeed);
    }

    public void init(double angle){
        this.angle = (DrivetrainSubsystem.getInstance().getGyroscope().getAngle().toRadians()+angle)%(2 * Math.PI);
    } 

    public void init(double angle,double maxSpeed){
        init(angle);
        this.maxSpeed = maxSpeed;
    }
    
    @Override
    public void task() {
        switch(state){
            case 0:
                double error = DrivetrainSubsystem.getInstance().getGyroscope().getAngle().toRadians() - angle;
                double power = error * P;
                SmartDashboard.putNumber("turnError", Math.toDegrees(error));
                SmartDashboard.putNumber("turnErrorRad", error);
                SmartDashboard.putNumber("gyroRad", DrivetrainSubsystem.getInstance().getGyroscope().getAngle().toRadians());
                SmartDashboard.putNumber("targetTurnAngle", Math.toDegrees(angle));
                SmartDashboard.putNumber("targetTurnAngleRad", angle);
                if (Math.abs(power) > maxSpeed){
                    power=Math.copySign(maxSpeed, power);
                }
                DrivetrainSubsystem.getInstance().drive(new Translation2d(), power, true);

                if(Math.abs(error) < .1){  
                    state++;
                }
            break;
        }
    }
    public boolean eventCompleteCondition(){
        return state==1;
    }

}