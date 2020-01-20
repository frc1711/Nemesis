/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveForward extends CommandBase {
  /**
   * Creates a new DriveForward.
   */
  DriveTrain driveTrain; 
  double counts; 
  double speed; 
  double timeout; 

  public DriveForward(DriveTrain driveTrain, double inches, double speed, double seconds) {
    addRequirements(driveTrain); 
    this.driveTrain = driveTrain; 
    this.speed = speed; 
    timeout = seconds * 1000;  //time in seconds, system in millis 
    counts = driveTrain.inchToCount(inches); //converting inches to encoder counts
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
    if(counts - driveTrain.getAvgEncCount() < 500) { //this value may have to change. It's a sixteenth of an inch. 
      driveTrain.driveStatic(.5 * speed); 
    } else {
      driveTrain.driveStatic(speed); 
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(counts > 0 && counts - driveTrain.getAvgEncCount() > 50) 
      return false; 
    else  
      return true; 
    //TODO: Write negative direction
  }
}
