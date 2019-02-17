package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team6083.lib.RobotPower;
import org.team6083.lib.dashboard.DashBoard;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Shooting {

    public static WPI_VictorSPX leftShootMotor;
    public static WPI_VictorSPX rightShootMotor;
    public static WPI_TalonSRX angleMotor;
    public static Encoder angelencoder;
    public static final int targetAng = -50;
    public static int target = 0;
    public static double kP;

    public static RobotPower rpLeft, rpRight;

    public static final int leftShootMotorID = 11;
    public static final int rightShootMotorID = 13;
    public static final int angleMotorID = 22;

    public static int currentStep = 0;

    public static DashBoard dashBoard = new DashBoard("shoot");

    public static void init() {
        leftShootMotor = new WPI_VictorSPX(leftShootMotorID);
        rightShootMotor = new WPI_VictorSPX(rightShootMotorID);
        angleMotor = new WPI_TalonSRX(angleMotorID);

        angleMotor.getSensorCollection().setQuadraturePosition(0, 1000);

        rpLeft = new RobotPower(15);
        rpRight = new RobotPower(14);

        dashBoard.markWarning();
        dashboard();
    }

    public static void teleop() {

        kP = SmartDashboard.getNumber("ShootingkP", 0);

        currentStep = angleMotor.getSensorCollection().getQuadraturePosition();
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

        if (Robot.xBox.getAButton()) {
            leftShootMotor.set(ControlMode.PercentOutput, -0.5);
            rightShootMotor.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.xBox.getBButton()) {
            leftShootMotor.set(ControlMode.PercentOutput, 0.4);
            rightShootMotor.set(ControlMode.PercentOutput, -0.4);
        } else if (Robot.xBox.getYButton()) {
            leftShootMotor.set(ControlMode.PercentOutput, -0.4);
            rightShootMotor.set(ControlMode.PercentOutput, 0.4);
        } else {
            leftShootMotor.set(0);
            rightShootMotor.set(0);

        }

        dashboard();
    }

    public static boolean an() {
        int ang = angleMotor.getSensorCollection().getQuadraturePosition();
        if (ang <= targetAng) {
            return true;
        } else {
            return false;
        }
    }

    public static double stepToAngle(int step) {
        return step * 360 / 4096;
    }

    public static int angleToStep(double angle) {
        return (int) (angle * 4096) / 360;
    }

    public static void dashboard(){
        SmartDashboard.putNumber("shoot/currentLeft", rpLeft.getPortCurrent());
        SmartDashboard.putNumber("shoot/currentRight", rpRight.getPortCurrent());
        SmartDashboard.putNumber("shoot/enc", stepToAngle(currentStep));
        SmartDashboard.putNumber("shoot/target", stepToAngle(target));
        SmartDashboard.putNumber("shoot/angleMotorOut", angleMotor.get());
        SmartDashboard.putBoolean("shoot/holdingOverride", false);
        
        dashBoard.markReady();
    }
}