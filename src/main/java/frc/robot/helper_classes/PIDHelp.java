/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.helper_classes;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

/**
 * @author: Lou DeZeeuw
 */
public class PIDHelp {

    public double getRPM(WPI_TalonSRX talon, int pidSlot) {
        double nativeUnitVelocity = talon.getSelectedSensorVelocity(pidSlot);
        nativeUnitVelocity /= .1;
        nativeUnitVelocity /= 4096;
        nativeUnitVelocity *= 60; 
        return nativeUnitVelocity; 
    }
    
    public double getRPM(WPI_TalonSRX talon) {
        double nativeUnitVelocity = talon.getSelectedSensorVelocity();
        nativeUnitVelocity /= .1;
        nativeUnitVelocity /= 4096;
        nativeUnitVelocity *= 60; 
        return nativeUnitVelocity; 
    }

    public double getRPM(CANSparkMax spark) {
        CANEncoder enc = spark.getEncoder(); 
        double rpm = enc.getVelocity(); 
        return rpm; 
    }

    public double getVelocity(WPI_TalonSRX talon, int pidSlot) {
        return talon.getSelectedSensorVelocity(pidSlot); 
    }

    public double getVelocity(WPI_TalonSRX talon) {
        return talon.getSelectedSensorVelocity(); 
    }

    public double getPosition(WPI_TalonSRX talon) {
        return talon.getSelectedSensorPosition(); 
    }

    public double getPosition(CANSparkMax spark) {
        CANEncoder enc = spark.getEncoder(); 
        return enc.getPosition(); 
    }

    public void toVelocity(WPI_TalonSRX talon, double velocity) {
        talon.set(ControlMode.Velocity, velocity);
    }

    public void toRPM(WPI_TalonSRX talon, double RPM) {
        double velocity = RPM * 4096; 
        talon.set(ControlMode.Velocity, velocity); 
    }
}
