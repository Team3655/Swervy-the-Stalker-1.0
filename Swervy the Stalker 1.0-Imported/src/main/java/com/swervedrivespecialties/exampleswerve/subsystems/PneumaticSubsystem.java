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

  //private final Solenoid intakeSolenoid = pcm.makeSolenoid(RobotMap.ID_EXTENDINTAKE);
  private static Compressor compressor=new Compressor(RobotMap.ID_PCM, PneumaticsModuleType.CTREPCM);

  private static PneumaticSubsystem instance;

  private Joystick  primaryJoystick = new Joystick(0);
  private Joystick  secondaryJoystick = new Joystick(1);
  
  private static boolean iTakeStatus = false;
  private static boolean compressorEnabled = false;



  public void PneumaticSubsystem() {
    compressor.disable();
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

      

      if (secondaryJoystick.getRawButtonPressed(4)){
        //intakeSolenoid.toggle();
        iTakeStatus = !iTakeStatus; 
      }


      if(secondaryJoystick.getRawButtonPressed(8)){ 
        compressorEnabled = !compressorEnabled;
        if(compressorEnabled){
          compressor.enableDigital();
        } else {
          compressor.disable();
        }
      }

      SmartDashboard.putBoolean("Comp Enabled", compressorEnabled);

    }
          
    public static boolean getItakeStatus() {
      return iTakeStatus;
    }

}

