package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.swervedrivespecialties.exampleswerve.OI;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class PneumaticSubsystem {
  private final PneumaticsControlModule pcm = new PneumaticsControlModule(RobotMap.ID_PCM);
  private final DoubleSolenoid grip1Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP1SOL, RobotMap.ID_CLOSEGRIP1SOL);
  private final DoubleSolenoid grip2Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP2SOL, RobotMap.ID_CLOSEGRIP2SOL);
  private final Solenoid intakeSolenoid = pcm.makeSolenoid(RobotMap.ID_EXTENDINTAKE);
  private static Compressor compressor=new Compressor(RobotMap.ID_PCM, PneumaticsModuleType.CTREPCM);

  private static PneumaticSubsystem instance;

  private Joystick  primaryJoystick = new Joystick(0);
  private Joystick  secondaryJoystick = new Joystick(1);

  private static boolean iTakeStatus = false;

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
      //This method will be called once per scheduler run
      //Solenoid Toggle
      if (secondaryJoystick.getRawButtonPressed(1)){
        grip1Solenoid.toggle();
        System.out.println(grip1Solenoid.get());
          }

      if (secondaryJoystick.getRawButtonPressed(2)){
        grip2Solenoid.toggle();
        System.out.println(grip1Solenoid.get());
      }

      if (secondaryJoystick.getRawButtonPressed(4)){
        intakeSolenoid.toggle();
        iTakeStatus = !iTakeStatus; 
      }

      if(secondaryJoystick.getRawButtonPressed(5)){
          if(compressor.enabled()){
            compressor.disable();
          } else {
            compressor.enableDigital();
          }
      }
    }
  
      //  boolean enabled = compressor.enabled();
          
    public static boolean getItakeStatus() {
      return iTakeStatus;
    }

}

