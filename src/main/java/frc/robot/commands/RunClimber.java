/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Climber;

public class RunClimber extends CommandBase {
  /**
   * Creates a new RunClimber.
   */
  private DoubleSupplier speed; 
  private Climber climber; 

  public RunClimber(Climber climber, DoubleSupplier speed) {
    addRequirements(climber); 
    this.climber = climber; 
    this.speed = speed;
  }

  @Override
  public void initialize() {
    // This method will be called once per scheduler run
  }

  @Override
  public void execute() {
    if(Math.abs(speed.getAsDouble()) > .01)
      climber.set(speed.getAsDouble()); 
    else 
      climber.stop(); 
  
  }

  @Override
  public boolean isFinished() {
    return false; 
  }
}
