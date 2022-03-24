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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PneumaticSubsystem {
  private final PneumaticsControlModule pcm = new PneumaticsControlModule(RobotMap.ID_PCM);

  private final DoubleSolenoid intakeSolenoid = pcm.makeDoubleSolenoid(RobotMap.ID_EXTENDINTAKE, RobotMap.ID_RETURNINTAKE);
  private static Compressor compressor=new Compressor(RobotMap.ID_PCM, PneumaticsModuleType.CTREPCM);

  private static PneumaticSubsystem instance;

  private Joystick  primaryJoystick = new Joystick(0);
  private Joystick  secondaryJoystick = new Joystick(2);
  
  private static boolean iTakeStatus = false;



  public void PneumaticSubsystem() {
    compressor.enableDigital();
    //compressor.disable();
    intakeSolenoid.set(Value.kReverse);
  }
   
  public static PneumaticSubsystem getInstance() {
    if (instance == null) {
        instance = new PneumaticSubsystem();
    }

    return instance;   
    }
  
  public void periodic() {
      //This method will be called once per scheduler run
      //Solenoid Toggle/

      
    
      /*if (secondaryJoystick.getRawButtonPressed(4)){
        intakeSolenoid.toggle();
        iTakeStatus = !iTakeStatus; 
      }*/


      if(secondaryJoystick.getRawButtonPressed(21)){ 
        toggleCompressor();
      }

      SmartDashboard.putBoolean("Comp Enabled", compressor.enabled());

    }
          
    public static boolean getItakeStatus() {
      return iTakeStatus;
    }

    public void toggleIntakeSolenoid(){
      intakeSolenoid.toggle();
    }

    public void toggleCompressor(){
        if(compressor.enabled()){
          compressor.disable();
        } else {
          compressor.enableDigital();
        }
    }

}

