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
  private Joystick primaryJoystick = new Joystick(0);

  private CANSparkMax intakeMotor = new CANSparkMax(RobotMap.INTAKE, MotorType.kBrushless); 
  boolean iTakeOff = true;

  public void periodic() {

    // variable intake
    double intake = primaryJoystick.getRawAxis(2);
    // Square the forward stick
    intake = Math.copySign(Math.pow(intake, 2.0), intake);

    double outtake = primaryJoystick.getRawAxis(3);
    outtake = Utilities.deadband(outtake);
    // Square the forward stick
    outtake = Math.copySign(Math.pow(outtake, 2.0), outtake);

    if (iTakeOff) {
        intakeMotor.set((outtake + (-intake))/1.25);
    }


    // fixed speed intake 
    if (primaryJoystick.getRawButtonReleased(4)) {
        iTakeOff();
    }

    if (primaryJoystick.getRawButtonReleased(5)) {
        iTakeFWD();
    }

    if (primaryJoystick.getRawButtonReleased(6)) {
        iTakeBWD();
    }

}   

    public void iTakeFWD(){
        iTakeOff = false;
        intakeMotor.set(-0.5);
    }

    public void iTakeBWD(){
        iTakeOff = false;
        intakeMotor.set(0.5);
    }

    public void iTakeOff(){
        iTakeOff = true;
        intakeMotor.set(0);
    }

    public static IntakeSubsystems getInstance() {
        if (instance == null) {
            instance = new IntakeSubsystems();
        }

        return instance;   
        }
}