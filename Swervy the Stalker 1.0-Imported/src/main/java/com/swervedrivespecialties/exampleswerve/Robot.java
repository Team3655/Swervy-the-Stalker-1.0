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
import frc.robot.Limelight;
import frc.robot.buttons.JSBAdapter;
import frc.robot.buttons.TSBAdapter;
import frc.robot.buttons.TSBAdapter.Mode;
import frc.robot.event.EventHandler;
import frc.robot.event.eventSequences.Auton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

    private static DrivetrainSubsystem drivetrain;
    private static PneumaticSubsystem pneumatic;
    private static ArmSubsystem arm;
    private static IntakeSubsystems itake;
    private static ShootSubsystem shoot;
    private static Robot instance;
    public static final EventHandler eHandler = new EventHandler();
    private Hashtable<String, Double> tuningValues=new Hashtable<>();   
    private TSBAdapter tsbAdapter;
    private JSBAdapter jsbAdapter;
    public static final UsbCamera front = CameraServer.startAutomaticCapture();
    public static final UsbCamera back = CameraServer.startAutomaticCapture();
    public static final Limelight limelight=new Limelight();


    @Override
    public void robotInit() {
        instance=this;
        //Tuning Value Defaults
        tuningValues.put("drive", 1d);
        tuningValues.put("shootSpeedTop",.75);//old shoot speed .775//maybe turn up?
        tuningValues.put("shootSpeedBot",.775);

        tuningValues.put("shootSpeedTopAuto",.75);//old shoot speed .775//maybe turn up?
        tuningValues.put("shootSpeedBotAuto",.675);

        tuningValues.put("Auto Delay", 0.0); 

        tuningValues.put("autonAlias",0d);
        back.setExposureManual(12);    
        front.setExposureManual(12); 

        drivetrain = DrivetrainSubsystem.getInstance();
        pneumatic = PneumaticSubsystem.getInstance();
        itake = IntakeSubsystems.getInstance();
        shoot = ShootSubsystem.getInstance();
        arm = ArmSubsystem.getInstance();
        ArmSubsystem.getInstance().Arm_SwervyInit();

        PneumaticSubsystem.getInstance().PneumaticSubsystem();
        
        tsbAdapter=new TSBAdapter(new Joystick(2),this);
        jsbAdapter=new JSBAdapter();
        eHandler.start();

        limelight.disable();
    }
        

    @Override
    public void autonomousInit() {
        super.autonomousInit();
        eHandler.triggerEvent(new Auton());
        shoot.setTopSpeed(tuningValues.get("shootSpeedTopAuto"));
        shoot.setBotSpeed(tuningValues.get("shootSpeedBotAuto"));
    }
    @Override
    public void autonomousPeriodic(){
        DrivetrainSubsystem.getInstance().periodic();
    }

    public void robotPeriodic(){
        DrivetrainSubsystem.getInstance().postModuleAnglesToDashboard();
    }

    public void disabledPeriodic(){
        tsbAdapter.update();
        DrivetrainSubsystem.getInstance().postModuleAnglesToDashboard();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        /*if (PneumaticSubsystem.getItakeStatus()) {
            itake.periodic();
        }*/
        tsbAdapter.update();
        jsbAdapter.update();
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
        shoot.setTopSpeed(tuningValues.get("shootSpeedTop"));
        shoot.setBotSpeed(tuningValues.get("shootSpeedBot"));
        limelight.reOpen();
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

      public JSBAdapter getJSBAdapter(){
          return jsbAdapter;
      }

}
