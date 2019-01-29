package frc.system;

import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Robot;

public class Up {
    public static VictorSP vic;

    public static void init() {
        vic = new VictorSP(1);
   }
   public static void teleop(){
    if(Robot.xBox.getStartButton()){
        vic.set(0.5);
    }
    else if(Robot.xBox.getBackButton()){
        vic.set(-0.2);
    }
    else{
        vic.set(0);
    }
   }
}

