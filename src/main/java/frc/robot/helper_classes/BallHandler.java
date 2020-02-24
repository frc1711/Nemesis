/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.helper_classes;

import java.util.ArrayList;

/**
 * Add your docs here.
 */
public class BallHandler {
    ArrayList<Ball> ballList; 

    public BallHandler() {
        ballList = new ArrayList<Ball>(); 
    }

    public void addBall(Ball ball) {
        ballList.add(ball); 
    }

    public void addBall(Ball... ball) {
        for(Ball b : ball) 
            ballList.add(b); 
    }
    
    public void removeBall(int ballID) {
        for(Ball b : ballList) {
            if(b.getID() == ballID) {
                ballList.remove(ballList.indexOf(b));
            }
        }
    }

    public void removeHighestBall() {
        if (ballList.size() != 0)
            ballList.remove(ballList.size() -1); 
    }

    public int numBallsInRobot() {
        return ballList.size(); 
    }

    public int totNumBallsHandled() {
        return Ball.getTotBall(); 
    }

    public Ball getLastBallHandled() {
        if(ballList.size() >= 1)
            return ballList.get(ballList.size() -1); 
        else
            return null;
    }

    public Ball getSecondToLastBallHandled() {
        if(ballList.size() >= 2)
            return ballList.get(ballList.size() -2);
        else 
            return null;  
    }

}
