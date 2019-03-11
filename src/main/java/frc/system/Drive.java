package frc.system;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team6083.lib.auto.GyroWalker;
import org.team6083.lib.dashboard.DashBoard;
import org.team6083.lib.drive.DifferentialDrive;

//import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
//import frc.system.sensor.OverlookingAHRS;

public class Drive {
    public static DifferentialDrive drive;
    public static WPI_VictorSPX leftMotor1;
    public static WPI_VictorSPX leftMotor2;
    public static WPI_VictorSPX rightMotor1;
    public static WPI_VictorSPX rightMotor2;

   // public static OverlookingAHRS gyro;
    public static GyroWalker gwalk;

    public static DashBoard dashboard = new DashBoard("drive");

    public static double Target;
    public static double kP;
    public static double kI;

    public static final int lMotor1ID = 12;
    public static final int lMotor2ID = 14;
    public static final int rMotor1ID = 15;
    public static final int rMotor2ID = 16;

    public static void init() {
        leftMotor1 = new WPI_VictorSPX(lMotor1ID);
        leftMotor2 = new WPI_VictorSPX(lMotor2ID);
        rightMotor1 = new WPI_VictorSPX(rMotor1ID);
        rightMotor2 = new WPI_VictorSPX(rMotor2ID);
        drive = new DifferentialDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
        drive.setSpeedDown(1.66666666);
        drive.setBoostMultiple(1.333333333);
        drive.setReverseDrive(true);

        // gyro = new OverlookingAHRS(SPI.Port.kMXP);
        // gwalk = new GyroWalker(gyro);

        SmartDashboard.putNumber("TargetAngle", 0);
        SmartDashboard.putNumber("GyrokP", 0);
        SmartDashboard.putNumber("GyrokI", 0);

        dashboard.markReady();
    }

    public static void tank() {
        drive.tankDrive(Robot.xBox);
    }

    public static void gyrowalker() {
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