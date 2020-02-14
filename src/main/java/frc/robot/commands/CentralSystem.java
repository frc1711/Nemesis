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

//TODO: reconfigure shooter system to work for manual and automated
public class CentralSystem extends CommandBase {
  /**
   * @author: Lou DeZeeuw
   */

  private Pulley pulley; 
  private Shooter shooter; 
  private BallHandler ballHandler; 
  private Intake intake;  
  private Joystick stick; 

  private boolean secondToggle; 
  private boolean shootMode; 
  private boolean created; 
  private boolean hold; 
  private boolean reverse; 
  private boolean destroyed; 
  private boolean manual; 
  
  int x = 0; 

  public CentralSystem(Pulley pulley, Shooter shooter, Intake intake, Joystick stick) {
    addRequirements(pulley, shooter, intake);
    
    this.pulley = pulley; 
    this.intake = intake; 
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

  private void print(String string) {
    /*Prints every sixty milliseconds instead of every twenty, 
    **diminishing lag and improving readability
    */
    x++;
    if (x > 3){  
      System.out.println(string);  
      x = 0; 
    }
  }

  private void flipButtons() {
    if(stick.getRawButtonReleased(4))
    reverse = !reverse; 

    if(stick.getRawButtonReleased(8)) 
      manual = !manual; 
  }
  
  private void automatedPulley() {
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
  }
  
  private void automatedShooter() {
    if(stick.getRawButtonReleased(1)){
      hold = !hold;
      shootMode = !shootMode;  
    }

    if(hold) { 
      shooter.toVelocity(7000);
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
  }

  private void flyWheel() {
    if(stick.getRawButton(2) && (shootMode || manual)) {
      shooter.runFlyWheel();
    } else {
      shooter.stopFlyWheel();
    }
  }

  private void removeAllBalls() {
    if(stick.getRawButton(3)) {
      ballHandler.removeHighestBall(); 
    }
  }

  private void intake() {
    if(!pulley.getBottomSensor() || manual) {
      if(stick.getRawButton(5))
        intake.run(.4); 
      else if (stick.getRawButton(6))
        intake.run(-.4);
      else
        intake.stop(); 
    } else {
      intake.stop(); 
    }
  }

  private void reverse() {
    pulley.run(-.25); 

    if(pulley.getBottomSensor() && !destroyed) {
      ballHandler.removeHighestBall(); 
      destroyed = true; 
    }

    if(!pulley.getBottomSensor()) {
        destroyed = false; 
    }
  }

  private void manualPulley() {
    if(stick.getRawButton(4)) {
      pulley.run(-.25); 
    } else if(stick.getRawButton(3)) {
      pulley.run(.25); 
    } else {
      pulley.run(0); 
    }
  }

  private void manualShooter() {
    if(stick.getRawButton(1)){
      shooter.toVelocity(7000);
    } else {
      shooter.toVelocity(0); 
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    print("VELOCITY: " + shooter.getVelocity()); 
    print("DC: " + shooter.getDutyCycle()); 

    flipButtons(); 
    removeAllBalls(); 
    intake(); 
    flyWheel(); 
    
    if(!manual) {
      if(!reverse){
        automatedPulley(); 
        automatedShooter(); 
      } else {
        reverse(); 
      }
    } else {
      print("WARNING: MANUAL MODE."); 
      manualPulley(); 
      manualShooter(); 
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
