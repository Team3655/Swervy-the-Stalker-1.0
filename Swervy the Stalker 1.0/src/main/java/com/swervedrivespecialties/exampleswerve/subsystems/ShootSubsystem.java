package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANSparkMax;
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

  private CANSparkMax indexMotor = new CANSparkMax(RobotMap.INDEXER, MotorType.kBrushless);
  private CANSparkMax shootBtmMotor = new CANSparkMax(RobotMap.SHOOTER_BOTTOM, MotorType.kBrushless);
  private CANSparkMax shootTopMotor = new CANSparkMax(RobotMap.SHOOTER_TOP, MotorType.kBrushless);

  //--Make a PID Loop Controller here
   PIDController pidTop = new PIDController(.1, 2, 0);
   PIDController pidBtm = new PIDController(.1, 2, 0);
  
  public void periodic() {
    pidTop.setSetpoint(0.5);
    pidBtm.setSetpoint(0.5);
    shootBtmMotor.set(pidBtm.calculate(shootBtmMotor.getEncoder().getVelocity()));
    shootTopMotor.set(pidTop.calculate(shootTopMotor.getEncoder().getVelocity())); 
  }

    public void indexOn(){
        indexMotor.set(-0.3);
    }

    public void indexOff(){
        indexMotor.set(0.0);
    }

//--Shooting Motors need to be in velocity (PID) 

    public void shootOn(){
        shootBtmMotor.set(0.1);
        shootTopMotor.set(0.1);
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