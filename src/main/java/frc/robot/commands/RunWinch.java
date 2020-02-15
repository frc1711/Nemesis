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
    change = .1; 
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
    /*
    * As all if statements in FRC that use command based robots act
    * as while loops, this loop checks to see if the user is commanding
    * the winch to a position. It then adds the position to an array, so 
    * that the position can be compared later on. This loop runs for 60 ms.    
    */
    if(Math.abs(speed.getAsDouble()) > .1 && !checked) {
      arr[i] = winch.getPosition(); 
      i++; 
    }

    /*
    * After the loop has run for 60 ms, the amount of change is compared 
    * to a set "change" value. This verifies that the winch is moving in 
    * the correct direction and not back driving. If it is back driving, 
    * the program switches the direction of the motor and cancels the loop. 
    */
    if(i > 3) {
      double firstInst = arr[3] - arr[2]; 
      double secondInst = arr[2] - arr[1]; 
      double thirdInst = arr[1] - arr[0]; 

      if(Math.abs(firstInst) < change || Math.abs(secondInst) < change || Math.abs(thirdInst) < change) {
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
