package frc.system;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
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

    public static final int leftShootMotorPort = 5;
    public static final int rightShootMotorPort = 6;
    public static final int angleMotorID = 0;

    public static void init() {
        rangeSensor = new Ultrasonic(1, 0);
        leftShootMotor = new VictorSP(leftShootMotorPort);
        rightShootMotor = new VictorSP(rightShootMotorPort);
        angleMotor = new TalonSRX(angleMotorID);

        doubleSolenoid = new DoubleSolenoid(0, 1);

        SmartDashboard.putNumber("ShootingkP", 0);
        SmartDashboard.putNumber("shootingTarget", 0);
    }

    public static double getRange() {
        return rangeSensor.getRangeMM();
    }

    public static void teleop() {
        kP = SmartDashboard.getNumber("ShootingkP", 0);
        target = (int) SmartDashboard.getNumber("Target", 0);
        int error = angleMotor.getSensorCollection().getPulseWidthPosition() - target;
        SmartDashboard.putNumber("error", error);
        double out = error * kP;
        angleMotor.set(ControlMode.PercentOutput, out);

        if (check(Robot.xBox.getBButton())) {
            leftShootMotor.set(0.6);
            rightShootMotor.set(-0.6);
        } else if (Robot.xBox.getAButton()) {
            leftShootMotor.set(0.3);
            rightShootMotor.set(-0.3);
        } else {
            leftShootMotor.set(0);
            rightShootMotor.set(0);
        }
    }

    public static boolean check(boolean in) {
        return Robot.controler.check(in, true);
    }
}