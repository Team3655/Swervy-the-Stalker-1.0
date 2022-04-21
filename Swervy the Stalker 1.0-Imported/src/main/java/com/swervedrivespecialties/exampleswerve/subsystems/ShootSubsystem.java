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
import frc.robot.event.Event;
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
  private boolean updateSpeedWithTuningValues=true;
  private CANSparkMax indexMotor = new CANSparkMax(RobotMap.INDEXER, MotorType.kBrushless);
  private CANSparkMax shootBtmMotor = new CANSparkMax(RobotMap.SHOOTER_BOTTOM, MotorType.kBrushless);
  private CANSparkMax shootTopMotor = new CANSparkMax(RobotMap.SHOOTER_TOP, MotorType.kBrushless);
  private SparkMaxPIDController topPidController = shootTopMotor.getPIDController();
  private SparkMaxPIDController btmPidController = shootBtmMotor.getPIDController();
  private RelativeEncoder topEncoder = shootTopMotor.getEncoder();
  private RelativeEncoder btmEncoder = shootBtmMotor.getEncoder();
  
  //
 // Event indexEvent=new Event();
  //

  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, topSpeed,botSpeed;

  public ShootSubsystem() {
    
    //
   // indexEvent.terminate();
    //

    itake = IntakeSubsystems.getInstance();

    // sLift init
    sLift.restoreFactoryDefaults();

    // shoot init
    shootTopMotor.restoreFactoryDefaults();
    shootBtmMotor.restoreFactoryDefaults();
    topEncoder = shootTopMotor.getEncoder();
    btmEncoder = shootBtmMotor.getEncoder();

    kP = 0.000004; 
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

    topSpeed = Robot.getRobot().getTuningValue("shootSpeedTop");
    botSpeed = Robot.getRobot().getTuningValue("shootSpeedBot");
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

      //
     // indexEvent.kill();
      //

      indexOn();
      itake.iTakeFWD(.2);
    } else {
      indexOn(0);
    }

    // shoot periodic 
    topEncoder = shootTopMotor.getEncoder();
    btmEncoder = shootBtmMotor.getEncoder();

    if (updateSpeedWithTuningValues){
      doTuningValueUpdating();
    }
    if (secondaryJoystick.getRawButton(5)) {
      
      topPidController.setReference(-topSpeed*maxRPM, CANSparkMax.ControlType.kVelocity);
      btmPidController.setReference(botSpeed*maxRPM, CANSparkMax.ControlType.kVelocity);

      /*/
      if (Robot.limelight.targetLocked()&&atSpeed()){
       // indexEvent.kill();
        indexOn();
      } else if (!secondaryJoystick.getRawButton(9)){
        if (indexEvent.taskDone()){
          indexEvent=new Event(this::indexOff,500);
          Robot.eHandler.triggerEvent(indexEvent);
        } 
      }
      /*/

      //Robot.eHandler.triggerEvent(new PrintEvent(topEncoder.getVelocity()));
    } else if (Robot.getRobot().isTeleopEnabled()){
      topPidController.setReference(0, CANSparkMax.ControlType.kDutyCycle);
      btmPidController.setReference(0, CANSparkMax.ControlType.kDutyCycle);

      /*/
      if (!secondaryJoystick.getRawButton(9)){
        indexOff();
      }
      /*/
    }

    
    
    double atSpeedT = -topSpeed *maxRPM *.9;
    double atSpeedB = botSpeed *maxRPM *.9;

    SmartDashboard.putNumber("top Speed", topEncoder.getVelocity());
    SmartDashboard.putNumber("btm Speed", btmEncoder.getVelocity());
    SmartDashboard.putNumber("atSpeed target top", atSpeedT);
    SmartDashboard.putNumber("atSpeed target top", atSpeedB);

    if (Math.abs(atSpeedT - topEncoder.getVelocity()) < Math.abs(atSpeedT *.1) && Math.abs(atSpeedB - btmEncoder.getVelocity()) < Math.abs(atSpeedB *.1)) {
      SmartDashboard.putBoolean("atSpeed", true);
    } else {
      SmartDashboard.putBoolean("atSpeed", false);
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

  /*/
  public boolean atSpeed(){
    double atSpeedT = -topSpeed *maxRPM *.9;
    double atSpeedB = botSpeed *maxRPM *.9;

    if (Math.abs(atSpeedT - topEncoder.getVelocity()) < Math.abs(atSpeedT *.1) && Math.abs(atSpeedB - btmEncoder.getVelocity()) < Math.abs(atSpeedB *.1)) {
      return true;
    } else {
      return false;
    }
  }
  /*/

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
    public void indexOn( double s){
        indexMotor.set(s);
    }

    public void indexOn(){
      indexOn(.6);
    }

    public void indexOff(){
        indexMotor.set(0.0);
    }
    //Shooting
    public void shootOn(){
      
      topPidController.setReference(-topSpeed*maxRPM, CANSparkMax.ControlType.kVelocity);
      btmPidController.setReference(botSpeed*maxRPM, CANSparkMax.ControlType.kVelocity);
    }

    public void shootOn(double s){
      
      topPidController.setReference(-s*maxRPM, CANSparkMax.ControlType.kVelocity);
      btmPidController.setReference(s*maxRPM, CANSparkMax.ControlType.kVelocity);
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

    public void setEnabledTuningValueUpating(boolean b) {
      updateSpeedWithTuningValues=b;
    }

    public void doTuningValueUpdating(){
      setTopSpeed(Robot.getRobot().getTuningValue("shootSpeedTop"));
      setBotSpeed(Robot.getRobot().getTuningValue("shootSpeedBot"));
    }
  
  }