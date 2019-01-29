package frc.robot;

import org.team6083.lib.drive.DifferentialDrive;

public class Controller {

    public DifferentialDrive drive;

    public Controller(DifferentialDrive drive) {
        this.drive = drive;
    }

    public boolean check(boolean in, boolean isFront){
        return check(in, isFront, false);
    }

    public boolean check(boolean in, boolean isFront, boolean disableValue) {
        return (drive.getReverseDrive() != isFront) ? in : disableValue;
    }

    public double check(double in, boolean isFront) {
        return check(in, isFront, 0);
    }

    public double check(double in, boolean isFront, double disableValue) {
        return (drive.getReverseDrive() != isFront) ? in : disableValue;
    }
}