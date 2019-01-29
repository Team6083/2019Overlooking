package frc.system;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Up {
    public static VictorSP upMotor;

    public static final int upMotorPort = 7;

    public static void init() {
        upMotor = new VictorSP(upMotorPort);
    }

    public static void teleop() {
        double upSpeed = checkNumber(Robot.xBox.getTriggerAxis(Hand.kLeft) - Robot.xBox.getTriggerAxis(Hand.kRight));
        
        upMotor.set(upSpeed);
        SmartDashboard.putNumber("up/motorOut", upSpeed);
    }

    public static double checkNumber(double number) {
        return Robot.controler.check(number, false);
    }
}
