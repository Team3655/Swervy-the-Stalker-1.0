package com.swervedrivespecialties.exampleswerve.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.swervedrivespecialties.exampleswerve.RobotMap;
import com.swervedrivespecialties.exampleswerve.commands.DriveCommand;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.drivers.Gyroscope;
import org.frcteam2910.common.drivers.SwerveModule;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.drivers.Mk2SwerveModuleBuilder;
import org.frcteam2910.common.robot.drivers.NavX;
import edu.wpi.first.wpilibj.I2C;

public class DrivetrainSubsystem extends Subsystem {

    private final Gyroscope gyroscope = new NavX(I2C.Port.kOnboard);

    
        private static final double TRACKWIDTH = 22;
        private static final double WHEELBASE = 22;

        private static final double FRONT_LEFT_ANGLE_OFFSET = -Math.toRadians(11.9);
        private static final double FRONT_RIGHT_ANGLE_OFFSET = -Math.toRadians(54.4);
        private static final double BACK_LEFT_ANGLE_OFFSET = -Math.toRadians(194.4);
        private static final double BACK_RIGHT_ANGLE_OFFSET = -Math.toRadians(93.8);
        //array of the pid constants for swerve modules with the constants for front left at index 0 and continuing clockwise
        private static final PidConstants[] PID_CONSTANTS=new PidConstants[] {
                //Default NEO constants are 0.5, 0.0, 0.0001
                new PidConstants(.625, 0.00001, 0.00001),
                new PidConstants(.625, 0.00001, 0.00001),
                new PidConstants(.625, 0.00001, 0.00001),
                new PidConstants(.625, 0.00001, 0.00001)};
        private static final double ANGLEREDUCTION=18;//(ratio)
        private static final double DRIVEREDUCTION=8.31;//(ratio)
        private static final double WHEEL_DIAMETER=3.875;//(inches)


        private static final double ROTATION_P=1/(2*Math.PI);

        private static DrivetrainSubsystem instance;

        private final SwerveModule frontLeftModule = new Mk2SwerveModuleBuilder(
                new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0))
                .angleEncoder(new AnalogInput(RobotMap.DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER), FRONT_LEFT_ANGLE_OFFSET)
                .angleMotor(new CANSparkMax(RobotMap.DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                        PID_CONSTANTS[0],ANGLEREDUCTION)
                .driveMotor(new CANSparkMax(RobotMap.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                        DRIVEREDUCTION,WHEEL_DIAMETER)
                .build();
        private final SwerveModule frontRightModule = new Mk2SwerveModuleBuilder(
                new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
                .angleEncoder(new AnalogInput(RobotMap.DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER), FRONT_RIGHT_ANGLE_OFFSET)
                .angleMotor(new CANSparkMax(RobotMap.DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                        PID_CONSTANTS[1],ANGLEREDUCTION)
                .driveMotor(new CANSparkMax(RobotMap.DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                        DRIVEREDUCTION,WHEEL_DIAMETER)
                .build();
        private final SwerveModule backLeftModule = new Mk2SwerveModuleBuilder(
                new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0))
                .angleEncoder(new AnalogInput(RobotMap.DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER), BACK_LEFT_ANGLE_OFFSET)
                .angleMotor(new CANSparkMax(RobotMap.DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                        PID_CONSTANTS[3],ANGLEREDUCTION)
                .driveMotor(new CANSparkMax(RobotMap.DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                        DRIVEREDUCTION,WHEEL_DIAMETER)
                .build();
        private final SwerveModule backRightModule = new Mk2SwerveModuleBuilder(
                new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
                .angleEncoder(new AnalogInput(RobotMap.DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER), BACK_RIGHT_ANGLE_OFFSET)
                .angleMotor(new CANSparkMax(RobotMap.DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                        PID_CONSTANTS[2],ANGLEREDUCTION)
                .driveMotor(new CANSparkMax(RobotMap.DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless),
                        DRIVEREDUCTION,WHEEL_DIAMETER)
                .build();

        private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
                new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                new Translation2d(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                new Translation2d(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0)
        );

        public DrivetrainSubsystem() {
                gyroscope.calibrate();
                gyroscope.setInverted(true); // You might not need to invert the gyro

                frontLeftModule.setName("Front Left");
                frontRightModule.setName("Front Right");
                backLeftModule.setName("Back Left");
                backRightModule.setName("Back Right");
                
        }

        public static DrivetrainSubsystem getInstance() {
                if (instance == null) {
                instance = new DrivetrainSubsystem();
                }


                return instance;
        }

        @Override
        public void periodic() {
                frontLeftModule.updateSensors();
                frontRightModule.updateSensors();
                backLeftModule.updateSensors();
                backRightModule.updateSensors(); 

                SmartDashboard.putNumber("Gyroscope Angle", gyroscope.getAngle().toDegrees());

                frontLeftModule.updateState(TimedRobot.kDefaultPeriod);
                frontRightModule.updateState(TimedRobot.kDefaultPeriod);
                backLeftModule.updateState(TimedRobot.kDefaultPeriod);
                backRightModule.updateState(TimedRobot.kDefaultPeriod);
        }

        public void postModuleAnglesToDashboard(){
                frontLeftModule.updateSensors();
                frontRightModule.updateSensors();
                backLeftModule.updateSensors();
                backRightModule.updateSensors();
                SmartDashboard.putNumber("Front Left Angle", Math.toDegrees(frontLeftModule.getCurrentAngle()));
                SmartDashboard.putNumber("Front Right Angle", Math.toDegrees(frontRightModule.getCurrentAngle()));
                SmartDashboard.putNumber("Back Left Angle", Math.toDegrees(backLeftModule.getCurrentAngle()));
                SmartDashboard.putNumber("Back Right Angle", Math.toDegrees(backRightModule.getCurrentAngle()));
        }

        public void drive(Translation2d translation, double rotation, boolean fieldOriented) {
                rotation *= 2.0 / Math.hypot(WHEELBASE, TRACKWIDTH);
                ChassisSpeeds speeds;
                if (fieldOriented) {
                        speeds = ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotation,
                        Rotation2d.fromDegrees(gyroscope.getAngle().toDegrees()));
                } else {
                        speeds = new ChassisSpeeds(translation.getX(), translation.getY(), rotation);
                }

                SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);
                frontLeftModule.setTargetVelocity(states[0].speedMetersPerSecond, states[0].angle.getRadians());
                frontRightModule.setTargetVelocity(states[1].speedMetersPerSecond, states[1].angle.getRadians());
                backLeftModule.setTargetVelocity(states[2].speedMetersPerSecond, states[2].angle.getRadians());
                backRightModule.setTargetVelocity(states[3].speedMetersPerSecond, states[3].angle.getRadians());
        }

        public SwerveModuleState[] getStates(Translation2d translation, double rotation, boolean fieldOriented){
                rotation *= 2.0 / Math.hypot(WHEELBASE, TRACKWIDTH);
                ChassisSpeeds speeds;
                if (fieldOriented) {
                        speeds = ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotation,
                        Rotation2d.fromDegrees(gyroscope.getAngle().toDegrees()));
                } else {
                        speeds = new ChassisSpeeds(translation.getX(), translation.getY(), rotation);
                }

                return kinematics.toSwerveModuleStates(speeds);
        }


        public void resetGyroscope() {
                gyroscope.setAdjustmentAngle(gyroscope.getUnadjustedAngle());
        }

        public Gyroscope getGyroscope() {
                return gyroscope;
        }

        @Override
        protected void initDefaultCommand() {
                setDefaultCommand(new DriveCommand());
        }

        /**Returns an array of the swerve modules with front left in index 0 and continuing clockwise
         * 
         * @return
         */
        public SwerveModule[] getSwerveModules(){
                return new SwerveModule[] {frontLeftModule,frontRightModule,backRightModule,backLeftModule};
        }
}