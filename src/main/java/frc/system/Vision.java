package frc.system;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Vision {
  public static UsbCamera usbcam;
  public static void init() {
    usbcam = CameraServer.getInstance().startAutomaticCapture(0);
    usbcam.setBrightness(70);
  }
}