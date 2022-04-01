package frc.robot.buttons;

import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.commands.DriveCommand;
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
            //Hold To Boost (GO FAST!!!)
            case 1:
                ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setRotationMultiplier(1);
                ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setTranslationMultiplier(1);
            break;
            //Hold To Disable FieldCentric
            case 5:
                ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setFieldCentric(false);
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
            case 1:
            //Release to Stop Boost (GO SLOW!!!)
                ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setRotationMultiplier(.75);
                ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setTranslationMultiplier(.7);
            break;
            case 5:
                ((DriveCommand)DrivetrainSubsystem.getInstance().getDefaultCommand()).setFieldCentric(true);
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
