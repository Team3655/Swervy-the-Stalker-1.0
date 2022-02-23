package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootSubsystem {
  public ShootSubsystem() {}
  private static ShootSubsystem instance;
  private Joystick  secondaryJoystick = new Joystick(1);

  private CANSparkMax sLift = new CANSparkMax(RobotMap.ELEVATION, MotorType.kBrushless);
  private CANSparkMax indexMotor = new CANSparkMax(RobotMap.INDEXER, MotorType.kBrushless);
  private CANSparkMax shootBtmMotor = new CANSparkMax(RobotMap.SHOOTER_BOTTOM, MotorType.kBrushless);
  private CANSparkMax shootTopMotor = new CANSparkMax(RobotMap.SHOOTER_TOP, MotorType.kBrushless);
  
  
  //--Make a PID Loop Controller here
   PIDController pidTop = new PIDController(.1, 2, 0);
   PIDController pidBtm = new PIDController(.1, 2, 0);
  
   //PID Control for Elevator
   private SparkMaxPIDController EPid;

  public void periodic() {
   /* pidTop.setSetpoint(-0.8);
    pidBtm.setSetpoint(0.8);
    shootBtmMotor.set(pidBtm.calculate(shootBtmMotor.getEncoder().getVelocity()));
    shootTopMotor.set(pidTop.calculate(shootTopMotor.getEncoder().getVelocity())); */
  }

  /*public double Elevator(CANSparkMax sLift){
    this.sLift=sLift;
    EPid=this.sLift.getPIDController();
    EPid.setP(1);
    EPid.setI(0);
    EPid.setD(0);
    EPid.setOutputRange(-0.3, 0.3);
    return EPid();
  }*/

 /* private void setPos(double sLift){
    if (sLift<-13){
        sLift=-13;
    } else if (sLift>0){
        sLift=0;
    }
    EPid.setReference(sLift, ControlType.kPosition);
  } */
 

  //Elevator
    public void sLiftMove(){
      while (secondaryJoystick.getRawButton(1)) {
        sLift.set(-0.3);
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