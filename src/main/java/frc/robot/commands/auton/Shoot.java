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
  private boolean destroyed; 
  private int numBalls; 

  public Shoot(Shooter shooter, Pulley pulley, int numBalls) {
    this.shooter = shooter; 
    this.pulley = pulley; 
    this.numBalls = numBalls; 
    destroyed = false; 
    ballHandler = new BallHandler();  
    addRequirements(shooter, pulley); 
  }

  @Override
  public void initialize() {
    for (int i = 0; i < numBalls; i++) {
      ballHandler.addBall(new Ball()); 
    }
    
    shooter.stopFlyWheel();
    shooter.stopShooter();
  }

  @Override
  public void execute() {
    shooter.toVelocity(8500);
    if(shooter.getVelocity() > 8300 && shooter.getVelocity() < 8700 && pulley.getTopSensor()) {
      shooter.runFlyWheel(); 
      if (!destroyed){
        System.out.println("DESTROYED!"); 
        ballHandler.removeHighestBall(); 
        destroyed = true; 
      }
    } else {
      shooter.stopFlyWheel(); 
    }

    if(!pulley.getTopSensor()) {
      pulley.run(.25); 
      destroyed = false; 
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

  @Override
  public boolean isFinished() {
    if(ballHandler.numBallsInRobot() == 0 && !destroyed)
      return true; 
    return false; 
  }
}
