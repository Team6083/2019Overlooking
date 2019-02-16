package frc.system;

import org.team6083.lib.dashboard.DashBoard;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Up {
    public static WPI_TalonSRX upMotor;

    public static DashBoard dashBoard = new DashBoard("up");

    public static final int upMotorID = 21;

    public static double upSpeed = 0;
    public static double target = 0;
    public static double kP = 0;

    public static void init() {
        upMotor = new WPI_TalonSRX(upMotorID);
        upMotor.getSensorCollection().setQuadraturePosition(0, 1000);
        dashBoard.markReady();

    }

    public static void teleop() {
        double currentPos = upMotor.getSensorCollection().getQuadraturePosition();

        if (Robot.xBox.getTriggerAxis(Hand.kLeft) > 0 || Robot.xBox.getTriggerAxis(Hand.kRight) > 0) {
            upSpeed = Robot.xBox.getTriggerAxis(Hand.kLeft) - Robot.xBox.getTriggerAxis(Hand.kRight);
            target = currentPos;
        } else {
            upSpeed = (currentPos - target) * kP;
        }

        upMotor.set(ControlMode.PercentOutput, upSpeed);
    }

    public static double checkNumber(double number) {
        return Robot.controler.check(number, false);
    }

    public static void dashboard() {
        SmartDashboard.putNumber("up/motorOut", upSpeed);
        SmartDashboard.putNumber("up/enc", 0);
        SmartDashboard.putNumber("up/targetStep", 0);
        SmartDashboard.putBoolean("up/holdingOverride", false);
    }
}
