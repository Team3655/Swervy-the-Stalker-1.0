package frc.robot.event.eventSequences;


import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.IntakeSubsystems;
import com.swervedrivespecialties.exampleswerve.subsystems.PneumaticSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.ShootSubsystem;

import java.util.function.DoubleConsumer;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.event.DriveEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.TurnEvent;


public class Auton extends EventSequence{

    public enum AUTON_ALIAS {shootAndGrab};

    public Auton(){
        this(AUTON_ALIAS.values()[(int)Math.floor(Robot.getRobot().getTuningValue("autonAlias"))]);
        
    }

    public Auton(AUTON_ALIAS alias) {
        super(
            new Event[] {


                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.2, 0), 0, true)),
                
                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, true),1750),

                new Event(          ShootSubsystem.getInstance()::lower),
                
                new Event(          PneumaticSubsystem.getInstance()::toggleIntakeSolenoid),


                new Event(          ShootSubsystem.getInstance()::shootOn,500),
                new Event(          ShootSubsystem.getInstance()::indexOn,1500),
                new Event(          ShootSubsystem.getInstance()::shootOff,4000),
                new Event(          ShootSubsystem.getInstance()::indexOff),
                
                                    //Turn Right PI/2 Radians
                /*new                 TurnEvent(-Math.PI/2),
                new                 TurnEvent(0, 2000),
                                    //Activate Intake, Drive Forward
                new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(-1)),
                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.1, 0), 0, true)),
                                   
                //Deactivate Intake, Stop
                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, true),1000),
                new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(-0)),

                                    //Turn Left
                new                 TurnEvent(Math.PI/2),

                                    //Shooting
                new Event(          ShootSubsystem.getInstance()::shootOn),
                new Event(          ShootSubsystem.getInstance()::indexOn),
                new Event(          ShootSubsystem.getInstance()::shootOff,1666l),
                new Event(          ShootSubsystem.getInstance()::indexOff),*/
        });
    }
    
}
