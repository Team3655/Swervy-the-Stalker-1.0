package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import com.swervedrivespecialties.exampleswerve.Robot;

public final class Main {
    public static void main(String... args) {
        RobotBase.startRobot(Robot::new);
    }
}
