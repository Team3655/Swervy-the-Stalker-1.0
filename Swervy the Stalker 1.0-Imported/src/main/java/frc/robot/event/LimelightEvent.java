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
    private final double P = .02;
    private final double Ppos=-.01;
    private boolean enabled=false;
    private boolean terminated;
    private double max=.2;
    private double dBand = 1.5;
    private double turnOutput=0;
    public LimelightEvent(){
        super();
    }

    @Override
    public void task(){
        if (enabled&&Robot.limelight.hasTarget()){
             //do limelight yams
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

                //((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setLock(false);
                
                double error=Robot.limelight.getX();
                SmartDashboard.putNumber("limelightError", error);
                //error will equal the angle of x the limelight returns
                if (Math.abs(error) > dBand){
                    turnOutput=error*Ppos;
                } else {
                    turnOutput=0;
                }
            

        } else {
            turnOutput=0;
        }
        SmartDashboard.putBoolean("Tracking", enabled);
        SmartDashboard.putBoolean("Target Locked", targetLocked());
    }

    @Override
    public boolean eventCompleteCondition(){
        return false;//Robot.getInstance().isEnabled();//should run until robot is turned off
    }

    public void enable(){
        enabled=true;
    }

    public void disable(){
        enabled=false;
        //((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setLock(false);
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
        return (Robot.limelight.hasTarget()&&Math.abs(Robot.limelight.getX()) < dBand /*&&Math.abs(Robot.limelight.getY())<.01*/);
    }
}
