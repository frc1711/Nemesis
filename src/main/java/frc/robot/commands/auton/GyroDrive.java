/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
/**
* @author: Lou DeZeeuw  
*/

public class GyroDrive extends CommandBase {
  private DriveTrain driveTrain; 
  private Joystick stick; 
  private double counts; 
  private double speed; 
  private boolean forward; 
  private int x; 

  public GyroDrive(DriveTrain driveTrain, double speed, double inches) {
    addRequirements(driveTrain);
    this.driveTrain = driveTrain;
    this.stick = null;  
    this.speed = speed; 
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

  private void print(String string) {
    if (x > 3) {
      System.out.println(string); 
      x = 0; 
    }
  }

  private double normalize(double angle, double max) {
      return Math.max(-max, Math.min(angle, max)); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double angle = driveTrain.getYaw(); 
    double encoderCounts = driveTrain.getAvgEncCount(driveTrain.getEncCount()); 

    if(forward) { 
        //subtract encoder counts from target count to get dist away
          if(counts-encoderCounts > 100) { 
            driveTrain.westCoast(speed, normalize(angle / 100, speed));
          } else {
            driveTrain.westCoast(speed*.5, normalize(angle / 100, speed * .5)); 
          }
        } else {
          if(counts - encoderCounts < -100) {
            driveTrain.westCoast(-speed, normalize(angle / 100, -speed));
          } else {
            driveTrain.westCoast(-speed * .5, normalize(angle / 100, -speed * .5));
          }
        }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("AUTONOMOUS STOPPED."); 
    driveTrain.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double encoderCounts = driveTrain.getIndividualEncCount();    
    if(stick != null && stick.getRawButtonReleased(2))
      return true; 

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
