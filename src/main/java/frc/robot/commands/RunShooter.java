/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

/** 
 * @author: Lou DeZeeuw, Gabriel Seaver
*/

public class RunShooter extends CommandBase {
  private final Shooter shooter;

  int x; 
  boolean hold; 

  private final Joystick stick; 

  public RunShooter(Shooter shooter, Joystick stick) {
    addRequirements(shooter);
    this.stick = stick; 
    this.shooter = shooter; 
    hold = false; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.stopShooter(); 
    shooter.stopFlyWheel(); 
    x = 0; 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    x++;
    if (x > 3){  
      System.out.println("Velocity: " + shooter.getVelocity()); 
      x = 0; 
    } 

    if(stick.getRawButtonReleased(1)) 
      hold = !hold; 
    
    if(hold) { 
      shooter.toVelocity(-31300);
    } else {
      shooter.stopShooter(); 
    }


    if(stick.getRawButton(2) && shooter.getTopSensor()) {
      shooter.runFlyWheel();
    } else {
      shooter.stopFlyWheel();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter(); 
    shooter.stopFlyWheel();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
