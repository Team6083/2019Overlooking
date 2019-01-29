package frc.system;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;

public class Vision {
  public static UsbCamera USBCamera0;
  public static MjpegServer mjp0;
  public static CvSource CVso;
  public static CvSink CvS;

  public static void init() {
    USBCamera0 = new UsbCamera("USBCamera_0", 0);
    mjp0 = new MjpegServer("serve_usb_USBCamera0", 1181);
    CvS = new CvSink("opencv_0");
    CVso = new CvSource("Blur", PixelFormat.kMJPEG, 640, 480, 30);

    mjp0.setSource(USBCamera0);
    CvS.setSource(USBCamera0);
  }

}