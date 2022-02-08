package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.swervedrivespecialties.exampleswerve.Robot;

public class ArmSubsystem extends Subsystem {
    
    private static ArmSubsystem instance;
    
    private final CANSparkMax leftArm = new CANSparkMax(RobotMap.ARM_LEFT_DRIVE_MOTOR, MotorType.kBrushless);;
    //private CANSparkMax rightArm new CANSparkMax(RobotMap.ARM_RIGHT_DRIVE_MOTOR, MotorType.kBrushless);
    private final SparkMaxPIDController leftPidController = leftArm.getPIDController();
    //private SparkMaxPIDController rightPidController = rightArm.getPIDController();
    private final RelativeEncoder leftEncoder = leftArm.getEncoder();
    //private RelativeEncoder rightEncoder rightArm.getEncoder();
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

    public void Arm_SwervyInit() {

    //leftArm = new CANSparkMax(RobotMap.ARM_LEFT_DRIVE_MOTOR, MotorType.kBrushless);
    //rightArm = new CANSparkMax(RobotMap.ARM_RIGHT_DRIVE_MOTOR, MotorType.kBrushless);
    
    leftArm.restoreFactoryDefaults();
    //rightArm.restoreFactoryDefaults();
    
    //leftPidController = leftArm.getPIDController();
    //rightPidController = rightArm.getPIDController();

    //leftEncoder = leftArm.getEncoder();
    //rightEencoder = rightArm.getEncoder();

    kP = 0.000006; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000165; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;

    leftPidController.setP(kP);
    leftPidController.setI(kI);
    leftPidController.setD(kD);
    leftPidController.setIZone(kIz);
    leftPidController.setFF(kFF);
    leftPidController.setOutputRange(kMinOutput, kMaxOutput);

    //rightPidController.setP(kP);
    //rightPidController.setI(kI);
    //rightPidController.setD(kD);
    //rightPidController.setIZone(kIz);
    //rightPidController.setFF(kFF);
    //ightPidController.setOutputRange(kMinOutput, kMaxOutput);

    }

    public ArmSubsystem() {
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);
        
        if((p != kP)) { leftPidController.setP(p); kP = p; }
        if((i != kI)) { leftPidController.setI(i); kI = i; }
        if((d != kD)) { leftPidController.setD(d); kD = d; }
        if((iz != kIz)) { leftPidController.setIZone(iz); kIz = iz; }
        if((ff != kFF)) { leftPidController.setFF(ff); kFF = ff; }
        if((max != kMaxOutput) || (min != kMinOutput)) { 
          leftPidController.setOutputRange(min, max); 
          kMinOutput = min; kMaxOutput = max; 
        }

        //if((p != kP)) { rightPidController.setP(p); kP = p; }
        //if((i != kI)) { rightPidController.setI(i); kI = i; }
        //if((d != kD)) { rightPidController.setD(d); kD = d; }
        //if((iz != kIz)) { rightPidController.setIZone(iz); kIz = iz; }
        //if((ff != kFF)) { rightPidController.setFF(ff); kFF = ff; }
        //if((max != kMaxOutput) || (min != kMinOutput)) { 
        //  rightPidController.setOutputRange(min, max); 
        //  kMinOutput = min; kMaxOutput = max; 
        //}

        double setPoint = -Robot.getOi().getSecondaryJoystick().getRawAxis(1);
        leftPidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
        //rightPidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);

        SmartDashboard.putNumber("SetPoint", setPoint);
        SmartDashboard.putNumber("Left ProcessVariable", leftEncoder.getVelocity());
        //SmartDashboard.putNumber("Right ProcessVariable", rightEncoder.getVelocity());
    }


    public static ArmSubsystem getInstance() {
        if (instance == null) {
            instance = new ArmSubsystem();
        }

        return instance;
    }

    protected void initDefaultCommand() {
    
    }

}