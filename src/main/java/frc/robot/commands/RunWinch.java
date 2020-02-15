/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Winch;

public class RunWinch extends CommandBase {
  /**
   * Creates a new RunWinch.
   */
  private Winch winch; 
  private DoubleSupplier speed; 
  private double initialLocation; 
  private int i;
  private int multiplier; 
  private double change; 
  private double[] arr; 
  private boolean checked; 

  public RunWinch(Winch winch, DoubleSupplier speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(winch);
    this.winch = winch; 
    this.speed = speed; 
    initialLocation = winch.getPosition(); 
    i = 0; 
    multiplier = 1; 
    arr = new double[4]; 
    checked = false; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Math.abs(speed.getAsDouble()) > .1 && !checked) {
      arr[i] = winch.getPosition(); 
      i++; 
    }

    if(i > 3) {
      double firstInst = arr[3] - arr[2]; 
      double secondInst = arr[2] - arr[1]; 
      double thirdInst = arr[1] - arr[0]; 

      if(firstInst < change || secondInst < change || thirdInst < change) {
        multiplier = -1; 
      }

      i = 0; 
    }

    if(Math.abs(speed.getAsDouble()) > .1) 
      winch.run(speed.getAsDouble() * multiplier); 
    else
      winch.run(0); 
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
