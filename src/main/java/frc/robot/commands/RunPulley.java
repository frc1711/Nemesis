/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helper_classes.Ball;
import frc.robot.helper_classes.BallHandler;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;

public class RunPulley extends CommandBase {
  /**
   * Creates a new PulleyButton.
   */

  private Pulley pulley; 
  private Shooter shooter; 
  private BallHandler ballHandler; 
  private Joystick stick; 

  private boolean secondToggle; 
  private boolean shootMode; 
  private boolean created; 
  private boolean hold; 
  private boolean reverse; 
  private boolean destroyed; 
  private boolean manual; 
  
  int x = 0; 

  public RunPulley(Pulley pulley, Shooter shooter, Joystick stick) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(pulley, shooter);
    
    this.pulley = pulley; 
    this.shooter = shooter; 
    this.stick = stick; 
    hold = false; 
    shootMode = false; 
    destroyed = false; 
    created = false; 
    secondToggle = false; 
    reverse = false; 
    manual = false; 

    ballHandler = new BallHandler(); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pulley.stop(); 
    shooter.stopShooter(); 
    shooter.stopFlyWheel(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    x++;
    if (x > 3){  
      System.out.println("BALLS: " + ballHandler.numBallsInRobot());  
      x = 0; 
    }

    if(stick.getRawButtonReleased(4))
      reverse = !reverse; 

    if(stick.getRawButtonReleased(8)) 
      manual = !manual; 
    
    if(!manual) {
      if(!reverse){
        //automated ball system
        if (pulley.getBottomSensor() && !created) {
          ballHandler.addBall(new Ball());
          created = true; 
        }

        Ball lastBall = ballHandler.getLastBallHandled(); 

        if(pulley.getMiddleSensor()) {
          if(!secondToggle && ballHandler.numBallsInRobot() > 0) {
            lastBall.setPastSensor(true); 
            secondToggle = true; 
          }
        } else {
          secondToggle = false; 
        }

        if(ballHandler.numBallsInRobot() == 1) {
          if(!lastBall.getPastSensor()) {
            pulley.run(.25); 
          } else {
            pulley.stop(); 
            created = false; 
          }
        } else if (ballHandler.numBallsInRobot() > 1 && ballHandler.getSecondToLastBallHandled().getPastSensor() && !lastBall.getPastSensor()) {
          pulley.run(.25); 
        } else {
          pulley.stop(); 
          created = false; 
        }

        //automated shooting system
          if(stick.getRawButtonReleased(1)){
            hold = !hold;
            shootMode = !shootMode;  
          }

          if(hold) { 
            shooter.toVelocity(-31300);
            shootMode = true; 
          } else {
            shooter.stopShooter(); 
          }
      
          if(!shooter.getTopSensor()) {
            destroyed = false; 
            if(shootMode) {
              pulley.run(.25); 
            }
          }

          if(shooter.getTopSensor()){
            pulley.stop(); 
            if(!destroyed) {
                ballHandler.removeHighestBall(); 
                destroyed = true; 
            }
          } 

          if(stick.getRawButton(3)) {
              ballHandler.removeHighestBall(); 
          }

          if(stick.getRawButton(2) && shootMode) {
            shooter.runFlyWheel();
          } else {
            shooter.stopFlyWheel();
          }
      } else {
        pulley.run(-.25); 

        if(pulley.getBottomSensor() && !destroyed) {
          ballHandler.removeHighestBall(); 
          destroyed = true; 
        }

        if(!pulley.getBottomSensor()) {
            destroyed = false; 
        }
      }
    } else {
      if (x > 3)
        System.out.println("WARNING: MANUAL MODE."); 
      if(stick.getRawButton(4)) {
        pulley.run(-.25); 
      } else if(stick.getRawButton(3)) {
        pulley.run(.25); 
      } else {
        pulley.run(0); 
      }

      if(stick.getRawButton(1)){
        shooter.toVelocity(-31300);
      } else {
        shooter.toVelocity(0); 
      }

      if(stick.getRawButton(2)) {
        shooter.runFlyWheel(); 
      } else {
        shooter.stopFlyWheel();
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    pulley.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
