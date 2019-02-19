/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.team6083.lib.RobotPower;
import org.team6083.lib.dashboard.DashBoard;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.system.Drive;
import frc.system.Hab;
import frc.system.Hatch;
import frc.system.Shooting;
import frc.system.Up;
import frc.system.Vision;
import frc.system.sensor.OverlookingAHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  public static XBox xBox;
  public static Controller controler;
  public static OverlookingAHRS ahrs;

  @Override
  public void robotInit() {
    xBox = new XBox(0);
    RobotPower.init(1);
    new DashBoard("pdp").markReady();
    DashBoard.init();

    Drive.init();
    Shooting.init();
    Hatch.init();
    Up.init();
    Hab.init();
    Vision.init();

    controler = new Controller(Drive.drive);
    ahrs = new OverlookingAHRS(SPI.Port.kMXP);
    ahrs.reset();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    Drive.tank();
    Hatch.tele();
    Shooting.teleop();
    Up.teleop();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
