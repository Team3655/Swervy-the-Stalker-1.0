package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import edu.wpi.first.wpilibj.Joystick;
import org.frcteam2910.common.robot.Utilities;


public class IntakeSubsystems{

  private static IntakeSubsystems instance;
  private Joystick primaryJoystick = new Joystick(0);
  private CANSparkMax intakeMotor = new CANSparkMax(RobotMap.INTAKE, MotorType.kBrushless); 
  boolean iTakeOff = false;

  public void periodic() {
    
    // variable intake
    double intake = primaryJoystick.getRawAxis(2)*.75;
    // Square the forward stick
    intake = Math.copySign(Math.pow(intake, 2.0), intake);

    double outtake = primaryJoystick.getRawAxis(3)*.75;
    outtake = Utilities.deadband(outtake);
    // Square the forward stick
    outtake = Math.copySign(Math.pow(outtake, 2.0), outtake);

    intakeMotor.set((outtake - intake)/1.25);
  }   

    public void iTakeFWD(double s){
        intakeMotor.set(-s);
    }

    public void iTakeOff(){
        intakeMotor.set(0);
    }

    public static IntakeSubsystems getInstance() {
        if (instance == null) {
            instance = new IntakeSubsystems();
        }
        return instance;   
        }
}