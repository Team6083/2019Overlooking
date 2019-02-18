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
    public static double kP = 0.0003;

    public static int idleLoopCount = 0;

    public static void init() {
        upMotor = new WPI_TalonSRX(upMotorID);
        upMotor.getSensorCollection().setQuadraturePosition(0, 1000);
        dashBoard.markReady();
        SmartDashboard.putNumber("upKp", kP);
        dashboard();
    }

    public static void teleop() {
        double currentPos = upMotor.getSensorCollection().getQuadraturePosition();

        if (Robot.xBox.getTriggerAxis(Hand.kLeft) > 0 || Robot.xBox.getTriggerAxis(Hand.kRight) > 0) {
            upSpeed = Robot.xBox.getTriggerAxis(Hand.kLeft) - Robot.xBox.getTriggerAxis(Hand.kRight);
            idleLoopCount = 0;
        } else if (idleLoopCount > 5) {
            upSpeed = -(currentPos - target) * kP;
        } else {
            upSpeed = 0;
            idleLoopCount++;
            target = currentPos;
        }

        upMotor.set(ControlMode.PercentOutput, upSpeed);
        SmartDashboard.putNumber("enc", currentPos);
        SmartDashboard.putNumber("target", target);
        kP = SmartDashboard.getNumber("upKp", 0);

        dashboard();
    }

    public static void dashboard() {
        SmartDashboard.putNumber("up/motorOut", upMotor.getMotorOutputPercent());
        SmartDashboard.putNumber("up/enc", upMotor.getSensorCollection().getQuadraturePosition());
        SmartDashboard.putNumber("up/targetStep", target);
        SmartDashboard.putBoolean("up/holdingOverride", false);
    }
}
