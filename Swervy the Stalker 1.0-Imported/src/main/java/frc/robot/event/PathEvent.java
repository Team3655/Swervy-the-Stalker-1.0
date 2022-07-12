package frc.robot.event;

import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import org.frcteam2910.common.drivers.SwerveModule;
import org.w3c.dom.events.MouseEvent;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class PathEvent extends Event{
    private HolonomicDriveController hDriveController = new HolonomicDriveController(new PIDController(1, 0, 0), new PIDController(1, 0, 0),new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(1.57, .78)));
    private SwerveDriveOdometry odometry = new SwerveDriveOdometry(DrivetrainSubsystem.getInstance().getKinematics(), new Rotation2d(DrivetrainSubsystem.getInstance().getGyroscope().getAngle().toRadians()));
    private Trajectory trajectory;
    private long startTime;
    private int state;

    public PathEvent(Trajectory t){
        this.trajectory=t;
    }

    public PathEvent(Trajectory t, long delay) {
        super(delay);
        this.trajectory = t;
    }

    public void task(){
        switch (state){
            case 0:
                startTime = System.currentTimeMillis();
                followPath(0);
            break;
            case 1:
                followPath(System.currentTimeMillis()-startTime);
            break;
        }
        
    }

    private void followPath(long time){
        SwerveModuleState[] states = new SwerveModuleState[4];
            SwerveModule[] modules = DrivetrainSubsystem.getInstance().getSwerveModules();
            //To Do : get the current states into states array for the odometry
    
            Pose2d pos = odometry.update(new Rotation2d(DrivetrainSubsystem.getInstance().getGyroscope().getAngle().toRadians()), states);

            hDriveController.calculate(pos, trajectory.sample(time), new Rotation2d());
    }

}