/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pulley;

public class PulleyButton extends CommandBase {
  /**
   * Creates a new PulleyButton.
   */

  private Pulley pulleySystem; 
  private double speed; 
  int x = 0; 

  public PulleyButton(Pulley pulleySystem, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(pulleySystem);
    
    this.pulleySystem = pulleySystem; 
    this.speed = speed; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pulleySystem.stop(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // x++;
    // if (x > 3){  
    //   System.out.println("Velocity: " + pulleySystem.getVelocity()); 
    //   x = 0; 
    // }
    
    pulleySystem.toVelocity(1000); 
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
