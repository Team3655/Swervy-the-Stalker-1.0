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
    static double P=.5;

    public TurnEvent(double angle){
        init(angle);
    }
    
    private TurnEvent(double angle, long delay){
        super(delay);
        init(angle);
    }

    private TurnEvent(double angle,  double maxSpeed, long delay){
        super(delay);
        init(angle, maxSpeed);
        
    }

    private TurnEvent(double angle, double maxSpeed){
        init(angle, maxSpeed);
    }

    private void init(double angle){
        this.angle = (DrivetrainSubsystem.getInstance().getGyroscope().getAngle().toRadians()+angle)%(2 * Math.PI);
    } 

    private void init(double angle,double maxSpeed){
        init(angle);
        this.maxSpeed = maxSpeed;
    }
    
    @Override
    public void task() {
        switch(state){
            case 0:
                double error = DrivetrainSubsystem.getInstance().getGyroscope().getAngle().toRadians()-angle;
                double power = error * P;    

                DrivetrainSubsystem.getInstance().drive(new Translation2d(), power, true);

                if(Math.abs(error)< .1){  
                    state++;
                }
            break;
        }
    }
    public boolean eventCompleteCondition(){
        return state==1;
    }

}
