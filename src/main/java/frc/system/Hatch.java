package frc.system;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;

public class Hatch {

    public static Compressor air;
    public static DoubleSolenoid dpush;
    public static DoubleSolenoid dpush2;

    public static void init() {
        air = new Compressor();
        air.setClosedLoopControl(true);
        dpush = new DoubleSolenoid(0, 1);
        dpush2 = new DoubleSolenoid(2, 3);
    }

    public static void controlCompressor(boolean on) {
        air.setClosedLoopControl(on);
    }

    public static void tele() {
        if (Robot.xBox.getAButton()) {
            dpush.set(DoubleSolenoid.Value.kForward);
            dpush2.set(DoubleSolenoid.Value.kForward);
        }
        else if(Robot.xBox.getBButton()){
            dpush.set(DoubleSolenoid.Value.kReverse);
        }
        else if(Robot.xBox.getYButton()){
            dpush2.set(DoubleSolenoid.Value.kReverse);
        }
        else{
            dpush.set(DoubleSolenoid.Value.kOff);
            dpush2.set(DoubleSolenoid.Value.kOff);
        }

    }
}

