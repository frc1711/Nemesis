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
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;

public class ReworkedCentralSystem extends CommandBase {
  /**
   * Creates a new ReworkedCentralSystem.
   */
  
  private Pulley pulley; 
  private Shooter shooter; 
  private BallHandler ballHandler; 
  private Intake intake;  
  private Joystick stick; 

  private boolean pastMiddle; 
  private boolean shootMode; 
  private boolean hold; 
  private boolean created; 
  private boolean reverse; 
  private boolean destroyed; 
  private boolean manual; 
  
  
  private int x; 
  private int timePastSensor; 
  
  public ReworkedCentralSystem() {
    addRequirements(pulley, shooter, intake);
    
    this.pulley = pulley; 
    this.intake = intake; 
    this.shooter = shooter; 
    this.stick = stick; 

    manual = false; 

    x = 0; 

    ballHandler = new BallHandler();   }

  // Called when the command is initially scheduled.
  
  @Override
  public void initialize() {
    defaultButtons(); 
    manual = false; 
  }

  private void print(String string) {
    /*
    * Due to FRC's 20 millisecond loop, this changes the print time from
    * 20 milliseconds to 60 milliseconds, greatly improving general readability
    * and reducing lag in the system. 
    */
    x++;
    timePastSensor++; 

    if (x > 3){  
      System.out.println(string);  
      x = 0; 
    }
  }

  private void defaultButtons() {
    shootMode = false; 
    destroyed = false; 
    created = false; 
    pastMiddle = false; 
    reverse = false; 
    hold = false; 
  }

  private void flipButtons() {
    if(stick.getRawButtonReleased(4))
      reverse = !reverse; 

    if(stick.getRawButtonReleased(8)) {
      manual = !manual; 
      defaultButtons(); 
    }
  }

  private void CreateBall() {
    if(pulley.getBottomSensor() && !created) {
      ballHandler.addBall(new Ball()); 
      timePastSensor = 0; 
      created = true; 
    }

    if(!pulley.getBottomSensor() && created) {
      created = false; 
    }
  }

  private void AutomatedPulley() {
    if(ballHandler.numBallsInRobot() < 2) {
      Ball lastBall = ballHandler.getLastBallHandled(); 
      if(!lastBall.getPastSensor() && timePastSensor < 50) {
        pulley.run(.25); 
      } else {
        pulley.stop(); 
      }
    }

  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
