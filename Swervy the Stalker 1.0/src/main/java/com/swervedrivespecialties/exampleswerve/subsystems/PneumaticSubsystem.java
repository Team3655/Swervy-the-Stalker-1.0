package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.swervedrivespecialties.exampleswerve.OI;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class PneumaticSubsystem {
  private final PneumaticsControlModule pcm = new PneumaticsControlModule();
  private final DoubleSolenoid grip1Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP1SOL, RobotMap.ID_CLOSEGRIP1SOL);
  private final DoubleSolenoid grip2Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP2SOL, RobotMap.ID_CLOSEGRIP2SOL);
  private static Compressor compressor=new Compressor(10, PneumaticsModuleType.CTREPCM);

  private static PneumaticSubsystem instance;

  private Joystick  secondaryJoystick = new Joystick(1);

  public PneumaticSubsystem() {
    //System.out.println(grip1Solenoid.get());
    grip1Solenoid.set(Value.kForward);
    
  }
   
  public static PneumaticSubsystem getInstance() {
    if (instance == null) {
        instance = new PneumaticSubsystem();
    }

    return instance;   
    }
    
    

  
  public void periodic() {
      // This method will be called once per scheduler run
      //Solenoid Toggle
      if (secondaryJoystick.getRawButtonPressed(1)){
        grip1Solenoid.toggle();
        System.out.println(grip1Solenoid.get());
          }

      if (secondaryJoystick.getRawButtonPressed(2)){
        grip2Solenoid.toggle();
        System.out.println(grip1Solenoid.get());
      }

      if(secondaryJoystick.getRawButtonPressed(5)){
          if(compressor.enabled()){
            compressor.disable();
          } else {
            compressor.enableDigital();
          }
      }
    }
  

  public void closeGrip1Solenoid() {
    grip1Solenoid.set(Value.kForward);
  }

  public void openGrip1Solenoid() {
    grip1Solenoid.set(Value.kReverse);
  }

  public void closeGrip2Solenoid() {
    grip2Solenoid.set(Value.kForward);
  }

  public void openGrip2Solenoid() {
    grip2Solenoid.set(Value.kReverse);
  }

      //  boolean enabled = compressor.enabled();
          
 

  }

