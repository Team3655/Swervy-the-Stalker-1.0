package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShootSubsystem extends Subsystem {
  /** Creates a new ExampleSubsystem. */
  public ShootSubsystem() {}

  private static ShootSubsystem instance;
  private Joystick  secondaryJoystick = new Joystick(1);

  private CANSparkMax indexMotor = new CANSparkMax(14, MotorType.kBrushless);
  private CANSparkMax shootBtmMotor = new CANSparkMax(16, MotorType.kBrushed);
  private CANSparkMax shootTopMotor = new CANSparkMax(17, MotorType.kBrushed);

  //--Make a PID Loop Controller here
  PIDController pid = new PIDController(/*kP*/, /*kI*/, /*kD*/);

  public void periodic() {
    //This method will be called once per scheduler run
  }

  public static ShootSubsystem getInstance() {
    if (instance == null) {
        instance = new ShootSubsystem();
        }
        return instance;   
    }

    protected void initDefaultCommand() { 
    }

    public void indexOn(){
        indexMotor.set(0.3);
    }

    public void indexOff(){
        indexMotor.set(0.0);
    }

//--Shooting Motors need to be in velocity (PID) 

   /* public void ShootOn(){
        shootBtmMotor.set();
    }
      */ 

}