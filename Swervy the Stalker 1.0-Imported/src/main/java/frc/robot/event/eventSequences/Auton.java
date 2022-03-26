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

                new Event(          DrivetrainSubsystem.getInstance()::resetGyroscope),

                new Event(          ShootSubsystem.getInstance()::lower),
                new Event(          PneumaticSubsystem.getInstance()::iTSFwd),

                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.15, 0), 0, true)),
                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, true), 2000),


                new Event(          ShootSubsystem.getInstance()::shootOn),
                new Event(          ShootSubsystem.getInstance()::indexOn, 2000),

                new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.4), 3250),
                
                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.1, 0), 0, true)),
                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, true), 750),


                new Event(          ShootSubsystem.getInstance()::shootOff, 5250),
                new Event(          ShootSubsystem.getInstance()::indexOff),
                new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.0), 0),
                


        });
    }
    
} 
