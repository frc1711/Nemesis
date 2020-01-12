/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class WestCoastDrive extends CommandBase {

  private final DriveTrain driveTrain; 
  private final DoubleSupplier speed; 
  private final DoubleSupplier rot; 

  public WestCoastDrive(DriveTrain driveTrain, DoubleSupplier speed, DoubleSupplier rot) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrain);

    this.driveTrain = driveTrain; 
    this.speed = speed; 
    this.rot = rot; 

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveTrain.rawWestCoast(speed.getAsDouble(), rot.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
