package frc.system;

import com.kauailabs.navx.frc.AHRS;

import org.team6083.auto.GyroWalker;
import org.team6083.lib.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class Drive {
    public static DifferentialDrive  drive;
    public static VictorSP leftMotor1;
    public static VictorSP leftMotor2;
    public static VictorSP rightMotor1;
    public static VictorSP rightMotor2;
    public static AHRS gyro;
    public static GyroWalker gwalk;
    public static double Target;
    public static double kP;
    public static double kI;

    
    public static void init(){
        leftMotor1 = new VictorSP(0);
        leftMotor2 = new VictorSP(1);
        rightMotor1 = new VictorSP(2);
        rightMotor2 = new VictorSP(3);
        gyro = new AHRS(SPI.Port.kMXP);
        gwalk = new GyroWalker((Gyro) gyro);
        SmartDashboard.putNumber("TargetAngle", 0);
        SmartDashboard.putNumber("GyrokP", 0);
        SmartDashboard.putNumber("GyrokI", 0);
        drive = new DifferentialDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
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