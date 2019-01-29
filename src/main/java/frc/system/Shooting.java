package frc.system;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooting {

    public static Ultrasonic rangeSensor;
    public static Encoder enc;
    public static VictorSP vic;
    public static int target;
    public static double kP;

    public static void init() {
        rangeSensor = new Ultrasonic(1, 0);
        enc = new Encoder(0, 0);
        vic = new VictorSP(0);
        SmartDashboard.putNumber("kP", 0);
        SmartDashboard.putNumber("Target", 0);
    }

    public static double getRange() {
        return rangeSensor.getRangeMM();
    }

    public static void teleop() {
        kP = SmartDashboard.getNumber("kP", 0);
        target = (int) SmartDashboard.getNumber("Target", 0);
        int error = enc.get() - target;
        SmartDashboard.putNumber("error", error);
        double out = error * kP;
        vic.set(out);
    }
}