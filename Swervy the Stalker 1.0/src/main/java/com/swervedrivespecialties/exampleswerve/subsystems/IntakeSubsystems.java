package com.swervedrivespecialties.exampleswerve.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Subsystem;



public class IntakeSubsystems{
  public IntakeSubsystems() {}

  private static IntakeSubsystems instance;
  private Joystick  secondaryJoystick = new Joystick(1);

  private CANSparkMax intakeMotor = new CANSparkMax(13, MotorType.kBrushless);


  public void periodic() {
    // This method will be called once per scheduler run
   /* if(secondaryJoystick.getRawButtonPressed(5)){
        intakeMotor.set(0.3);
    } else {
        intakeMotor.set(0);
    }   */
    
  /*  if(secondaryJoystick.getRawButtonPressed(6)){
        intakeMotor.set(-0.3);
    } else {
        intakeMotor.set(0);
    } */
  }   




    public void itakeFWD(){
        intakeMotor.set(0.3);
    }

    public void itakeBWD(){
        intakeMotor.set(-0.3);
    }

    public static IntakeSubsystems getInstance() {
        if (instance == null) {
            instance = new IntakeSubsystems();
        }

        return instance;   
        }
}