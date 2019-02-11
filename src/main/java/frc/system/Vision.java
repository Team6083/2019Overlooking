package frc.system;

import edu.wpi.first.cameraserver.CameraServer;

public class Vision {
  public static void init() {
    CameraServer.getInstance().startAutomaticCapture(0);
  }
}