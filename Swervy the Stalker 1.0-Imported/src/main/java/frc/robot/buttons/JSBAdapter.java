package frc.robot.buttons;

import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JSBAdapter extends ButtonHandler{

    public JSBAdapter(){
        super(new Joystick(0),10);
    }

    void buttonPressed(int no) {
        
        // TODO Auto-generated method stub
        switch (no){
            case 7:
                DrivetrainSubsystem.getInstance().resetGyroscope();
            break;
            //Hold to Enable Limelight
            case 6:
                Robot.limelight.enable();

            break;
        }


    }

    @Override
    void buttonReleased(int no) {
        switch (no){
            //Release to Disable Limelight
            case 6:
                Robot.limelight.disable();
            break;
        }
        
    }

    @Override
    void buttonDown(int no) {
        // TODO Auto-generated method stub
        
    }

    public void update(){
        super.update();
    }
    
}
