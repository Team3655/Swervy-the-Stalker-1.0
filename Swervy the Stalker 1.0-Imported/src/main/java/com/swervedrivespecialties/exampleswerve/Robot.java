package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.IntakeSubsystems;
import com.swervedrivespecialties.exampleswerve.subsystems.PneumaticSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.ShootSubsystem;

import java.util.Hashtable;

import com.swervedrivespecialties.exampleswerve.subsystems.ArmSubsystem;

import frc.robot.event.Event;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.buttons.TSBAdapter;
import frc.robot.buttons.TSBAdapter.Mode;
import frc.robot.event.EventHandler;
import frc.robot.event.eventSequences.Auton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private static OI oi;

    private static DrivetrainSubsystem drivetrain;
    private static PneumaticSubsystem pneumatic;
    private static ArmSubsystem arm;
    private static IntakeSubsystems itake;
    private static ShootSubsystem shoot;
    private static Robot instance;
    public static final EventHandler eHandler = new EventHandler();
    private Hashtable<String, Double> tuningValues=new Hashtable<>();   
    private TSBAdapter tsbAdapter;
    public static final UsbCamera front = CameraServer.startAutomaticCapture();
    public static final UsbCamera back = CameraServer.startAutomaticCapture();

    public static OI getOi() {
        return oi;
    }

    @Override
    public void robotInit() {
        instance=this;
        //Tuning Value Defaults
        tuningValues.put("drive", 1d);
    
        tuningValues.put("autonAlias",0d);

        oi = new OI();

        drivetrain = DrivetrainSubsystem.getInstance();
        pneumatic = PneumaticSubsystem.getInstance();
        itake = IntakeSubsystems.getInstance();
        shoot = ShootSubsystem.getInstance();
        arm = ArmSubsystem.getInstance();
        ArmSubsystem.getInstance().Arm_SwervyInit();

        PneumaticSubsystem.getInstance().PneumaticSubsystem();
        
        tsbAdapter=new TSBAdapter(new Joystick(2),this);
        eHandler.start();
    }
        

    @Override
    public void autonomousInit() {
        super.autonomousInit();
        eHandler.triggerEvent(new Auton());
        tuningValues.put("shootSpeedTop",.75);
        tuningValues.put("shootSpeedBot",.65);
    }
    @Override
    public void autonomousPeriodic(){
        DrivetrainSubsystem.getInstance().periodic();
    }


    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        /*if (PneumaticSubsystem.getItakeStatus()) {
            itake.periodic();
        }*/
        tsbAdapter.update();
        itake.periodic();
        pneumatic.periodic();
        shoot.periodic();
        arm.periodic();
        SmartDashboard.putNumber("bottom speed", tuningValues.get("shootSpeedTop"));
        SmartDashboard.putNumber("bottom speed", tuningValues.get("shootSpeedBot"));
    }

    @Override
    public void teleopInit(){
        tsbAdapter.setMode(Mode.RobotResponse);
        tuningValues.put("shootSpeedTop",.775);//old shoot speed -.775
        tuningValues.put("shootSpeedBot",.775);
    }

    @Override
    public void disabledInit(){
        tsbAdapter.setMode(Mode.Tune);
        eHandler.clear();
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
        String[] keys = new String[tuningValues.keySet().size()];
        tuningValues.keySet().toArray(keys);
        return keys;
      }

      public void stopEverything(){

      }

      public static Robot getRobot(){
          return instance;
      }

      public TSBAdapter getTSBAdapter(){
          return tsbAdapter;
      }
}
