/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.helper_classes;

/**
 * Add your docs here.
 */
public class Ball {
    private static int ballTotal = 0;
    private int ballID; 
    private boolean pastSensor; 

    public Ball() {
        ballID = ballTotal; 
        ballTotal++; 
        pastSensor = false; 
    }

    public static int getTotBall() {
        return ballTotal; 
    }

    public int getID() {
        return ballID; 
    }

    public boolean getPastSensor() {
        return pastSensor;  
    }

    public void setPastSensor(boolean pastSensor) {
        this.pastSensor = pastSensor; 
    }
}
