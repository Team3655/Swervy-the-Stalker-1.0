package com.swervedrivespecialties.exampleswerve;

public class RobotMap {
    //FRONT_LEFT
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 2; // CAN
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER = 0; // Analog
    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 1; // CAN

    //FRONT_RIGHT
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 4; // CAN
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER = 1; // Analog
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 3; // CAN
    
    //BACK_LEFT
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 6; // CAN
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER = 2; // Analog
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 5; // CAN

    //BACK_RIGHT
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 8; // CAN
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER = 3; // Analog
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 7; // CAN

    //ARMS
    public static final int ARM_LEFT_MOTOR = 11; // CAN
    public static final int ARM_RIGHT_MOTOR = 12; // CAN
    public static final int ID_OPENGRIP1SOL = 0;
    public static final int ID_CLOSEGRIP1SOL = 1;
    public static final int ID_OPENGRIP2SOL = 2;
    public static final int ID_CLOSEGRIP2SOL = 3;
    public static final int ID_OPENGRIP3SOL = 4;
    public static final int ID_CLOSEGRIP3SOL = 5;
    public static final int ID_OPENGRIP4SOL = 6;
    public static final int ID_CLOSEGRIP4SOL = 7;//7 (turn back to 7 to use for arm testing)

    //public static final int ID_EXTENDINTAKE = 7;
    public static final int ID_PCM = 0;

    //INTAKE_&_INDEXER
    public static int INTAKE = 13;
    public static final int INDEXER = 14;

    //ELEVATOR
    public static final int ELEVATION = 15;

    //SHOOTER
    public static final int SHOOTER_BOTTOM = 16;
    public static final int SHOOTER_TOP = 17;
    
}
