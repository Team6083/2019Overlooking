package frc.system;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Robot;

public class Hatch {

    public static Compressor air;
    public static DoubleSolenoid dpush;
    public static DoubleSolenoid dpush2;

    public static void init() {
        air = new Compressor(2);
        air.setClosedLoopControl(true);
        dpush = new DoubleSolenoid(2, 4, 5);
        dpush2 = new DoubleSolenoid(2, 2, 3);
    }

    public static void controlCompressor(boolean on) {
        air.setClosedLoopControl(on);
    }

    public static void tele() {
        if (check(Robot.xBox.getAButton())) {
            dpush.set(DoubleSolenoid.Value.kForward);
            dpush2.set(DoubleSolenoid.Value.kForward);
        } else if (check(Robot.xBox.getBButton())) {
            dpush.set(DoubleSolenoid.Value.kReverse);
        } else if (check(Robot.xBox.getYButton())) {
            dpush2.set(DoubleSolenoid.Value.kReverse);
        } else {
            dpush.set(DoubleSolenoid.Value.kOff);
            dpush2.set(DoubleSolenoid.Value.kOff);
        }

    }

    public static boolean check(boolean in) {
        return Robot.controler.check(in, true);
    }
}
