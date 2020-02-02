/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helper_classes.Ball;
import frc.robot.helper_classes.BallHandler;
import frc.robot.subsystems.Pulley;

public class RunPulley extends CommandBase {
  /**
   * Creates a new PulleyButton.
   */

  private Pulley pulleySystem; 
  private BallHandler ballHandler; 
  private double speed; 
  private boolean secondToggle; 
  private boolean created; 
  int x = 0; 

  public RunPulley(Pulley pulleySystem, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(pulleySystem);
    
    this.pulleySystem = pulleySystem; 
    this.speed = speed; 
    ballHandler = new BallHandler(); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pulleySystem.stop(); 
    created = false; 
    secondToggle = false; 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    x++;
    if (x > 3){  
      System.out.println("SENSOR: " + pulleySystem.getMiddleSensor()); 
      x = 0; 
    }


    //automated ball system
    if (pulleySystem.getBottomSensor() && !created) {
      ballHandler.addBall(new Ball());
      created = true; 
    }

    Ball lastBall = ballHandler.getLastBallHandled(); 
    System.out.println(Ball.getTotBall()); 

    if(pulleySystem.getMiddleSensor() && !secondToggle) {
      lastBall.setPastSensor(true); 
      secondToggle = true; 
    } 
    if (!pulleySystem.getMiddleSensor()) {
      secondToggle = false; 
    }

    if(ballHandler.numBallsInRobot() == 1) {
      if(!lastBall.getPastSensor()) {
        pulleySystem.run(.50); 
      } else {
        pulleySystem.stop(); 
        created = false; 
      }
    } else if (ballHandler.numBallsInRobot() > 1 && ballHandler.getSecondToLastBallHandled().getPastSensor() && !lastBall.getPastSensor()) {
      pulleySystem.run(.50); 
    } else {
      pulleySystem.stop(); 
      created = false; 
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
