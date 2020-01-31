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

public class Turn extends CommandBase {
  /**
   * Creates a new Turn.
   */

  private DriveTrain driveTrain; 
  private double speed; 
  private int angle; 
  private boolean forwards = true; 

  public Turn(DriveTrain driveTrain, double speed, int angle) {
    addRequirements(driveTrain);
    this.driveTrain = driveTrain; 
    this.speed = speed; 
    this.angle = angle; 
    if(Math.abs(angle) == angle) 
      forwards = false; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.zeroEncoders();
    driveTrain.zeroGyro(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(forwards) {
      if(angle - driveTrain.getYaw() > 15) {
        driveTrain.drive(speed, RoboDir.RIGHT); 
      } else {
        driveTrain.drive(speed * .5, RoboDir.RIGHT); 
      }
    } else {
      if(angle - driveTrain.getYaw() < -15) {
        driveTrain.drive(speed, RoboDir.LEFT); 
      } else {
        driveTrain.drive(speed * .5, RoboDir.LEFT); 
      }
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.zeroEncoders();
    driveTrain.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(angle) - Math.abs(driveTrain.getYaw()) < 2)
      return true; 
    return false;
  }
}
