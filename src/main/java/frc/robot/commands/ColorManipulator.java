/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ColorManager;

public class ColorManipulator extends CommandBase {
  /**
   * Creates a new ColorManager.
   */
  private ColorManager colorManager; 

  public ColorManipulator(ColorManager colorManager) {
    this.colorManager = colorManager; 
  }

  private char getGameData() {
    try {
      return DriverStation.getInstance().getGameSpecificMessage().charAt(0); 
    } catch (Exception e) {
      return ' '; 
    }
  }

  private char getColor() {
    Color detectedColor = colorManager.getColor(); 
    double r = detectedColor.red; 
    double g = detectedColor.green; 
    double b = detectedColor.blue; 
    return colorManager.categorizeColor(r, g, b); 
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorManager.stop(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    colorManager.run(Constants.colorSpeed); 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    colorManager.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(getGameData() == getColor())
      return true; 
    return false;
  }
}
