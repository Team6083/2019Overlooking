package frc.system;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Hatch {
    
   public static Compressor com;
   public static DoubleSolenoid dou;
   public static DoubleSolenoid dou1;
  
   public static void init() {
    com = new Compressor();
    com.setClosedLoopControl(true);
   }


   public static void controlCompressor(boolean on){
       com.setClosedLoopControl(on);
   }
}