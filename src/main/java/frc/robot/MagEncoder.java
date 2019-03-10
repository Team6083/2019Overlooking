package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class MagEncoder implements PIDSource {

    private TalonSRX motor;
    private PIDSourceType sourceType;

    public MagEncoder(TalonSRX m) {
        motor = m;
        sourceType = PIDSourceType.kDisplacement;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        sourceType = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return sourceType;
    }

    @Override
    public double pidGet() {
        return motor.getSensorCollection().getPulseWidthPosition();
    }

    

}