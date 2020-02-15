/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.helper_classes.PIDHelp;

public class Winch extends SubsystemBase {
  /**
   * Creates a new Winch.
   */
  private CANSparkMax winchSpark; 

  public Winch() {
    winchSpark = new CANSparkMax(11, MotorType.kBrushless); 

  }

  public void run(double speed) {
    winchSpark.set(speed); 
  }

  public double getPosition() {
    return PIDHelp.getPosition(winchSpark); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
