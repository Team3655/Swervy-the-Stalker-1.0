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
  //public final DoubleSolenoid grip1Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP1SOL, RobotMap.ID_CLOSEGRIP1SOL);
  //public final DoubleSolenoid grip2Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP2SOL, RobotMap.ID_CLOSEGRIP2SOL);
  //public final DoubleSolenoid grip3Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP3SOL, RobotMap.ID_CLOSEGRIP3SOL);
  //public final DoubleSolenoid grip4Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP4SOL, RobotMap.ID_CLOSEGRIP4SOL);

  //private final Solenoid intakeSolenoid = pcm.makeSolenoid(RobotMap.ID_EXTENDINTAKE);
  private static Compressor compressor = new Compressor(RobotMap.ID_PCM, PneumaticsModuleType.CTREPCM);

  private static PneumaticSubsystem instance;

  private Joystick  primaryJoystick = new Joystick(0);
  private Joystick  secondaryJoystick = new Joystick(1);
  
  private static boolean iTakeStatus = false;
  private static boolean compressorEnabled = false;



  public void PneumaticSubsystem() {
    //System.out.println(grip1Solenoid.get());
    compressor.disable();
    //intakeSolenoid.toggle();
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

      

      //if (secondaryJoystick.getRawButtonPressed(3)){
      //  grip3Solenoid.toggle();
        //System.out.println(grip3Solenoid.get());
      //}
      
      //if (secondaryJoystick.getRawButtonPressed(4)){
      //  grip4Solenoid.toggle();
        //System.out.println(grip4Solenoid.get());
      //}

      /*if (secondaryJoystick.getRawButtonPressed(4)){
        intakeSolenoid.toggle();
        iTakeStatus = !iTakeStatus; 
      }*/


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

