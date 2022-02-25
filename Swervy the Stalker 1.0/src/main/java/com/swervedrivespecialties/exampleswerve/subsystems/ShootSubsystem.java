package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.TimedRobot;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import com.swervedrivespecialties.exampleswerve.subsystems.IntakeSubsystems;

public class ShootSubsystem {

  private static ShootSubsystem instance;
  private Joystick  secondaryJoystick = new Joystick(1);
  private static IntakeSubsystems itake;

  private CANSparkMax sLift = new CANSparkMax(RobotMap.ELEVATION, MotorType.kBrushless);
  private RelativeEncoder sLifte;
  private CANSparkMax indexMotor = new CANSparkMax(RobotMap.INDEXER, MotorType.kBrushless);
  private CANSparkMax shootBtmMotor = new CANSparkMax(RobotMap.SHOOTER_BOTTOM, MotorType.kBrushless);
  private CANSparkMax shootTopMotor = new CANSparkMax(RobotMap.SHOOTER_TOP, MotorType.kBrushless);
  private SparkMaxPIDController topPidController = shootTopMotor.getPIDController();
  private SparkMaxPIDController btmPidController = shootBtmMotor.getPIDController();
  private RelativeEncoder topEncoder = shootTopMotor.getEncoder();
  private RelativeEncoder btmEncoder = shootBtmMotor.getEncoder();
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

  public ShootSubsystem() {

    itake = IntakeSubsystems.getInstance();

    // sLift init
    sLift.restoreFactoryDefaults();
    sLifte = sLift.getEncoder();

    // shoot init
    shootTopMotor.restoreFactoryDefaults();
    shootBtmMotor.restoreFactoryDefaults();
    topEncoder = sLift.getEncoder();
    btmEncoder = sLift.getEncoder();

    kP = 0.0000015; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000165; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5200;

    topPidController.setP(kP);
    topPidController.setI(kI);
    topPidController.setD(kD);
    topPidController.setIZone(kIz);
    topPidController.setFF(kFF);
    topPidController.setOutputRange(kMinOutput, kMaxOutput);

    btmPidController.setP(kP);
    btmPidController.setI(kI);
    btmPidController.setD(kD);
    btmPidController.setIZone(kIz);
    btmPidController.setFF(kFF);
    btmPidController.setOutputRange(kMinOutput, kMaxOutput);
  }
  
  
  //--Make a PID Loop Controller here
   PIDController pidTop = new PIDController(.1, 2, 0);
   PIDController pidBtm = new PIDController(.1, 2, 0);

  public void periodic() {

    // sLift periodic 
    while (secondaryJoystick.getRawButton(1)) {
      sLift.set(.3);
    }

    while (secondaryJoystick.getRawButton(2)) {
      sLift.set(-.3);
    }


    // index periodic 
    if (secondaryJoystick.getRawButton(6)) {
      indexOn();
      itake.iTakeFWD(.05);
    } else {
      indexOff();
    }

    sLift.set(0);
    SmartDashboard.putNumber("Encoder Position", sLifte.getPosition());
    SmartDashboard.putNumber("Encoder Velocity", sLifte.getVelocity());

    // shoot periodic 
    int speed = 0;
    if (secondaryJoystick.getRawButton(5)) {
      speed = -1;
    }

    double setPoint = speed*maxRPM;
    topPidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
    btmPidController.setReference(-setPoint, CANSparkMax.ControlType.kVelocity);
    SmartDashboard.putNumber("Setpoint", setPoint);
        
    SmartDashboard.putNumber("Top V", topEncoder.getVelocity());
    SmartDashboard.putNumber("Btm V", btmEncoder.getVelocity());
  }

  //Elevator
    public void sLiftUp(){
      while (secondaryJoystick.getRawButton(2)) {
        sLift.set(-0.3);
      } 
      sLift.set(0);
    }

    public void sLiftDown(){
      while (secondaryJoystick.getRawButton(1)) {
        sLift.set(0.3);
      } 
      sLift.set(0);
    }

    public void indexOn(){
        indexMotor.set(0.6);
    }

    public void indexOff(){
        indexMotor.set(0.0);
    }
//--Shooting Motors need to be in velocity (PID) 
    public void shootOn(){
      shootTopMotor.set(-1);
      shootBtmMotor.set(1);
        
    }
    public void shootOff(){
      shootBtmMotor.set(0);
      shootTopMotor.set(0);
    }

    public static ShootSubsystem getInstance() {
      if (instance == null) {
          instance = new ShootSubsystem();
          }
          return instance;   
      }
  
  }