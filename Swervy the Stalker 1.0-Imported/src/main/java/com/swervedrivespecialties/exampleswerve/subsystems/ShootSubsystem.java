package com.swervedrivespecialties.exampleswerve.subsystems;

import javax.xml.namespace.QName;

import com.ctre.phoenix.motorcontrol.IFollower;
import com.revrobotics.CANSparkMax;


import edu.wpi.first.wpilibj.TimedRobot;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.event.PrintEvent;
import gameutil.math.geom.Point;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import com.swervedrivespecialties.exampleswerve.subsystems.IntakeSubsystems;

public class ShootSubsystem {

  private static ShootSubsystem instance;
  private Joystick  secondaryJoystick = new Joystick(2);
  private static IntakeSubsystems itake;

  private CANSparkMax sLift = new CANSparkMax(RobotMap.ELEVATION, MotorType.kBrushless);
  private RelativeEncoder sLifte = sLift.getEncoder();
  private double sLiftPos = sLifte.getPosition();

  private CANSparkMax indexMotor = new CANSparkMax(RobotMap.INDEXER, MotorType.kBrushless);
  private CANSparkMax shootBtmMotor = new CANSparkMax(RobotMap.SHOOTER_BOTTOM, MotorType.kBrushless);
  private CANSparkMax shootTopMotor = new CANSparkMax(RobotMap.SHOOTER_TOP, MotorType.kBrushless);
  private SparkMaxPIDController topPidController = shootTopMotor.getPIDController();
  private SparkMaxPIDController btmPidController = shootBtmMotor.getPIDController();
  private RelativeEncoder topEncoder = shootTopMotor.getEncoder();
  private RelativeEncoder btmEncoder = shootBtmMotor.getEncoder();
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, topSpeed,botSpeed;

  public ShootSubsystem() {

    itake = IntakeSubsystems.getInstance();

    // sLift init
    sLift.restoreFactoryDefaults();

    // shoot init
    shootTopMotor.restoreFactoryDefaults();
    shootBtmMotor.restoreFactoryDefaults();
    topEncoder = sLift.getEncoder();
    btmEncoder = sLift.getEncoder();

    kP = 0.000002; 
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

    topSpeed=Robot.getRobot().getTuningValue("shootSpeedTop");
    botSpeed=Robot.getRobot().getTuningValue("shootSpeedBot");
  }
  
  
  //--Make a PID Loop Controller here
   PIDController pidTop = new PIDController(.1, 2, 0);
   PIDController pidBtm = new PIDController(.1, 2, 0);

  public void periodic() {

    // sLift periodic
    sLift.getEncoder(); 
    sLifte.getPosition();
    sLiftPos = sLifte.getPosition();

    SmartDashboard.putNumber("Elevator Position", sLifte.getPosition());
    SmartDashboard.putNumber("Encoder Velocity", sLifte.getVelocity());
  
    

    //Makes ELevator move w/ limits.

    
    
    


    // index periodic 
    if (secondaryJoystick.getRawButton(9)) {
      indexOn();
      itake.iTakeFWD(.2);
    } else {
      indexOff();
    }

    // shoot periodic 
    if (secondaryJoystick.getRawButton(5)) {
      
      topPidController.setReference(-topSpeed*maxRPM, CANSparkMax.ControlType.kVelocity);
      btmPidController.setReference(botSpeed*maxRPM, CANSparkMax.ControlType.kVelocity);

      //
      //Robot.eHandler.triggerEvent(new PrintEvent(topEncoder.getVelocity()));
    } else if (Robot.getRobot().isTeleopEnabled()){
      topPidController.setReference(0, CANSparkMax.ControlType.kDutyCycle);
      btmPidController.setReference(0, CANSparkMax.ControlType.kDutyCycle);
    }

    
    //SmartDashboard.putNumber("Setpoint", setPoint);
        
    //SmartDashboard.putNumber("Top V", topEncoder.getVelocity());
    //SmartDashboard.putNumber("Btm V", btmEncoder.getVelocity());

  }
  
  //Raise and Lower Elevator
  public void lower() {
    sLift.getPIDController().setP(.05);
    sLift.getPIDController().setReference(134, ControlType.kPosition);
  } 

  public double getSSpeed () {
    return (topEncoder.getVelocity() + btmEncoder.getVelocity())/2;
  }

  public void raise() {
    sLift.getPIDController().setP(.06);
    sLift.getPIDController().setReference(2, ControlType.kPosition);
  }

  //Elevator Up and Down when Button Held Down
  
  public void elevatorUp(){
    sLift.set(-0.3);
  if(sLiftPos >= 131){
    sLift.set(0);
  }else{
    sLift.set(-.3);
    }
  }

  public void elevatorDown(){
    sLift.set(0.3);
    if(sLiftPos <= 2){
      sLift.set(0);
    }else{
      sLift.set(.3);
    }
  }

  public void elevatorStop(){
    sLift.set(0);
  }

    //Index On/Off
    public void indexOn(){
        indexMotor.set(0.6);
    }

    public void indexOff(){
        indexMotor.set(0.0);
    }
    //Shooting
    public void shootOn(){
      
      topPidController.setReference(-topSpeed*maxRPM, CANSparkMax.ControlType.kVelocity);
      btmPidController.setReference(botSpeed*maxRPM, CANSparkMax.ControlType.kVelocity);
    }
    
    public void shootOff(){
      shootBtmMotor.set(0);
      shootTopMotor.set(0);
    }

    public void setTopSpeed(double d){
      topSpeed=d;
    }

    public void setBotSpeed(double d){
      botSpeed=d;
    }

    public void setSpeed(Point p){
      topSpeed=p.tuple.i(0);
      botSpeed=p.tuple.i(1);
    }

    public static ShootSubsystem getInstance() {
      if (instance == null) {
          instance = new ShootSubsystem();
          }
          return instance;   
      }
  
  }