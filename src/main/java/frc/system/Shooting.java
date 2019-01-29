package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team6083.lib.RobotPower;

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

    public static final int leftShootMotorPort = 5;
    public static final int rightShootMotorPort = 6;
    public static final int angleMotorID = 3;

    public static void init() {
        rangeSensor = new Ultrasonic(1, 0);
        leftShootMotor = new VictorSP(leftShootMotorPort);
        rightShootMotor = new VictorSP(rightShootMotorPort);
        angleMotor = new TalonSRX(angleMotorID);

        doubleSolenoid = new DoubleSolenoid(2, 0, 1);

        rpLeft = new RobotPower(15);
        rpRight = new RobotPower(14);

        SmartDashboard.putNumber("ShootingkP", 0);
        SmartDashboard.putNumber("shootingTarget", 0);
    }

    public static double getRange() {
        return rangeSensor.getRangeMM();
    }

    public static void teleop() {
        kP = SmartDashboard.getNumber("ShootingkP", 0);
        target = (int) SmartDashboard.getNumber("ShooterTarget", 0);
        
        int currentStep = angleMotor.getSensorCollection().getPulseWidthPosition();
        int error = currentStep - target;
        
        SmartDashboard.putNumber("shooterError", error);

        double angleMotorOut = 0;

        if (checkNumber(Robot.xBox.getTriggerAxis(Hand.kLeft)) > 0) {
            angleMotorOut = checkNumber(Robot.xBox.getTriggerAxis(Hand.kLeft));
        } else if (checkNumber(Robot.xBox.getTriggerAxis(Hand.kRight)) > 0) {
            angleMotorOut = checkNumber(Robot.xBox.getTriggerAxis(Hand.kRight));
        } else {
            angleMotorOut = error * kP;
        }

        angleMotor.set(ControlMode.PercentOutput, angleMotorOut);
        SmartDashboard.putNumber("shoot/", angleMotorOut);

        if (check(Robot.xBox.getBButton())) {
            leftShootMotor.set(0.6);
            rightShootMotor.set(0.6);
        } else if (check(Robot.xBox.getAButton())) {
            leftShootMotor.set(-0.3);
            rightShootMotor.set(-0.3);
        } else if (check(Robot.xBox.getBButtonReleased())) {
            doubleSolenoid.set(Value.kForward);
            shootTimer.start();
        } else if (shootTimer.get() > 1 && check(Robot.xBox.getBButton())) {
            leftShootMotor.set(0);
            rightShootMotor.set(0);
            doubleSolenoid.set(Value.kReverse);
            shootTimer.stop();
            shootTimer.reset();
        } else {
            doubleSolenoid.set(Value.kOff);
        }

        SmartDashboard.putNumber("shoot/currentLeft", rpLeft.getPortCurrent());
        SmartDashboard.putNumber("shoot/currentRight", rpRight.getPortCurrent());
        SmartDashboard.putNumber("shoot/enc", translateAngle(currentStep));
        SmartDashboard.putNumber("shoot/target", translateAngle(target));
    }

    public static double translateAngle(int step) {
        return step / 4096 * 360;
    }

    public static boolean check(boolean in) {
        return Robot.controler.check(in, true);
    }

    public static double checkNumber(double number) {
        return Robot.controler.check(number, true);
    }
}