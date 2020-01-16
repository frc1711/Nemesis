/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class RunShooter extends CommandBase {
  /**
   * Creates a new RunShooter.
   */

  private final Shooter shooter; 
  int x; 

  private final Joystick stick; 

  public RunShooter(Shooter shooter, Joystick stick) {
    addRequirements(shooter);
    this.stick = stick; 
    this.shooter = shooter; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    x = 0; 
  }
  //-5827
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    x++;
    if (x > 3){  
      System.out.println("RPMa: " + shooter.getVelocity()); 
      x = 0; 
    }
    
    if(stick.getRawButton(1)) { 
      shooter.toVelocity(7000);
    } else {
      shooter.stop(); 
    }
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
