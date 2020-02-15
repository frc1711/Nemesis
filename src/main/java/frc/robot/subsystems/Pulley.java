/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helper_classes.PIDHelp;

/** 
* @author: Lou DeZeeuw, Gabriel Seaver  
*/

public class Pulley extends SubsystemBase {
  
  private WPI_TalonSRX pulleyTalon;

  private DigitalInput bottomSensor; 
  private DigitalInput middleSensor; 

  public Pulley() {
    pulleyTalon = new WPI_TalonSRX(Constants.pulley);

    pulleyTalon.setSafetyEnabled(false); 

    pulleyTalon.config_kP(0, Constants.pulleykP); 
    pulleyTalon.config_kI(0, Constants.pulleykI); 
    pulleyTalon.config_kD(0, Constants.pulleykD); 
    pulleyTalon.config_kF(0, Constants.pulleykF); 

    bottomSensor = new DigitalInput(Constants.bottomSensor); 
    middleSensor = new DigitalInput(Constants.middleSensor); 
  }

  public void run(double speed) {
    pulleyTalon.set(speed);
  }
  
  public void stop() {
    pulleyTalon.set(0);
  }

  public double getRPM() {
    return PIDHelp.getRPM(pulleyTalon); 
  }

  public double getVelocity() {
    return PIDHelp.getVelocity(pulleyTalon); 
  }

  public void toVelocity(double velocity) {
    PIDHelp.toVelocity(pulleyTalon, velocity); 
  }

  public void toRPM(double RPM) {
    PIDHelp.toRPM(pulleyTalon, RPM); 
  }

  public boolean getBottomSensor() {
    return !bottomSensor.get(); 
  }
  
  public boolean getMiddleSensor() {
    return !middleSensor.get(); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
