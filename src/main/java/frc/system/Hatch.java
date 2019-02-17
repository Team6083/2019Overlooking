package frc.system;


import org.team6083.lib.dashboard.DashBoard;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Hatch {

    public static Compressor air;
    public static DoubleSolenoid dhatch;
    public static DoubleSolenoid dpush; // push down panel
    public static VictorSP hatch;

    public static DashBoard dashBoard = new DashBoard("hatch");

    public static void init() {
        air = new Compressor(2);
        controlCompressor(true);
        dpush = new DoubleSolenoid(2, 0, 1);
        dhatch = new DoubleSolenoid(2, 3, 2);
        hatch = new VictorSP(17);

        dpush.set(Value.kReverse);
        dashBoard.markReady();
    }

    public static void controlCompressor(boolean on) {
        air.setClosedLoopControl(on);
        SmartDashboard.putBoolean("pneumatic/compCloseLoop", on);
    }

    public static void tele() {
        
        if (Shooting.an() == true) {
            if (Robot.xBox.getStickButtonPressed(Hand.kRight)) {
                if (dhatch.get() == DoubleSolenoid.Value.kForward) {
                    dhatch.set(DoubleSolenoid.Value.kReverse);
                } else {
                    dhatch.set(DoubleSolenoid.Value.kForward);
                }
            }

            if (Robot.xBox.getPOV() == 90) {
                dpush.set(DoubleSolenoid.Value.kForward);
            } else {
                dpush.set(DoubleSolenoid.Value.kReverse);
            }
        } else {
            dhatch.set(Value.kReverse);
            dpush.set(Value.kReverse);
        }
        dashboard();
        if(Robot.controler.check(Robot.xBox.getXButton(), false)){
            hatch.set(0.1);
        }else if(Robot.controler.check(Robot.xBox.getYButton(), false)){
            hatch.set(-0.1);
        }
    }

    public static boolean check(boolean in) {
        return Robot.controler.check(in, false);
    }

    public static void dashboard() {
        if (air.getCompressorShortedFault()) {
            dashBoard.markError();
        } else if (air.getCompressorNotConnectedFault()) {
            dashBoard.markWarning();
        } else {
            dashBoard.markReady();
        }

        SmartDashboard.putBoolean("pneumatic/compPower", !air.getPressureSwitchValue());
        controlCompressor(SmartDashboard.getBoolean("pneumatic/compCloseLoop", false));

        SmartDashboard.putBoolean("panel/fowPis", dpush.get() == Value.kForward);
    }
}
