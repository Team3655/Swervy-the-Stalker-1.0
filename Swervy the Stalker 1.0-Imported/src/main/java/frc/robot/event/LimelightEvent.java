package frc.robot.event;

import com.swervedrivespecialties.exampleswerve.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.event.Event;

public class LimelightEvent extends Event {
    private final double P = .02;
    private final double Ppos=-.01;
    private boolean enabled=false;
    private boolean terminated;
    private double max=.2;
    private double turnOutput=0;
    public LimelightEvent(){
        super();
    }

    @Override
    public void task(){
        if (enabled&&Robot.limelight.hasTarget()){
            //do limelight yams
            double shootSpeed=P*Robot.limelight.getY();
            if (Math.abs(shootSpeed)>max){
                shootSpeed=max*Math.abs(shootSpeed)/shootSpeed;
            }
            //error will equal the angle of y the limelight returns
            //set motor speed as P times error
            
            //Robot.getInstance().turret().set(power);

            //Robot.eHandler.triggerEvent(new PrintEvent("Doing limelight yams."+Robot.getInstance().turret().getEncoder().getPosition()));
            //Robot.getInstance().turret().set(P*Robot.getInstance().getLimelight().getX());

            //double h = 2; //distance between turret and target (height)
            //double d = h/Math.tan(-Robot.getInstance().getLimelight().getX()); //distance between turret and target (length)
            //double t = Math.sqrt(19.6*h)/9.8; //time for power cell to travel from turret to target
            //double v = Math.sqrt(Math.pow(d/t, 2)+19.6*h)*Robot.getInstance().getTuningValue("velocityCoefficient"); //velocity of power cell
            //double angle = Math.sinh(Math.sqrt(19.6*h)/v); //elevator angle relative to floor
            //double targetAngle = angle+Robot.getInstance().getLimelight().getX(); //the target angle from Limelight*/
            //if (Math.abs(v)>6000){
                //v=6000*Math.abs(v)/v;
            //}
            //Robot.getInstance().setTuningValue("shoot", v);
            //easier way is recording power and angle requirements for many distances and 
            //other way:
            double error=Robot.limelight.getX();
            SmartDashboard.putNumber("limelightError", error);
            //error will equal the angle of x the limelight returns
            if (Math.abs(error) >1.5){
                turnOutput=error*Ppos;
            } else {
                turnOutput=0;
            }

        } else {
            turnOutput=0;
        }
        SmartDashboard.putBoolean("Tracking", enabled);
        
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
    }

    public void setEnabled(boolean enabled){
        this.enabled=enabled;
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
        return (Robot.limelight.hasTarget()&&Math.abs(Robot.limelight.getX())<1.5/*&&Math.abs(Robot.limelight.getY())<.01*/);
    }
}
