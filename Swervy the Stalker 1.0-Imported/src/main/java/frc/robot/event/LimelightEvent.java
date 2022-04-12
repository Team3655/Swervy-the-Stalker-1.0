package frc.robot.event;

import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.ShootSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.event.Event;
import gameutil.math.geom.Point;
import com.swervedrivespecialties.exampleswerve.commands.DriveCommand;

public class LimelightEvent extends Event {
    private final double I = -.000001;
    private final double P = -.008;
    private double integral = 0; 
    private long lastUpdate = System.currentTimeMillis();
    private boolean enabled = false;
    private boolean terminated;
    private double max = .2;
    private double dBand = 1;
    private double dBand2 = 3;
    private double turnOutput = 0;
    private boolean wasLockedOn=false;
    public LimelightEvent(){
        super();
    }

    @Override
    public void task(){
        if (enabled&&Robot.limelight.hasTarget()){
             //do limelight yams
             
             if (targetLocked()){
                ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setLock(true);   
             } else {
                
                updateSpeed();
                double error=Robot.limelight.getX();
                long currentTime=System.currentTimeMillis();
                integral += error*(currentTime - lastUpdate);
                lastUpdate=currentTime;
                SmartDashboard.putNumber("limelightError", error);
                SmartDashboard.putNumber("limelightIntegral", integral);
                //error will equal the angle of x the limelight returns
                //if (Math.abs(error) > dBand2) {
                    
                ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setLock(false);
                turnOutput=error*P+integral*I;

                //} else {
                    //turnOutput=0;
                //}
            }
            

        } else {
            turnOutput=0;
            wasLockedOn=false;
        }
        SmartDashboard.putBoolean("Tracking", enabled);
        SmartDashboard.putBoolean("Target Locked", targetLocked());
    }

    @Override
    public boolean eventCompleteCondition(){
        return false;//Robot.getInstance().isEnabled();//should run until robot is turned off
    }

    public void enable(){
        updateSpeed();
        lastUpdate = System.currentTimeMillis();
        enabled=true;
        integral = 0;
    }

    public void disable(){
        enabled=false;
        ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setLock(false);
    }

    public void setEnabled(boolean enabled){
        if (enabled){
            enable();
        } else {
            disable();
        }
    }

    public boolean isEnabled(){
        return enabled;
    }

    public double getTurnOutput(){
        return turnOutput;
    }

    /**Returns weather or not turret is locked onto target
     * 
     * @return
     */
    public boolean targetLocked(){
        boolean lockedOn=(Robot.limelight.hasTarget()&&Math.abs(Robot.limelight.getX()) < dBand /*&&Math.abs(Robot.limelight.getY())<.01*/)||(wasLockedOn&&Math.abs(Robot.limelight.getX()) < dBand2);
        wasLockedOn=lockedOn;
        return lockedOn;
    }


    public void updateSpeed(){

        Point pL = null;
        double phi=Robot.limelight.getY();
        for(Point j: RobotMap.shootData){
            if(j.tuple.i(2) >= phi){
                if(pL != null){
                    if(Math.abs(j.tuple.i(2)-phi)<Math.abs(pL.tuple.i(2)-phi)){
                        pL = j;
                    }
                } else {
                    pL = j;
                }
            }
        }

        Point pS = null;
        for(Point j: RobotMap.shootData){
            if(j.tuple.i(2) <= phi){
                if(pS != null){
                    if(Math.abs(j.tuple.i(2)-phi)<Math.abs(pS.tuple.i(2)-phi)){
                        pS = j;
                    }
                } else {
                    pS = j;
        
                }
            }
        }

        Point speed;
        if(pL==null){
            speed = pS;
        }else if (pS==null){
            speed = pL;
        }else{
            if(pS .equals(pL)){
                speed = pS;
            }else{
                try {
                    double t = (phi - pL.tuple.i(2))/(pS.tuple.i(2)-pL.tuple.i(2));
                    speed = pS.lerp(pL, t);
                    SmartDashboard.putNumber("t val", t);
                } catch (Exception e){
                    e.printStackTrace();
                    speed = pS.lerp(pL, .5);
                }
            }
        }
        SmartDashboard.putNumber("Limespeed Top", speed.tuple.i(0));
        SmartDashboard.putNumber("Limespeed Bot", speed.tuple.i(1));
        try {
            SmartDashboard.putNumber("pL angle", pL.tuple.i(2));
            SmartDashboard.putNumber("pS angle", pS.tuple.i(2));
        } catch (NullPointerException e){

        }
        ShootSubsystem.getInstance().setSpeed(speed);
    }
    
}
