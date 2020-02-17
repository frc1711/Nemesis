/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.RoboDir;
import frc.robot.subsystems.DriveTrain;

public class DriveCorrect extends CommandBase {
  /**
   * Creates a new DriveCorrect.
   */
  private DriveTrain driveTrain; 
  private double speed;
  private double rotationSpeed;  
  private double counts; 
  private boolean forward; 

  public DriveCorrect(DriveTrain driveTrain, double speed, double rotationSpeed, double inches) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrain); 
    this.driveTrain = driveTrain; 
    this.speed = speed; 
    this.rotationSpeed = rotationSpeed; 
    counts = driveTrain.inchToCount(inches); 

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
    System.out.println(driveTrain.getYaw()); 
    if(forward) { 
      //subtract encoder counts from target count to get dist away
       if(Math.abs(driveTrain.getYaw()) < 1){
        if(counts-encoderCounts > 100) { 
            driveTrain.drive(speed);
          } else {
            driveTrain.drive(speed*.5); 
          }
        } else {
          if(Math.abs(driveTrain.getYaw()) == driveTrain.getYaw()) {
            driveTrain.drive(rotationSpeed, RoboDir.RIGHT); 
          } else {
            driveTrain.drive(rotationSpeed, RoboDir.LEFT); 
          }
        }
      } else {
        if(Math.abs(driveTrain.getYaw()) < 2) {
          if(counts - encoderCounts < -100) {
            driveTrain.drive(-speed); 
          } else {
            driveTrain.drive(-speed*.5); 
          }
      } else {
        if(Math.abs(driveTrain.getYaw()) == driveTrain.getYaw()) {
          driveTrain.drive(rotationSpeed, RoboDir.LEFT); 
        } else {
          driveTrain.drive(rotationSpeed, RoboDir.RIGHT); 
        }
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double encoderCounts = driveTrain.getIndividualEncCount();    
    if(forward && counts - encoderCounts > 2){
      return false;
    } else if(!forward && counts- encoderCounts < 2)  {
      return false; 
    }else {
      //print final distance in NU counts from target
      System.out.println(Math.abs(counts-encoderCounts)); 
      System.out.println(counts-encoderCounts);
      return true; 
    }
  }
}
