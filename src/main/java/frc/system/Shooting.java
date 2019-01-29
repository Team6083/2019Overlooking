package frc.system;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Shooting {

    public static Ultrasonic rangeSensor;
    public static Encoder enc;
    public static VictorSP vic;
    public static VictorSP vic2;
    public static VictorSP vic3;
    public static int target;
    public static double kP;
    public static DoubleSolenoid doubleSolenoid;

    public static void init() {
        rangeSensor = new Ultrasonic(1, 0);
        enc = new Encoder(0, 0);
        vic = new VictorSP(0);
        vic2 = new VictorSP(2);
        vic3 = new VictorSP(3);
        doubleSolenoid = new DoubleSolenoid(0, 1);
        SmartDashboard.putNumber("ShootingkP", 0);
        SmartDashboard.putNumber("Target", 0);
    }

    public static double getRange() {
        return rangeSensor.getRangeMM();
    }

    public static void teleop() {
        kP = SmartDashboard.getNumber("ShootingkP", 0);
        target = (int) SmartDashboard.getNumber("Target", 0);
        int error = enc.get() - target;
        SmartDashboard.putNumber("error", error);
        double out = error * kP;
        vic.set(out);

        if (check(Robot.xBox.getBButton())) {
            vic2.set(0.6);
            vic3.set(-0.6);
        } else if (Robot.xBox.getAButton()) {
            vic2.set(0.3);
            vic3.set(-0.3);
        } else {
            vic2.set(0);
            vic3.set(0);
        }
    }

    public static boolean check(boolean in) {
        return Robot.controler.check(in, true);
    }
}