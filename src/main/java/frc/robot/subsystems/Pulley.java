/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pulley extends SubsystemBase {
  
  WPI_TalonSRX pulleyTalon;

  public static final int timeToIndex = Constants.timeToIndex;

  public Pulley() {
    pulleyTalon = new WPI_TalonSRX(Constants.pulley);
  }

  public void startMoving() {
    pulleyTalon.set(Constants.pulleySpeed);
  }

  public void stopMoving() {
    pulleyTalon.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
