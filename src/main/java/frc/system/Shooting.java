package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team6083.lib.RobotPower;
import org.team6083.lib.dashboard.DashBoard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Shooting {

    public static WPI_VictorSPX leftShootMotor;
    public static WPI_VictorSPX rightShootMotor;
    public static WPI_TalonSRX angleMotor;
    public static int target = 0;
    public static double kP;

    public static RobotPower rpLeft, rpRight;

    public static final int leftShootMotorID = 11;
    public static final int rightShootMotorID = 13;
    public static final int angleMotorID = 22;

    public static DashBoard dashBoard = new DashBoard("shoot");

    public static void init() {
        leftShootMotor = new WPI_VictorSPX(leftShootMotorID);
        rightShootMotor = new WPI_VictorSPX(rightShootMotorID);
        angleMotor = new WPI_TalonSRX(angleMotorID);

        angleMotor.getSensorCollection().setQuadraturePosition(0, 1000);

        rpLeft = new RobotPower(15);
        rpRight = new RobotPower(14);

        SmartDashboard.putNumber("ShootingkP", 0);
        SmartDashboard.putNumber("shootingTarget", 0);
        SmartDashboard.getNumber("shootingLevel", 0);
    }

    public static void teleop() {

        kP = SmartDashboard.getNumber("ShootingkP", 0);

        int currentStep = angleMotor.getSensorCollection().getQuadraturePosition();
        int error = currentStep - target;
        double angleMotorOut = 0;

        if (Robot.xBox.getPOV(0) == 0) {
            angleMotorOut = 0.15;
            target = currentStep;
        } else if (Robot.xBox.getPOV(0) == 180) {
            angleMotorOut = -0.15;
            target = currentStep;
        } else {
            angleMotorOut = error * kP;
        }

        angleMotor.set(ControlMode.PercentOutput, angleMotorOut);
        SmartDashboard.putNumber("shoot/angleMotorOut", angleMotorOut);

        if (Robot.xBox.getAButton()) {
            leftShootMotor.set(ControlMode.PercentOutput, -0.5);
            rightShootMotor.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.xBox.getBButton()) {
            leftShootMotor.set(ControlMode.PercentOutput, 0.3);
            rightShootMotor.set(ControlMode.PercentOutput, -0.3);
        } else if (Robot.xBox.getYButton()) {
            leftShootMotor.set(ControlMode.PercentOutput, -0.3);
            rightShootMotor.set(ControlMode.PercentOutput, 0.3);
        } else {
            leftShootMotor.set(0);
            rightShootMotor.set(0);
        }

        SmartDashboard.putNumber("shoot/currentLeft", rpLeft.getPortCurrent());
        SmartDashboard.putNumber("shoot/currentRight", rpRight.getPortCurrent());
        SmartDashboard.putNumber("shoot/enc", stepToAngle(currentStep));
        SmartDashboard.putNumber("shoot/target", stepToAngle(target));
        SmartDashboard.putNumber("shoot/outSpeed", leftShootMotor.getMotorOutputPercent());
        SmartDashboard.putNumber("shoot/autoTarget", 0);
        SmartDashboard.putBoolean("shoot/autoHeading", false);// 自己去改
    }

    public static double stepToAngle(int step) {
        return step * 360 / 4096;
    }

    public static int angleToStep(double angle) {
        return (int) (angle * 4096) / 360;
    }

    public static boolean check(boolean in) {
        return Robot.controler.check(in, true);
    }

    public static double checkNumber(double number) {
        return Robot.controler.check(number, true);
    }
}