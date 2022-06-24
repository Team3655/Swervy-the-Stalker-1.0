package frc.robot.event.eventSequences;


import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.commands.DriveCommand;
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
import gameutil.math.geom.Point;
import gameutil.math.geom.Tuple;


public class Auton extends EventSequence{

    public enum AUTON_ALIAS {shootAndGrab, shootTwoAtATime, threeBallLeft, threeBallright, turnEventTest, autonFixedSpeed, autonBackFar, shootTest,path};
    public static final Limelight limelight=new Limelight();

    public Auton(){
        this(AUTON_ALIAS.values()[(int)Math.floor(Robot.getRobot().getTuningValue("autonAlias"))]);
        
    }

    public Auton(AUTON_ALIAS alias) {
        super(Auton.getAuton(alias));
    }

    private static Event[] getAuton(AUTON_ALIAS alias){
        switch(alias){
            case shootAndGrab:
                return new Event[] {
                    
                    //Start Up
                    new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                    new Event(          ShootSubsystem.getInstance()::lower, (int) (Robot.getRobot().getTuningValue("Auto Delay"))),
                    
                    new Event(          ShootSubsystem.getInstance()::lower),
                    
                    //Turn Intake On and drive forwards
                    new Event(          PneumaticSubsystem.getInstance()::iTSFwd),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.15, 0), 0, false)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 2000),
                    
                    //Turn limelight on and start targeting
                    new Event(          Robot.limelight::enable),
                    new Event (() ->    ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(true)),
    
                    //Shoot 1st Cargo
                    new Event(() ->     limelight.forceUpdateSpeed()),
                    new Event(          ShootSubsystem.getInstance()::shootOn),
                    new Event(          ShootSubsystem.getInstance()::indexOn, 2000),
                    new Event(          ShootSubsystem.getInstance()::indexOff,3250),

                    //Stop Shooting and move to 2nd Cargo
                    new Event(          Robot.limelight::disable),
                    new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.4)),
                    new Event (() ->    ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.1, 0), 0, false)),
    
                    //Shoot 2nd Cargo
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 850),
                    new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(true)),
                    new Event(          Robot.limelight::enable),
                    new Event(          ShootSubsystem.getInstance()::indexOn, 1500),
    
                    //Clean Up
                    new Event(          ShootSubsystem.getInstance()::shootOff, 3750),
                    new Event(          ShootSubsystem.getInstance()::indexOff),
                    new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.0), 0),
                    new Event(          Robot.limelight::disable),
                    new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                    
    
            };

            
            case shootTwoAtATime:
                return new Event[] {   

                    new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                    new Event(          ShootSubsystem.getInstance()::lower, (int) (Robot.getRobot().getTuningValue("Auto Delay"))),
    
                    new Event(          ShootSubsystem.getInstance()::lower),
                    new Event(          PneumaticSubsystem.getInstance()::iTSFwd),
                    
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.15, 0), 0, false)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 2000),
                    
                    new Event(          Robot.limelight::enable),
                    new Event (() ->    ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(true)),
    
                    new Event(          ShootSubsystem.getInstance()::shootOn),

                    new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.4), 3250),
                    
                    
                    new Event (() ->    ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.1, 0), 0, false)),
    
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 850),
                    new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(true)),
                    new Event(() ->     limelight.forceUpdateSpeed()),
                    new Event(          ShootSubsystem.getInstance()::indexOn, 750),
    
    
                    new Event(          ShootSubsystem.getInstance()::shootOff, 4750),
                    new Event(          ShootSubsystem.getInstance()::indexOff),
                    new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.0), 0),
                    new Event(          Robot.limelight::disable),
                    new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                    
    
            };


            case turnEventTest:
                return new Event[]{
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), .45, false)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 450),
                };


            case threeBallright:
                return new Event[]{
                        //Start Up
                        new Event(          Robot.limelight::disable),
                        new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                        new Event(          ShootSubsystem.getInstance()::lower, (int) (Robot.getRobot().getTuningValue("Auto Delay"))),
                        
                        new Event(          ShootSubsystem.getInstance()::lower),
                        
                        //Turn Intake On and drive forwards
                        new Event(          PneumaticSubsystem.getInstance()::iTSFwd),
                        new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.15, 0), 0, false)),
                        new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 2000),
                        
        
                        //Shoot 1st Cargo
                        new Event(() ->     ShootSubsystem.getInstance().setSpeed(new Point(new Tuple(new double[]{.52,.64})))),
                        new Event(          ShootSubsystem.getInstance()::shootOn),
                        new Event(          ShootSubsystem.getInstance()::indexOn, 2000),
                        new Event(          ShootSubsystem.getInstance()::indexOff,3250),
                        //start going to speed for next shot
                        new Event(() ->     ShootSubsystem.getInstance().setSpeed(new Point(new Tuple(new double[]{.53,.65})))),
                        //Stop Shooting and move to 2nd Cargo
                        
                        new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.4)),
                        new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.1, 0), 0, false)),
        
                        //Shoot 2nd Cargo
                        new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 850),
                        
                        new Event(          ShootSubsystem.getInstance()::indexOn, 1500),
        
                    //Clean Up
                    
                    new Event(          Robot.limelight::disable,1500),
                    new Event(          ShootSubsystem.getInstance()::shootOff),
                    new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                    new Event(() ->     ShootSubsystem.getInstance().setSpeed(new Point(new Tuple(new double[]{0.2,.2})))),
                    
                    //rotate counter-clockwise 100 degrees and go forward 60"
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), .45, false)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.2, 0), 0, false), 550),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 1750),
                    
                    //Spit out Opposing Ball
                    new Event(          ShootSubsystem.getInstance()::shootOn),
                    new Event(          ShootSubsystem.getInstance()::indexOn, 465),
                    new Event(          ShootSubsystem.getInstance()::shootOff, 1750),
                    new Event(          ShootSubsystem.getInstance()::indexOff),
                    new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.0), 0),
                };


            case threeBallLeft:
                return new Event[]{//Start Up
                        //Start Up
                        new Event(          Robot.limelight::disable),
                        new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                        new Event(          ShootSubsystem.getInstance()::lower, (int) (Robot.getRobot().getTuningValue("Auto Delay"))),
                        
                        new Event(          ShootSubsystem.getInstance()::lower),
                        
                        //Turn Intake On and drive forwards
                        new Event(          PneumaticSubsystem.getInstance()::iTSFwd),
                        new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.15, 0), 0, false)),
                        new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 2000),
                        
        
                        //Shoot 1st Cargo
                        new Event(() ->     ShootSubsystem.getInstance().setSpeed(new Point(new Tuple(new double[]{.52,.64})))),
                        new Event(          ShootSubsystem.getInstance()::shootOn),
                        new Event(          ShootSubsystem.getInstance()::indexOn, 2000),
                        new Event(          ShootSubsystem.getInstance()::indexOff,3250),
                        //start going to speed for next shot
                        new Event(() ->     ShootSubsystem.getInstance().setSpeed(new Point(new Tuple(new double[]{.53,.65})))),
                        //Stop Shooting and move to 2nd Cargo
                        
                        new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.4)),
                        new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.1, 0), 0, false)),
        
                        //Shoot 2nd Cargo
                        new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 850),
                        
                        new Event(          ShootSubsystem.getInstance()::indexOn, 1500),
        
                    //Clean Up
                    
                    new Event(          Robot.limelight::disable,1500),
                    new Event(          ShootSubsystem.getInstance()::shootOff),
                    new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                    new Event(() ->     ShootSubsystem.getInstance().setSpeed(new Point(new Tuple(new double[]{0.2,.2})))),
                    
                    //rotate clockwise 100 degrees and go forward 60"
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), -.45, false)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.2, 0), 0, false),465),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 1750),
                    
                    //Spit out Opposing Ball
                    new Event(          ShootSubsystem.getInstance()::shootOn),
                    new Event(          ShootSubsystem.getInstance()::indexOn, 500),
                    new Event(          ShootSubsystem.getInstance()::shootOff, 1750),
                    new Event(          ShootSubsystem.getInstance()::indexOff),
                    new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.0), 0),
                };

                case shootTest:
                    return new Event[]{
                        new Event(() ->     limelight.forceUpdateSpeed()),
                        new Event(          Robot.limelight::enable),
                        new Event(          ShootSubsystem.getInstance()::shootOn),
                        new Event(          ShootSubsystem.getInstance()::shootOff, 7500),
                    };
                case autonFixedSpeed:
                    return new Event[]{
                        //Start Up
                    new Event(          Robot.limelight::disable),
                    new Event(() ->     ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setAutoEnabled(false)),
                    new Event(          ShootSubsystem.getInstance()::lower, (int) (Robot.getRobot().getTuningValue("Auto Delay"))),
                    
                    new Event(          ShootSubsystem.getInstance()::lower),
                    
                    //Turn Intake On and drive forwards
                    new Event(          PneumaticSubsystem.getInstance()::iTSFwd),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.15, 0), 0, false)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 2000),
                    
    
                    //Shoot 1st Cargo
                    new Event(() ->     ShootSubsystem.getInstance().setSpeed(new Point(new Tuple(new double[]{.53,.65})))),
                    new Event(          ShootSubsystem.getInstance()::shootOn),
                    new Event(          ShootSubsystem.getInstance()::indexOn, 2000),
                    new Event(          ShootSubsystem.getInstance()::indexOff,3250),
                    //start going to speed for next shot
                    new Event(() ->     ShootSubsystem.getInstance().setSpeed(new Point(new Tuple(new double[]{.53,.65})))),
                    //Stop Shooting and move to 2nd Cargo
                    
                    new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.4)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.1, 0), 0, false)),
    
                    //Shoot 2nd Cargo
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 850),
                    
                    new Event(          ShootSubsystem.getInstance()::indexOn, 1500),
    
                    //Clean Up
                    new Event(          ShootSubsystem.getInstance()::shootOff, 3750),
                    new Event(          ShootSubsystem.getInstance()::indexOff),
                    new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.0), 0),
                    
    
                    };
                    case autonBackFar:
                    return new Event[]{
                    //Start Up
                    new Event(          Robot.limelight::disable), 
                    new Event(          ShootSubsystem.getInstance()::lower),
                    
                    //drive forwards and pull itake out
                    new Event(          PneumaticSubsystem.getInstance()::iTSFwd),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.15, 0), 0, false)),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 2000),
                    
    
                    //Shoot 1st Cargo
                    new Event(() ->     ShootSubsystem.getInstance().setSpeed(new Point(new Tuple(new double[]{.53,.65})))),
                    new Event(          ShootSubsystem.getInstance()::shootOn),
                    new Event(          ShootSubsystem.getInstance()::indexOn, 2000),
                    new Event(          ShootSubsystem.getInstance()::indexOff,1000),
                    new Event(          ShootSubsystem.getInstance()::shootOff),
                    
                    //Move It
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(.3, 0), 0, false), 500),
                    new Event(() ->     DrivetrainSubsystem.getInstance().drive(new Translation2d(), 0, false), 2000),

                    //Clean Up
                    new Event(() ->     IntakeSubsystems.getInstance().iTakeFWD(.0), 0),
    
                    };
                case path://for path finder testing
                    return new Event[] {
                        
                    };
        }  

        return new Event[0];
    
    

    }
    
    
} 
