package frc.system;

import org.team6083.lib.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.VictorSP;

public class Drive {
    public static DifferentialDrive  drive;
    public static VictorSP leftMotor1;
    public static VictorSP leftMotor2;
    public static VictorSP rightMotor1;
    public static VictorSP rightMotor2;
    
    public static void init(){
        leftMotor1 = new VictorSP(0);
        leftMotor2 = new VictorSP(1);
        rightMotor1 = new VictorSP(2);
        rightMotor2 = new VictorSP(3);
        drive = new DifferentialDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
    }
}