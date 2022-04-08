package frc.robot.event.eventSequences;


import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.IntakeSubsystems;
import com.swervedrivespecialties.exampleswerve.subsystems.PneumaticSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.ShootSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.DoubleConsumer;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.event.DriveEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.LimelightEvent;
import frc.robot.event.TurnEvent;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Limelight;
import frc.robot.buttons.JSBAdapter;
import frc.robot.buttons.TSBAdapter;
import frc.robot.buttons.TSBAdapter.Mode;
import frc.robot.event.EventHandler;
import frc.robot.event.eventSequences.Auton;


public class Auton extends EventSequence{

    public enum AUTON_ALIAS {shootAndGrab};
    public static final Limelight limelight=new Limelight();

    public Auton(){
        this(AUTON_ALIAS.values()[(int)Math.floor(Robot.getRobot().getTuningValue("autonAlias"))]);
        
    }

    public Auton(AUTON_ALIAS alias) {
        super(
            new Event[] {

                //new Event(          DrivetrainSubsystem.getInstance()::resetGyroscope),

                new Event(          ShootSubsystem.getInstance()::lower, (int) (Robot.getRobot().getTuningValue("Auto Delay"))),

                new Event(          ShootSubsystem.getInstance()::lower),
                new Event(          PneumaticSubsystem.getInstance()::iTSFwd),

                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.15, 0), 0, false)),
                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 2000),

                new Event(          Robot.limelight::enable),

                new Event(          ShootSubsystem.getInstance()::shootOn),
                new Event(          ShootSubsystem.getInstance()::indexOn, 2000),

                new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.4), 3250),
                
                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.1, 0), 0, false)),
                new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 850),


                new Event(          ShootSubsystem.getInstance()::shootOff, 5250),
                new Event(          ShootSubsystem.getInstance()::indexOff),
                new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.0), 0),
                new Event(          Robot.limelight::disable),
                


        });
    }
    
} 
