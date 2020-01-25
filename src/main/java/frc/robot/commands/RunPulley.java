/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pulley;
/** 
* @author: Gabriel Seaver, Lou DeZeeuw
*/ 
public class RunPulley extends CommandBase {
  
  Pulley pulleySystem;

  private int time;
  private double speed; 

  public RunPulley(Pulley pulleySystem, double speed, int time) {
    addRequirements(pulleySystem);

    this.pulleySystem = pulleySystem;
    this.speed = speed; 
    this.time = time; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pulleySystem.run(speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    time--;
    if (time <= 0) {
      pulleySystem.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    pulleySystem.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
