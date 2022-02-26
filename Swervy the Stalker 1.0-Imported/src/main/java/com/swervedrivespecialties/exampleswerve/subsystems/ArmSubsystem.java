package com.swervedrivespecialties.exampleswerve.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.swervedrivespecialties.exampleswerve.Robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import com.swervedrivespecialties.exampleswerve.subsystems.PneumaticSubsystem;


public class ArmSubsystem extends Subsystem {
    
    private static ArmSubsystem instance;
    
    private CANSparkMax leftArm = new CANSparkMax(RobotMap.ARM_LEFT_MOTOR, MotorType.kBrushless);;
    private CANSparkMax rightArm = new CANSparkMax(RobotMap.ARM_RIGHT_MOTOR, MotorType.kBrushless);
    private SparkMaxPIDController leftPidController = leftArm.getPIDController();
    private SparkMaxPIDController rightPidController = rightArm.getPIDController();
    private RelativeEncoder leftEncoder = leftArm.getEncoder();
    private RelativeEncoder rightEncoder = rightArm.getEncoder();
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
    double avgDiff;

    private double leftEncoderPos = leftEncoder.getPosition();
    private double rightEncoderPos = rightEncoder.getPosition();


    public void Arm_SwervyInit() {

    leftArm.restoreFactoryDefaults();
    rightArm.restoreFactoryDefaults();

    leftEncoder = leftArm.getEncoder();
    rightEncoder = rightArm.getEncoder();

    Joystick  primaryJoystick = new Joystick(0);
    Joystick  secondaryJoystick = new Joystick(1);

    //Arm PID
    kP = 0.000006; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000165; 
    kMaxOutput = 0.8; 
    kMinOutput = -0.8;
    maxRPM = 2000;

    leftPidController.setP(kP);
    leftPidController.setI(kI);
    leftPidController.setD(kD);
    leftPidController.setIZone(kIz);
    leftPidController.setFF(kFF);
    leftPidController.setOutputRange(kMinOutput, kMaxOutput);

    rightPidController.setP(kP);
    rightPidController.setI(kI);
    rightPidController.setD(kD);
    rightPidController.setIZone(kIz);
    rightPidController.setFF(kFF);
    rightPidController.setOutputRange(kMinOutput, kMaxOutput);
    }

    public void periodic() {
    //ARM PERIODIC
       double setPoint = (Robot.getOi().getSecondaryJoystick().getRawAxis(2)-Robot.getOi().getSecondaryJoystick().getRawAxis(3))*maxRPM;
       leftEncoderPos = leftEncoder.getPosition();
       rightEncoderPos = rightEncoder.getPosition();
       boolean Grip1Open = getSubsystem().PneumaticSubsystem().grip1Solenoid.get();
       boolean Grip2Open = (grip2Solenoid.get() == Value.kReverse);
        boolean Grip3Open = (grip3Solenoid.get() == Value.kReverse);
        boolean Grip4Open = (grip4Solenoid.get() == Value.kReverse);


       if((setPoint > 0) && ((leftEncoderPos >= 255) || (rightEncoderPos <= -255))){
        setPoint = 0;
        } 

       if((setPoint < 0) && ((leftEncoderPos <= 0) || (rightEncoderPos >= 0))){
           setPoint = 0;
       } 
         
   
       leftPidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
       rightPidController.setReference(-setPoint, CANSparkMax.ControlType.kVelocity);
       SmartDashboard.putNumber("Setpoint", setPoint);
        
       SmartDashboard.putNumber("Left P", leftEncoder.getPosition());
       SmartDashboard.putNumber("Right P", rightEncoder.getPosition());
       double diff = -rightEncoder.getVelocity() - leftEncoder.getVelocity();
       SmartDashboard.putNumber("diff V", diff);

       if ( leftEncoder.getVelocity() > 750 || rightEncoder.getVelocity() > 750) {
           avgDiff = (avgDiff + diff)/2;
        }
        SmartDashboard.putNumber("avgDiff", avgDiff);
       
    }

    public static ArmSubsystem getInstance() {
        if (instance == null) { 
            instance = new ArmSubsystem();
        }
        return instance;
    }

    protected void initDefaultCommand() {}

}