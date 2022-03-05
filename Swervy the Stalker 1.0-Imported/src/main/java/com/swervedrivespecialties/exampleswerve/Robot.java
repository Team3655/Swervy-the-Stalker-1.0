package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.IntakeSubsystems;
import com.swervedrivespecialties.exampleswerve.subsystems.PneumaticSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.ShootSubsystem;

import java.util.Hashtable;

import com.swervedrivespecialties.exampleswerve.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.buttons.TSBAdapter;
import frc.robot.event.EventHandler;

public class Robot extends TimedRobot {
    private static OI oi;

    private static DrivetrainSubsystem drivetrain;
    private static PneumaticSubsystem pneumatic;
    private static ArmSubsystem arm;
    private static IntakeSubsystems itake;
    private static ShootSubsystem shoot;
    public static final EventHandler eHandler = new EventHandler(); 
    private Hashtable<String, Double> tuningValues=new Hashtable<>();        
    private TSBAdapter tsbAdapter;
    public static OI getOi() {
        return oi;
    }

    @Override
    public void robotInit() {
        oi = new OI();
        drivetrain = DrivetrainSubsystem.getInstance();
        pneumatic = PneumaticSubsystem.getInstance();
        itake = IntakeSubsystems.getInstance();
        shoot = ShootSubsystem.getInstance();
        arm = ArmSubsystem.getInstance();
        ArmSubsystem.getInstance().Arm_SwervyInit();
        PneumaticSubsystem.getInstance().PneumaticSubsystem();
        //Tuning Value Defaults
        tuningValues.put("drive", 1d);
        tuningValues.put("shootSpeed",-.5);
        tsbAdapter=new TSBAdapter(new Joystick(2),this);
    }
        

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        /*if (PneumaticSubsystem.getItakeStatus()) {
            itake.periodic();
        }*/
        itake.periodic();
        pneumatic.periodic();
        shoot.periodic();
        arm.periodic();
    }

    public static void intake() {
    }

    public static Object getInstance() {
        return null;
    }

    public Object getIOtake() {
        return null;
    }

    public double getTuningValue(final String key) {
        return tuningValues.get(key);
      }
    
      public void setTuningValue(final String key, final int value) {
        tuningValues.replace(key, (double) value);
      }
    
      public void setTuningValue(final String key, final double value) {
        tuningValues.replace(key, value);
      }

    public String[] getKeys() {
        final String[] keys = new String[tuningValues.keySet().size()];
        tuningValues.keySet().toArray(keys);
        return keys;
      }

      public void stopEverything(){

      }
}
