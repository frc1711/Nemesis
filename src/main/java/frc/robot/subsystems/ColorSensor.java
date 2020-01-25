/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** 
* @author: Gabe Seaver  
*/

public class ColorSensor extends SubsystemBase {
  
  private ColorSensorV3 colorSensor;
  private Color colorDetected;

  public ColorSensor() {
    colorSensor = new ColorSensorV3(Port.kOnboard);
  }

  public Color getColor() {
    colorDetected = colorSensor.getColor();
    return colorDetected;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
