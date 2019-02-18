package frc.system;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team6083.lib.RobotPower;
import org.team6083.lib.dashboard.DashBoard;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Shooting {

    public static WPI_VictorSPX leftShootMotor;
    public static WPI_VictorSPX rightShootMotor;
    public static WPI_TalonSRX angleMotor;
    public static Encoder angelencoder;
    public static Timer timer, suckTimer;
    public static int ang;
    public static int targetang = 0;
    public static final int targetAng = -50;
    public static int target = 0;
    public static double kP = 0.001;

    public static boolean holdingOverride = false;

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
        angelencoder = new Encoder(0, 1);

        timer = new Timer();
        timer.reset();

        suckTimer = new Timer();
        suckTimer.reset();

        angleMotor.getSensorCollection().setQuadraturePosition(0, 1000);

        rpLeft = new RobotPower(0);
        rpRight = new RobotPower(1);

        dashBoard.markReady();

        SmartDashboard.putNumber("ShootingkP", kP);

        dashboard();
    }

    public static void teleop() {

        kP = SmartDashboard.getNumber("ShootingkP", 0);

        currentStep = angleMotor.getSensorCollection().getQuadraturePosition();
        double angleMotorOut = 0;

        if (Robot.xBox.getPOV(0) == 0) {
            angleMotorOut = 0.2;
            target = currentStep;
        } else if (Robot.xBox.getPOV(0) == 180) {
            angleMotorOut = -0.15;
            target = currentStep;
        } else {
            angleMotor.set(ControlMode.PercentOutput, 0);
            angleMotorOut = (currentStep - target) * kP;
            if (Math.abs(angleMotorOut) > 0.2) {
                angleMotorOut = 0.2 * ((angleMotorOut > 0) ? 1 : -1);
            }
        }

        angleMotor.set(ControlMode.PercentOutput, angleMotorOut);

        if (Robot.controler.check(Robot.xBox.getAButton(), true)) {
            leftShootMotor.set(ControlMode.PercentOutput, -0.5);
            rightShootMotor.set(ControlMode.PercentOutput, 0.5);
        } else if (Robot.controler.check(Robot.xBox.getYButtonPressed(), true)) {
            leftShootMotor.set(ControlMode.PercentOutput, 0.4);
            rightShootMotor.set(ControlMode.PercentOutput, -0.4);
        } else if (Robot.controler.check(Robot.xBox.getYButtonReleased(), true)) {
            timer.stop();
            timer.reset();
            timer.start();
        } else if (Robot.controler.check(Robot.xBox.getBButton(), true)) {
            leftShootMotor.set(ControlMode.PercentOutput, -0.4);
            rightShootMotor.set(ControlMode.PercentOutput, 0.4);
        } else if (((rpLeft.getPortCurrent() > 10 || rpRight.getPortCurrent() > 10) && suckTimer.get() == 0) || (timer.get() > 5 && suckTimer.get() == 0)) {
            suckTimer.start();
            timer.stop();
            leftShootMotor.set(ControlMode.PercentOutput, 0.3);
            rightShootMotor.set(ControlMode.PercentOutput, -0.3);
        } else if (suckTimer.get() > 1) {
            suckTimer.stop();
            suckTimer.reset();
            timer.stop();
            timer.reset();

            leftShootMotor.set(ControlMode.PercentOutput, 0);
            rightShootMotor.set(ControlMode.PercentOutput, 0);            
        } else if (timer.get() == 0 && !Robot.controler.check(Robot.xBox.getYButton(), true)) {
            leftShootMotor.set(0);
            rightShootMotor.set(0);
        }

        SmartDashboard.putNumber("suckTimer", suckTimer.get());
        SmartDashboard.putNumber("timer", timer.get());

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

    public static void dashboard() {
        SmartDashboard.putNumber("shoot/currentLeft",
                new BigDecimal(rpLeft.getPortCurrent()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        SmartDashboard.putNumber("shoot/currentRight",
                new BigDecimal(rpRight.getPortCurrent()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        SmartDashboard.putNumber("shoot/enc", currentStep);
        SmartDashboard.putNumber("shoot/target", target);
        SmartDashboard.putNumber("shoot/angleMotorOut", angleMotor.getMotorOutputPercent());

        holdingOverride = SmartDashboard.putBoolean("shoot/holdingOverride", false);
        SmartDashboard.putBoolean("shoot/holdingOverride", holdingOverride);
    }
}