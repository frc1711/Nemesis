/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
/**
* @author: Lou DeZeeuw  
*/

public class Drive extends CommandBase {
  DriveTrain driveTrain; 
  double counts; 
  double speed; 
  double timeout; 
  boolean forward; 

  public Drive(DriveTrain driveTrain, double speed, double inches, double seconds) {
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
    driveTrain.zeroGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double encoderCounts = driveTrain.getAvgEncCount(driveTrain.getEncCount());    
    
    if(forward) { 
    //subtract encoder counts from target count to get dist away
      if(counts-encoderCounts > 100) { 
        driveTrain.drive(speed);
      } else {
        driveTrain.drive(speed*.5); 
      }
    } else {
      if(counts - encoderCounts < -100) {
        driveTrain.drive(-speed); 
      } else {
        driveTrain.drive(-speed*.5); 
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
    
    if(Math.abs(counts-encoderCounts) > 2){
      return false;
    } else {
      //print final distance in NU counts from target
      System.out.println(Math.abs(counts-encoderCounts)); 
      System.out.println(counts-encoderCounts);
      return true; 
    }

  }
}
