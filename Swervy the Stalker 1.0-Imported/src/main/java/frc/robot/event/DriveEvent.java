package frc.robot.event;


import javax.swing.text.AbstractDocument.BranchElement;

import com.revrobotics.CANSparkMax.ControlType;
import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import org.frcteam2910.common.drivers.SwerveModule;
import org.frcteam2910.common.math.Rotation2;

import edu.wpi.first.math.geometry.Translation2d;


public class DriveEvent extends Event{
    int state=0;
    double[] target;
    double angle;
    double maxSpeed=1;
    SwerveModule[] swerveModules;
    static double P=.5;

    public DriveEvent(double dist,double angle){
        init(dist,angle);
    }

    public DriveEvent(long delay,double dist,double angle){
        super(delay);
        init(dist,angle);
    }

    public DriveEvent(double dist,double angle,double maxSpeed){
        init(dist,angle);
        this.maxSpeed=maxSpeed;
    }

    public DriveEvent(long delay,double dist,double angle,double maxSpeed){
        super(delay);
        init(dist,angle);
        this.maxSpeed=maxSpeed;
    }

    private void init(double dist,double angle){
        if (dist>=0){
            this.angle=angle;
        } else {
            dist=-dist;
            this.angle=angle+Math.PI;
        }
        this.angle=angle%(2*(Math.PI));
        target=new double[4];
        swerveModules=DrivetrainSubsystem.getInstance().getSwerveModules();
        for (int i=0;i<4;i++){
            target[i]=swerveModules[i].getCurrentDistance()+dist;
        }
    }

    @Override
    public void task(){
        switch (state){
            //Move motors
            case 0:
                double error=0;
                for (int i=0;i<4;i++){
                    error+=Math.abs(target[i]-swerveModules[i].getCurrentDistance());
                }
                error/=4;
                Robot.eHandler.triggerEvent(new PrintEvent("Error:"+error));
                double speed=P*error;
                Robot.eHandler.triggerEvent(new PrintEvent("Speed:"+speed));
                Robot.eHandler.triggerEvent(new PrintEvent("Current Distance: "+swerveModules[0].getCurrentDistance()+"; Target: "+target[0]));
                if (speed>maxSpeed){
                    speed=maxSpeed;
                } else if (speed<-maxSpeed){
                    speed=-maxSpeed;
                }
                DrivetrainSubsystem.getInstance().drive(new Translation2d(speed*Math.cos(angle), speed*Math.sin(angle)), 0, true);
                if (swerveModules[0].getCurrentDistance()>=target[0]){
                    state++;
                }
            break;
            case 1:
                for (SwerveModule s:swerveModules){
                    DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, true);
                }
                Robot.eHandler.triggerEvent(new PrintEvent("Drive event complete!"));
                state++;
            break;
        }
    }

    

    public boolean eventCompleteCondition(){
        return state==2;
    }
}
