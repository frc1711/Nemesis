/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.helper_classes.Ball;
import frc.robot.helper_classes.BallHandler;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pulley;

public class AutonCentralSystem extends CommandBase {
  /**
   * Creates a new Intake.
   */
  private Intake intake; 
  private Pulley pulley; 
  private BallHandler ballHandler; 
  
  private boolean created; 
  private boolean pastMiddle;
  
  private int sinceStart; 
  private int timeout; 

  public AutonCentralSystem(Intake intake, Pulley pulley, int timeout) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake, pulley); 
    this.intake = intake; 
    this.pulley = pulley; 
    ballHandler = new BallHandler(); 
    this.timeout = timeout * 50; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    sinceStart = 0; 
    ballHandler.addBall(new Ball(true), new Ball(true), new Ball(true)); 
    intake.stop(); 
    pulley.stop(); 
    created = true;
    pastMiddle = false;  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println(ballHandler.numBallsInRobot()); 
    sinceStart++; 
    intake.run(Constants.intakeSpeed); 

    if(pulley.getBottomSensor() && !created) {
      System.out.println("A"); 
      ballHandler.addBall(new Ball()); 
      created = true; 
    } else if (!pulley.getBottomSensor()) {
      created = false; 
    }

    if(ballHandler.getSecondToLastBallHandled().getPastSensor() && !ballHandler.getLastBallHandled().getPastSensor())
      pulley.run(Constants.pulleySpeed); 

    if(pulley.getMiddleSensor() && !pastMiddle && !pulley.getBottomSensor()) {
      System.out.println("B"); 
      ballHandler.getLastBallHandled().setPastSensor(true);
      pulley.stop(); 
      pastMiddle = true; 
    } else {
      pastMiddle = false; 
    }

    if(!ballHandler.getLastBallHandled().getPastSensor() && !pulley.getTopSensor()) {
      System.out.println("C"); 
      pulley.run(Constants.pulleySpeed); 
    } else {
      pulley.stop(); 
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stop(); 
    pulley.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (sinceStart >= timeout) {
      ballHandler.removeAllBalls();
      return true; 
    } else if (ballHandler.numBallsInRobot() == 5) {
      ballHandler.removeAllBalls(); 
      return true; 
    }
    return false;
  }
}
