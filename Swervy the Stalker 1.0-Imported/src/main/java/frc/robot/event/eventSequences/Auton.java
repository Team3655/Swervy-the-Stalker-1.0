package frc.robot.event.eventSequences;


import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.event.DriveEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;


public class Auton extends EventSequence{

    public Auton() {
        super(
            //new DriveEvent(20d, Math.PI, .2),
            new Event[] {
            
                new Event(new Runnable() {
                    @Override
                    public void run(){
                        DrivetrainSubsystem.getInstance().drive(new Translation2d(-.1, 0), 0, true);
                    }
    
                }),
            
                new Event(new Runnable() {
                    @Override
                    public void run(){
                        DrivetrainSubsystem.getInstance().drive(new Translation2d(0, 0), 0, true);
                    }
    
                },50000)
                
        });
    }
    
}
