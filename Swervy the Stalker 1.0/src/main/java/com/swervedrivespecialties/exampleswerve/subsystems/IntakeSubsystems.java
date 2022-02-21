package com.swervedrivespecialties.exampleswerve.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import com.swervedrivespecialties.exampleswerve.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam2910.common.robot.Utilities;


public class IntakeSubsystems{
  public IntakeSubsystems() {}

  private static IntakeSubsystems instance;
  private Joystick  secondaryJoystick = new Joystick(1);

  private CANSparkMax intakeMotor = new CANSparkMax(RobotMap.INTAKE, MotorType.kBrushless);

  public void periodic() {
    double intake = -Robot.getOi().getPrimaryJoystick().getRawAxis(2);
    intake = Utilities.deadband(intake);
    // Square the forward stick
    intake = Math.copySign(Math.pow(intake, 2.0), intake);
    intakeMotor.set(-intake);

    double outtake = -Robot.getOi().getPrimaryJoystick().getRawAxis(3);
    outtake = Utilities.deadband(outtake);
    // Square the forward stick
    outtake = Math.copySign(Math.pow(outtake, 2.0), outtake);
    intakeMotor.set(outtake);
}   

    public void itakeFWD(){
        intakeMotor.set(-0.5);
    }

    public void itakeBWD(){
        intakeMotor.set(0.5);
    }

    public void itakeOff(){
        intakeMotor.set(0);
    }

    public static IntakeSubsystems getInstance() {
        if (instance == null) {
            instance = new IntakeSubsystems();
        }

        return instance;   
        }
}