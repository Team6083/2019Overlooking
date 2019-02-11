package frc.system;

import org.team6083.lib.dashboard.DashBoard;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Hatch {

    public static Compressor air;
    public static DoubleSolenoid dpush;
    public static DoubleSolenoid dpush2;

    public static DashBoard dashBoard = new DashBoard("hatch");

    public static void init() {
        air = new Compressor(2);
        air.setClosedLoopControl(true);
        dpush = new DoubleSolenoid(2, 3, 2);

        dpush.set(Value.kReverse);
        dashBoard.markReady();
    }

    public static void controlCompressor(boolean on) {
        air.setClosedLoopControl(on);
    }

    public static void tele() {
        if (air.getCompressorShortedFault()) {
            dashBoard.markError();
        } else if (air.getCompressorNotConnectedFault()) {
            dashBoard.markWarning();
        } else {
            dashBoard.markReady();
        }

        if (check(Robot.xBox.getAButton())) {
            dpush.set(DoubleSolenoid.Value.kForward);
        } else if (check(Robot.xBox.getBButton())) {
            dpush.set(DoubleSolenoid.Value.kReverse);
        } else {
            dpush.set(DoubleSolenoid.Value.kOff);
            dpush2.set(DoubleSolenoid.Value.kOff);
        }

    }

    public static boolean check(boolean in) {
        return Robot.controler.check(in, false);
    }
}
