package frc.robot.event;


import javax.swing.text.AbstractDocument.BranchElement;

import com.revrobotics.CANSparkMax.ControlType;
import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import org.frcteam2910.common.drivers.SwerveModule;
import org.frcteam2910.common.math.Rotation2;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveEvent extends Event{
    int state=0;
    double[] target;
    short[] inverted;
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
        SwerveModuleState[] states = DrivetrainSubsystem.getInstance().getStates(new Translation2d(Math.cos(angle), Math.sin(angle)), 0, true);
        this.angle = angle%(2*(Math.PI));
        target = new double[4];
        inverted=new short[4];
        swerveModules = DrivetrainSubsystem.getInstance().getSwerveModules();
        for (int i=0;i<4;i++){
            inverted[i]=(short)Math.copySign(1d,states[i].speedMetersPerSecond);
            target[i] = swerveModules[i].getCurrentDistance()+dist*inverted[i];
        }
    }

    @Override
    public void task(){
        switch (state){
            //Move motors
            case 0:
                double error=0;
                double individualErrors[]=new double[4];
                for (int i=0;i<4;i++){
                    /*error +=*/individualErrors[i]= (target[i]-swerveModules[i].getCurrentDistance())*((double)(inverted[i]));
                    error+=individualErrors[i];
                    SmartDashboard.putNumber("error "+i, individualErrors[i]);   
                    //Robot.eHandler.triggerEvent(new PrintEvent("Error["+i+"]:"+error));
                }
                
                error/=4;
                double speed=P*error;
                //Robot.eHandler.triggerEvent(new PrintEvent("Error:"+error+" Speed:"+speed+" Current Distance: "+swerveModules[0].getCurrentDistance()+" Target: "+target[0]));
                if (speed>maxSpeed){
                    speed=maxSpeed;
                } else if (speed<-maxSpeed){
                    speed=-maxSpeed;
                }
                DrivetrainSubsystem.getInstance().drive(new Translation2d(speed*Math.cos(angle), speed*Math.sin(angle)), 0, true);
                if (error>=-.2&&error<=.2){
                    state++;
                }
            break;
            case 1:
                for (SwerveModule s:swerveModules){
                    DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, true);
                }
               // Robot.eHandler.triggerEvent(new PrintEvent("Drive event complete!"));
                state++;
            break;
        }
    }

    

    public boolean eventCompleteCondition(){
        return state==2;
    }
}
