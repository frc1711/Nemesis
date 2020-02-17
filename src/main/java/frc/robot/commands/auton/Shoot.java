/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helper_classes.Ball;
import frc.robot.helper_classes.BallHandler;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {
  
  private Shooter shooter; 
  private Pulley pulley; 
  private BallHandler ballHandler; 

  public Shoot(Shooter shooter, Pulley pulley) {
    this.shooter = shooter; 
    this.pulley = pulley; 
    ballHandler = new BallHandler(); 
    ballHandler.addBall(new Ball()); 
    
    addRequirements(shooter, pulley); 
  }

  @Override
  public void initialize() {

    shooter.stopFlyWheel();
    shooter.stopShooter();
  }

  @Override
  public void execute() {
    shooter.toVelocity(8500);
    System.out.println(shooter.getVelocity()); 
    if(shooter.getVelocity() > 8300 && shooter.getVelocity() < 8700 && shooter.getTopSensor()) {
      shooter.runFlyWheel(); 
    } else {
      shooter.stopFlyWheel(); 
    }

    if(!shooter.getTopSensor()) {
      pulley.run(.25); 
    } else {
      pulley.run(0); 
    }
  }

  @Override
  public void end(boolean interrupted) {
    pulley.stop(); 
    shooter.stopShooter();
    shooter.stopFlyWheel(); 
  }
}
