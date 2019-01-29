package frc.system;

import org.team6083.lib.auto.GyroWalker;
import org.team6083.lib.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.system.sensor.OverlookingAHRS;

public class Drive {
    public static DifferentialDrive  drive;
    public static VictorSP leftMotor1;
    public static VictorSP leftMotor2;
    public static VictorSP rightMotor1;
    public static VictorSP rightMotor2;

    public static OverlookingAHRS gyro;
    public static GyroWalker gwalk;
    
    public static double Target;
    public static double kP;
    public static double kI;

    public static final int lMotor1Port = 0;
    public static final int lMotor2Port = 1;
    public static final int rMotor1Port = 2;
    public static final int rMotor2Port = 3;
    
    public static void init(){
        leftMotor1 = new VictorSP(lMotor1Port);
        leftMotor2 = new VictorSP(lMotor2Port);
        rightMotor1 = new VictorSP(rMotor1Port);
        rightMotor2 = new VictorSP(rMotor2Port);
        drive = new DifferentialDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);

        gyro = new OverlookingAHRS(SPI.Port.kMXP);
        gwalk = new GyroWalker(gyro);

        SmartDashboard.putNumber("TargetAngle", 0);
        SmartDashboard.putNumber("GyrokP", 0);
        SmartDashboard.putNumber("GyrokI", 0);
    }

    public static void tank() {
        drive.tankDrive(Robot.xBox);
    }

    public static void gyrowalker(){
        Target = SmartDashboard.getNumber("TargetAngle", 0);
        kP = SmartDashboard.getNumber("GyrokP", 0);
        kI = SmartDashboard.getNumber("GyrokI", 0);
        gwalk.setkP(kP);
        gwalk.setkI(kI);
        
        gwalk.setTargetAngle(Target);
        gwalk.calculate(Robot.xBox.leftSpeed(), Robot.xBox.rightSpeed());
        double lspeed = gwalk.getLeftPower();
        double rspeed = gwalk.getRightPower();
        drive.directControl(lspeed, rspeed);
    }
}