/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** 
* @author: Lou DeZeeuw  
*/

public class Climber extends SubsystemBase {
  private WPI_TalonSRX climberTalon; 
  
  public Climber() {
    climberTalon = new WPI_TalonSRX(Constants.climber); 
  }

  public void set(double speed) {
    climberTalon.set(speed); 
  }
  
  public void stop() {
    climberTalon.set(0); 
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
