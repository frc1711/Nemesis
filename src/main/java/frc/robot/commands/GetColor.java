/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ColorSensor;

public class GetColor extends CommandBase {
  
  private Color colorDetected;
  private ColorSensor colorSensor;

  public GetColor(ColorSensor colorSensor) {
    addRequirements(colorSensor);

    this.colorSensor = colorSensor;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
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

    public double dist (ColorCoordinate point) { //THIS IS A 3D DISTANCE CALCULATOR
      double d;
      double redSq = Math.pow((this.red-point.red), 2);
      double greenSq = Math.pow((this.green-point.green), 2);
      double blueSq = Math.pow((this.blue-point.blue), 2);
      d = Math.sqrt(redSq + greenSq + blueSq);
      return d;
    }

  }

  private String categorizeColor(double r, double g, double b) { //This returns the color found in uppercase
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
    String category = "";

    ColorCoordinate sensorC = new ColorCoordinate(r, g, b);

    ColorCoordinate blueC = new ColorCoordinate(0.18, 0.46, 0.37);
    ColorCoordinate greenC = new ColorCoordinate(0.2, 0.55, 0.24);
    ColorCoordinate redC = new ColorCoordinate(0.46, 0.38, 0.16);
    ColorCoordinate yellowC = new ColorCoordinate(0.32, 0.55, 0.13);

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

    if (indexOfClosestCategory == 0) {
      category = "BLUE";
    } else if (indexOfClosestCategory == 1) {
      category = "GREEN";
    } else if (indexOfClosestCategory == 2) {
      category = "RED";
    } else if (indexOfClosestCategory == 3) {
      category = "YELLOW";
    } else {
      category = "NOT_FOUND"; //This should never happen
    }

    return category;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    colorDetected = colorSensor.getColor();
    double r = colorDetected.red;
    double g = colorDetected.green;
    double b = colorDetected.blue;
    String categoryDetected = categorizeColor(r, g, b);

    //Category detected is the color the sensor reads, in uppercase

    System.out.println(categoryDetected);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

