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
 

  private final Joystick stick; 

  public RunShooter(Shooter shooter, Joystick stick) {
    addRequirements(shooter);
    this.stick = stick; 
    this.shooter = shooter; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }
  //-5827
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("RPMa: " + shooter.getVelocity()); 

    if(stick.getRawButton(1)) { 
      shooter.toVelocity(1000);
    } else if(stick.getRawButton(2)) {
      shooter.forwardShoot(.31); 
    } else if(stick.getRawButton(3)) {
      shooter.forwardShoot(.30); 
    } else if(stick.getRawButton(4)) {
      shooter.backwardShoot(.2); 
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
