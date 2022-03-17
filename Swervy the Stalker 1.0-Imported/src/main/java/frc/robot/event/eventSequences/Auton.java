package frc.robot.event.eventSequences;


import frc.robot.event.DriveEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;


public class Auton extends EventSequence{

    public Auton() {
        super(new Event[] {
            new DriveEvent(20d, Math.PI, .2),
        });
    }
    
}
