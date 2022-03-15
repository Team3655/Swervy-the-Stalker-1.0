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
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;



//import com.swervedrivespecialties.exampleswerve.subsystems.PneumaticSubsystem;


public class ArmSubsystem extends Subsystem {
    
    Joystick  primaryJoystick = new Joystick(0);
    Joystick  secondaryJoystick = new Joystick(1);

    private static ArmSubsystem instance;
    
    private final PneumaticsControlModule pcm = new PneumaticsControlModule(RobotMap.ID_PCM);
    public final DoubleSolenoid grip1Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP1SOL, RobotMap.ID_CLOSEGRIP1SOL);
    public final DoubleSolenoid grip2Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP2SOL, RobotMap.ID_CLOSEGRIP2SOL);
    public final DoubleSolenoid grip3Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP3SOL, RobotMap.ID_CLOSEGRIP3SOL);
    public final DoubleSolenoid grip4Solenoid = pcm.makeDoubleSolenoid(RobotMap.ID_OPENGRIP4SOL, RobotMap.ID_CLOSEGRIP4SOL);


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

    grip1Solenoid.set(Value.kReverse);
    grip2Solenoid.set(Value.kReverse);
    grip3Solenoid.set(Value.kReverse);
    grip4Solenoid.set(Value.kReverse);

    leftArm.restoreFactoryDefaults();
    rightArm.restoreFactoryDefaults();

    leftEncoder = leftArm.getEncoder();
    rightEncoder = rightArm.getEncoder();


    //Arm PID
    kP = 0.000006; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000165; 
    kMaxOutput = 0.8; 
    kMinOutput = -0.8;
    maxRPM = 4000;

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
        boolean Grip1Open = grip1Solenoid.get() == Value.kReverse;
        boolean Grip2Open = grip2Solenoid.get() == Value.kReverse;
        boolean Grip3Open = grip3Solenoid.get() == Value.kReverse;
        boolean Grip4Open = grip4Solenoid.get() == Value.kReverse;

        SmartDashboard.putBoolean("Grip1Solenoid State", Grip1Open);
        SmartDashboard.putBoolean("Grip2Solenoid State", Grip2Open);
        SmartDashboard.putBoolean("Grip3Solenoid State", Grip3Open);
        SmartDashboard.putBoolean("Grip4Solenoid State", Grip4Open);

        if((Grip2Open == false || Grip4Open == false) && ((leftEncoderPos >= 34 && leftEncoderPos <= 66) || (rightEncoderPos <= -34 && rightEncoderPos >= -66))){
            setPoint = 0;

        } else if (secondaryJoystick.getRawButtonPressed(1)) {
            grip1Solenoid.toggle();
             grip3Solenoid.toggle();
             //System.out.println(grip1Solenoid.get());
        }

        if((Grip1Open == false || Grip3Open == false) && ((leftEncoderPos >= 134 && leftEncoderPos <= 166) || (rightEncoderPos <= -134 && rightEncoderPos >= -166))){
            setPoint = 0;

        }
        

        if((Grip2Open == false || Grip4Open == false) && ((leftEncoderPos >= 234 && leftEncoderPos <= 266) || (rightEncoderPos <= -234 && rightEncoderPos >= -266))){
            setPoint = 0;
        } 
        

        if((setPoint > 0) && ((leftEncoderPos >= 235) || (rightEncoderPos <= -235))){
            setPoint = 0;
        } 

        if((setPoint < 0) && ((leftEncoderPos <= 0) || (rightEncoderPos >= 0))){
            setPoint = 0;
        }


        

        if (secondaryJoystick.getRawButtonPressed(2)) {
            grip2Solenoid.toggle();
            grip4Solenoid.toggle();
            //System.out.println(grip2Solenoid.get());
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