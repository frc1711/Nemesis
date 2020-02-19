/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** 
* @author: Gabriel Seaver, Lou DeZeeuw  
*/

public class ColorManager extends SubsystemBase {
  
  private Color colorDetected;
  private ColorSensorV3 colorSensor;
  private WPI_TalonSRX colorTalon;

  public ColorManager() {
    colorSensor = new ColorSensorV3(Port.kOnboard); 
    colorTalon = new WPI_TalonSRX(Constants.colorWheel); 
  }

  public Color getColor() {
    colorDetected = colorSensor.getColor(); 
    return colorDetected; 
  }

  public class ColorCoordinate { //THIS IS A CLASS FOR DESCRIBING COLOR AS A 3D COORDINATE

    double red;
    double green;
    double blue;

    public ColorCoordinate (double r, double g, double b) {
      this.red = r;
      this.green = g;
      this.blue = b;
    }

    private double dist (ColorCoordinate point) { //THIS IS A 3D DISTANCE CALCULATOR
      double d;
      double redSq = Math.pow((this.red-point.red), 2);
      double greenSq = Math.pow((this.green-point.green), 2);
      double blueSq = Math.pow((this.blue-point.blue), 2);
      d = Math.sqrt(redSq + greenSq + blueSq);
      return d;
    }

  }

  public char categorizeColor(double r, double g, double b) { //This returns the color found in uppercase
    /*
    Goal of this function: Take any 3D coordinate (representing an rgb color) and categorize it as either yellow, green, red, or blue
    (based on the rgb values of the colors in the competition)
    The RGB Values are:
    Blue: (0.18, 0.46, 0.37)
    Green: (0.2, 0.55, 0.24)
    Red: (0.46, 0.38, 0.16)
    Yellow: (0.32, 0.55, 0.13)
    So we're going to find which of the points above is closest to the color the sensor read in 3D space, and categorize the color
    that the sensor is reading
    */

    ColorCoordinate sensorC = new ColorCoordinate(r, g, b);

    ColorCoordinate blueC = new ColorCoordinate(0.18, 0.46, 0.37);
    ColorCoordinate greenC = new ColorCoordinate(0.2, 0.55, 0.24);
    ColorCoordinate redC = new ColorCoordinate(0.46, 0.38, 0.16);
    ColorCoordinate yellowC = new ColorCoordinate(0.32, 0.55, 0.13);

    /*
    * COLOR LIST: 
    * blue = 0, 
    * green = 1, 
    * red = 2, 
    * yellow = 3
    */ 

    ColorCoordinate[] colorList = {blueC, greenC, redC, yellowC}; //List of category colors

    int indexOfClosestCategory = 0;
    double closestDistance = sensorC.dist(colorList[0]);

    for (int i = 1; i < colorList.length; i++) {
      double di = sensorC.dist(colorList[i]);
      if (di < closestDistance) {
        indexOfClosestCategory = i;
        closestDistance = di;
      }
    }

    switch(indexOfClosestCategory) {
      case 0: 
        return 'B';
      case 1: 
        return 'G';
      case 2: 
        return 'R'; 
      case 3: 
        return 'Y'; 
      default: 
        return 'N'; 
    }
  }

  public void stop() {
    colorTalon.set(0); 
  }

  public void run(double speed) {
    colorTalon.set(speed); 
  }
}

