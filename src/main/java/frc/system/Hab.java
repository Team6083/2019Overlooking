package frc.system;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;

public class Hab {
    public static DoubleSolenoid doubleSolenoid;

    public static void init(){
        doubleSolenoid = new DoubleSolenoid(2, 4, 5);
        doubleSolenoid.set(Value.kReverse);
    }
    
    public static void teleop(){
        if(Robot.xBox.getXButtonPressed()){
            if(doubleSolenoid.get() == Value.kForward){
                doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
            } else{
                doubleSolenoid.set(DoubleSolenoid.Value.kForward);
            }
        }
    }

}