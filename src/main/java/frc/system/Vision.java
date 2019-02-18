package frc.system;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Vision {
  public static void init() {
    UsbCamera usb = CameraServer.getInstance().startAutomaticCapture(0);
    usb.setConnectVerbose(0);
    usb.setResolution(640, 360);
  }
}