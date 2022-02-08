package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class PneumaticSubsystem extends Subsystem {
  private final PneumaticsControlModule pcm = new PneumaticsControlModule();
  private final DoubleSolenoid grip1Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP1SOL, RobotMap.ID_CLOSEGRIP1SOL);
  private final DoubleSolenoid grip2Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP2SOL, RobotMap.ID_CLOSEGRIP2SOL);
 
  private static PneumaticSubsystem instance;

  public static PneumaticSubsystem getInstance() {
    if (instance == null) {
        instance = new PneumaticSubsystem();
    }

    return instance;   
    } 
    
    public PneumaticSubsystem() {
  
  
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
   
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

  protected void initDefaultCommand() {
    
  }

}
