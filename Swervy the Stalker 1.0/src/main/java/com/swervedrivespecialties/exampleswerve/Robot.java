package com.swervedrivespecialties.exampleswerve;

import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.IntakeSubsystems;
import com.swervedrivespecialties.exampleswerve.subsystems.PneumaticSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
    private static OI oi;

    private static DrivetrainSubsystem drivetrain;
    private static PneumaticSubsystem pneumatic;
    //private static ArmSubsystem arm;
    private static IntakeSubsystems itake;


    public static OI getOi() {
        return oi;
    }
   
   

    @Override
    public void robotInit() {
        oi = new OI();
        drivetrain = DrivetrainSubsystem.getInstance();
        pneumatic = PneumaticSubsystem.getInstance();
        //arm = ArmSubsystem.getInstance();
        
        //ArmSubsystem.getInstance().Arm_SwervyInit();
    }


    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
    }



    public static void intake() {
    }
}
