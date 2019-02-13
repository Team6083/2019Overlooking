package frc.system;

import org.team6083.lib.dashboard.DashBoard;


import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Up {
    public static TalonSRX upMotor;

    public static DashBoard dashBoard = new DashBoard("up");

    public static final int upMotorPort = 6;

    public static double upSpeed = 0;

    public static void init() {
        upMotor = new TalonSRX(upMotorPort);
        dashBoard.markReady();
    }

    public static void teleop() {
        upSpeed = checkNumber(Robot.xBox.getTriggerAxis(Hand.kLeft) - Robot.xBox.getTriggerAxis(Hand.kRight));
        upMotor.set(ControlMode.PercentOutput, upSpeed);
    }

    public static double checkNumber(double number) {
        return Robot.controler.check(number, false);
    }

    public static void dashboard(){
        SmartDashboard.putNumber("up/motorOut", upSpeed);
        SmartDashboard.putNumber("up/enc", 0);
        SmartDashboard.putNumber("up/targetStep", 0);
        SmartDashboard.putBoolean("up/holdingOverride", false);
    }
}
