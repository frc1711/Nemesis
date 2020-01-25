/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveForward extends CommandBase {
  
  DriveTrain driveTrain; 
  double counts; 
  double speed; 
  double timeout; 
  boolean forward; 

  public DriveForward(DriveTrain driveTrain, double inches, double speed, double seconds) {
    addRequirements(driveTrain); 
    this.driveTrain = driveTrain; 
    this.speed = speed; 
    timeout = seconds * 1000;  //time in seconds, system in millis 
    counts = driveTrain.inchToCount(inches); //converting inches to encoder counts
    if(Math.abs(counts) != counts) 
      forward = false; 
    else
      forward = true; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.stop(); 
    driveTrain.zeroEncoders(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double encoderCounts = driveTrain.getAvgEncCount(driveTrain.getEncCount());    
    if(forward) {
      if(counts-encoderCounts > 10) {
        driveTrain.drive(speed);
      } else {
        driveTrain.drive(speed*.5); 
      }
    } else {
      if(counts - encoderCounts > 1) {
        driveTrain.drive(-speed); 
      } else {
        driveTrain.drive(-speed*.5); 
        System.out.println("A"); 
      }
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("AUTONOMOUS STOPPED."); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double encoderCounts = driveTrain.getIndividualEncCount();    
    if(Math.abs(counts-encoderCounts) > 1)
      return false;
    else 
      return true; 
  }
}
