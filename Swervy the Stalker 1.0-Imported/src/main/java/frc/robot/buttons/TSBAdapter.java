package frc.robot.buttons;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.event.Event;
import frc.robot.event.PrintEvent;
import com.swervedrivespecialties.exampleswerve.Robot;
import com.swervedrivespecialties.exampleswerve.commands.DriveCommand;
import com.swervedrivespecialties.exampleswerve.subsystems.ArmSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.DrivetrainSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.PneumaticSubsystem;
import com.swervedrivespecialties.exampleswerve.subsystems.ShootSubsystem;

/**Tractor Simulator Button Adapter for long
 * 
 */
public class TSBAdapter extends ButtonHandler{
    private Robot robot;
    public enum Mode{RobotResponse,Tune,RobotRecord,Test};
    private enum ControlMode{Joystick,PID};
    private ControlMode elevatorControlMode;
    private ControlMode armControlMode;
    private Mode mode;
    private String[] tuningValues;
    private int currentPropertyNo=0;
    private String currentTuningValue;
    private String inputCache;
    private Joystick armJoystick;



    public TSBAdapter(Joystick tractorPanel, Robot robot){
        super(tractorPanel,28); //button 28 is the red button on the joystick and button 27 is press on wheel (those buttons aren't labled on the panel)
        this.robot=robot;
        mode=Mode.RobotResponse;
        currentPropertyNo=0;
        tuningValues=Robot.getRobot().getKeys();
        currentTuningValue=tuningValues[currentPropertyNo];
        inputCache="";
        elevatorControlMode=ControlMode.Joystick;
        armControlMode=ControlMode.PID;
        SmartDashboard.putBoolean("Submit", false);
        //setArmJoystick(getJoystick());
    }
    public void buttonPressed(int no){
        if (mode==Mode.RobotResponse&&robot.isEnabled()){
            switch (no){

                case 1:
                    ArmSubsystem.getInstance().toggleGreen();
                break;

                case 2:
                    ArmSubsystem.getInstance().toggleYellow();
                break;  

                case 4:
                    PneumaticSubsystem.getInstance().toggleIntakeSolenoid();
                break;


                case 19: 
                    ShootSubsystem.getInstance().raise();
                break;

                case 20: 
                    ShootSubsystem.getInstance().lower();
                break;

                case 21:
                    PneumaticSubsystem.getInstance().toggleCompressor();
                break;
                
                case 23:
                        //turn everything off
                        robot.stopEverything();
                break;
            
                case 28:
                    mode=Mode.Tune;
                    Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'Tune'"));//need make log before getting rid of this
                    SmartDashboard.putString("Out", "Mode set to 'Tune'");
                break;
            }
        } else if (mode==Mode.Tune) {
            if (no<10){
                    inputCache=inputCache+no;
                    SmartDashboard.putString("Input", inputCache);
                
            } else if (no<28) {
                switch (no){
                    case 10:
                        inputCache=inputCache+0;
                        SmartDashboard.putString("Input", inputCache);
                    break;

                    case 11:
                        try {
                            inputCache=inputCache.substring(0, inputCache.length()-1);
                        } catch (Exception e){
                        }
                        SmartDashboard.putString("Input", inputCache);
                    break;

                    case 12:
                        if (!inputCache.contains(".")){
                            inputCache=inputCache+".";
                            SmartDashboard.putString("Input", inputCache);
                        }
                    break;

                    case 15:
                        nextTuningValue();
                    break;
   
                    case 16:
                        lastTuningValue();
                    break;

                    case 17:
                        if (!inputCache.contains("-")){
                            inputCache="-"+inputCache;
                            //Robot.eHandler.triggerEvent(new PrintEvent("Input Cache: "+inputCache));
                        } else {
                            inputCache=inputCache.substring(1);
                        }
                        SmartDashboard.putString("Input", inputCache);
                    break;

                    case 20:
                        Robot.eHandler.clear();
                    break;
                                    

                    //Button 21 set value to input
                    case 21:
                        submitTune();
                    break;
                    
                    case 23:
                        //turn everything off
                        robot.stopEverything();
                    break;

                    case 25:
                        SmartDashboard.putBoolean("Submit", false);
                        //Robot.eHandler.triggerEvent(new PrintEvent("Current value of "+currentTuningValue+": "+robot.getTuningValue(currentTuningValue)));
                        SmartDashboard.putString("Current Value", ""+robot.getTuningValue(currentTuningValue));
                    break;

                }
             } else {
                switch (no){
                    case 28:
                        if (robot.isEnabled()){
                            if (robot.isTeleop()){
                                mode=Mode.RobotResponse;
                                Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));//need make log before getting rid of this
                                SmartDashboard.putString("Out", "Mode set to 'RobotResponse'");
                                
                            } else {
                                mode=Mode.Tune;
                                Robot.eHandler.triggerEvent(new PrintEvent("RobotResponse mode not available while robot is in auton. Mode set to 'Tune'",true));//need make log before getting rid of this
                                SmartDashboard.putString("Out", "RobotResponse mode not available while robot is in auton. Mode set to 'Tune'");
                            }
                            
                        } else {
                            mode=Mode.Tune;
                            Robot.eHandler.triggerEvent(new PrintEvent("RobotResponse mode not available while robot is disabled. Mode set to 'Tune'",true));
                            SmartDashboard.putString("Out", "RobotResponse mode not available while robot is disabled. Mode set to 'Tune'");
                        }
                    break;
                }
            }
        } else {
            mode=Mode.Tune;
            Robot.eHandler.triggerEvent(new PrintEvent("RobotResponse mode not available while robot is disabled. Mode set to 'Tune'",true));
        }
    }

    public void buttonReleased(int no){
        switch (no){
            //Move Elevator Up & Down When Button is held
            case 3:
                ShootSubsystem.getInstance().elevatorStop();   
            break;
  
            case 8:
                ShootSubsystem.getInstance().elevatorStop(); 
            break;

        }
    }

    @Override
    public void update() {
        super.update();
        if (robot.isEnabled()){
            ArmSubsystem.getInstance().setSetPoint(getY());
        }
        if (mode==Mode.Tune){
            SmartDashboard.putString("Current Value", ""+robot.getTuningValue(currentTuningValue));
            SmartDashboard.putString("Editing", ""+currentTuningValue);
            inputCache=SmartDashboard.getString("Input", inputCache);
            SmartDashboard.putString("Input", ""+inputCache);
            if (SmartDashboard.getBoolean("Submit", false)){
                SmartDashboard.putBoolean("Submit", false);
                submitTune();
            }
        }
    }

    public void nextTuningValue(){
        currentPropertyNo++;
        if (currentPropertyNo>=tuningValues.length){
            currentPropertyNo=0;
        }
        currentTuningValue=tuningValues[currentPropertyNo];
        //System.out.println("Now edititing "+currentTuningValue);
        if (!Robot.eHandler.triggerEvent(new PrintEvent("Now edititing "+currentTuningValue))){
            System.err.println("Print failed to queue");
        }
    }

    public void lastTuningValue(){
        currentPropertyNo--;
        if (currentPropertyNo<0){
            currentPropertyNo=tuningValues.length-1;
        }
        currentTuningValue=tuningValues[currentPropertyNo];
        //System.out.println("Now edititing "+currentTuningValue);
        if (!Robot.eHandler.triggerEvent(new PrintEvent("Now editing"+currentTuningValue))){
            System.err.println("Print failed to queue");
        }
    }

    public void setMode(Mode mode){
        this.mode=mode;
    }

    
    void buttonDown(int no) {
        switch(no){
        //Move Elevator Up & Down When Button is held
        case 3:
            ShootSubsystem.getInstance().elevatorUp();   
        break;

        case 8:
            ShootSubsystem.getInstance().elevatorDown(); 
        break;

        }
    }

    public void submitTune(){
        try {
            robot.setTuningValue(currentTuningValue, Double.parseDouble(inputCache));
            SmartDashboard.putString("Out", currentTuningValue+" set to "+inputCache);
            inputCache="";

        } catch (NumberFormatException e){
            inputCache="";
            SmartDashboard.putString("Out", "User did not enter a number");
        }
    }
}