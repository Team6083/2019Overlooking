package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team6083.lib.RobotPower;
import org.team6083.lib.dashboard.DashBoard;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Shooting {

    public static Ultrasonic rangeSensor;
    public static VictorSP leftShootMotor;
    public static VictorSP rightShootMotor;
    public static TalonSRX angleMotor;
    public static int target;
    public static double kP;

    public static DoubleSolenoid doubleSolenoid;
    public static Timer shootTimer = new Timer();

    public static RobotPower rpLeft, rpRight;

    public static final int leftShootMotorPort = 4;
    public static final int rightShootMotorPort = 5;
    public static final int angleMotorID = 3;

    public static final double[] level = { 49, 120, 192 };

    public static int currentLevel = 0;

    public static DashBoard dashBoard = new DashBoard("shoot");

    public static void init() {
        rangeSensor = new Ultrasonic(1, 0);
        leftShootMotor = new VictorSP(leftShootMotorPort);
        rightShootMotor = new VictorSP(rightShootMotorPort);
        angleMotor = new TalonSRX(angleMotorID);

        angleMotor.getSensorCollection().setPulseWidthPosition(0, 500);

        doubleSolenoid = new DoubleSolenoid(2, 5, 4);

        rpLeft = new RobotPower(15);
        rpRight = new RobotPower(14);

        SmartDashboard.putNumber("ShootingkP", 0);
        SmartDashboard.putNumber("shootingTarget", 0);
        SmartDashboard.getNumber("shootingLevel", 0);
    }

    public static double getRange() {
        return rangeSensor.getRangeMM() / 10;
    }

    public static double getAngle(double height) {
        double result = 0;
        double range = getRange();
        double tan = range / height;

        result = Math.toDegrees(Math.atan(tan));
        return result;
    }

    public static void teleop() {

        currentLevel = (int) SmartDashboard.getNumber("shootingLevel", 0);
        SmartDashboard.putNumber("shootingLevel", currentLevel);

        if (currentLevel > 0) {
            target = angleToStep(getAngle(level[currentLevel - 1]));
        }

        kP = SmartDashboard.getNumber("ShootingkP", 0);
        target = (int) SmartDashboard.getNumber("ShooterTarget", 0);

        int currentStep = angleMotor.getSensorCollection().getPulseWidthPosition();
        int error = currentStep - target;

        SmartDashboard.putNumber("shooterError", error);

        double angleMotorOut = 0;

        if (checkNumber(Robot.xBox.getTriggerAxis(Hand.kLeft)) > 0) {
            angleMotorOut = checkNumber(Robot.xBox.getTriggerAxis(Hand.kLeft));
            target = currentStep;
            currentLevel = 0;
        } else if (checkNumber(Robot.xBox.getTriggerAxis(Hand.kRight)) > 0) {
            angleMotorOut = checkNumber(-Robot.xBox.getTriggerAxis(Hand.kRight));
            target = currentStep;
            currentLevel = 0;
        } else {
            angleMotorOut = error * kP;
        }

        SmartDashboard.putNumber("ShooterTarget", target);

        angleMotor.set(ControlMode.PercentOutput, angleMotorOut);
        SmartDashboard.putNumber("shoot/angleMotorOut", angleMotorOut);

        if (check(Robot.xBox.getBButton())) {
            leftShootMotor.set(0.6);
            rightShootMotor.set(-0.6);
        } else if (check(Robot.xBox.getYButton())) {
            leftShootMotor.set(0.4);
            rightShootMotor.set(-0.4);
        } else if (check(Robot.xBox.getXButton())) {
            leftShootMotor.set(0.5);
            rightShootMotor.set(-0.5);
        } else if (check(Robot.xBox.getAButton())) {
            leftShootMotor.set(-0.3);
            rightShootMotor.set(0.3);
        } else if (check(Robot.xBox.getBButtonReleased() || Robot.xBox.getYButtonReleased() || Robot.xBox.getXButtonReleased())) {
            doubleSolenoid.set(Value.kForward);
            shootTimer.start();
        } else if (shootTimer.get() > 0.7) {
            leftShootMotor.set(0);
            rightShootMotor.set(0);
            doubleSolenoid.set(Value.kReverse);
            shootTimer.stop();
            shootTimer.reset();
        } else if (shootTimer.get() == 0) {
            doubleSolenoid.set(Value.kOff);
            leftShootMotor.set(0);
            rightShootMotor.set(0);
        }

        SmartDashboard.putNumber("shoot/currentLeft", rpLeft.getPortCurrent());
        SmartDashboard.putNumber("shoot/currentRight", rpRight.getPortCurrent());
        SmartDashboard.putNumber("shoot/enc", stepToAngle(currentStep));
        SmartDashboard.putNumber("shooterEncOut", currentStep);
        SmartDashboard.putNumber("shoot/target", stepToAngle(target));
        SmartDashboard.putNumber("shoot/disToRocket", getRange());
        SmartDashboard.putBoolean("shoot/outPiston", doubleSolenoid.get() == Value.kForward);
        SmartDashboard.putNumber("shoot/currentLevel", currentLevel);
    }

    public static double stepToAngle(int step) {
        return step * 360 / 4096;
    }

    public static int angleToStep(double angle) {
        return (int) (angle / 360 * 4096);
    }

    public static boolean check(boolean in) {
        return Robot.controler.check(in, true);
    }

    public static double checkNumber(double number) {
        return Robot.controler.check(number, true);
    }
}