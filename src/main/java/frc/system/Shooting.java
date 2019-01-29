package frc.system;

import edu.wpi.first.wpilibj.Ultrasonic;

public class Shooting{

    public static Ultrasonic rangeSensor;

    public static void init(){
        rangeSensor = new Ultrasonic(1, 0);
    }

    public static double getRange(){
        return rangeSensor.getRangeMM();
    }

}