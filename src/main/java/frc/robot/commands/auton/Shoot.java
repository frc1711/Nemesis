/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {
  
  private Shooter shooter; 
  private Pulley pulley; 

  public Shoot(Shooter shooter, Pulley pulley) {
    this.shooter = shooter; 
    this.pulley = pulley; 
  }

  @Override
  public void initialize() {
    shooter.stopFlyWheel();
    shooter.stopShooter();
  }

  @Override
  public void execute() {
    shooter.toVelocity(-31300);

    if(shooter.getVelocity() < -3100 && shooter.getVelocity() > -31700 && shooter.getTopSensor()) {
      shooter.runFlyWheel(); 
    } else {
      shooter.stopFlyWheel(); 
    }

    if(!shooter.getTopSensor()) {
      pulley.run(.5); 
    }
  }
}
