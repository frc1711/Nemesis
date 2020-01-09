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

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  private WPI_TalonSRX shooterTalon; 
  private WPI_TalonSRX altShooterTalon; 

  public Shooter() {
    shooterTalon = new WPI_TalonSRX(Constants.shooter); 
    altShooterTalon = new WPI_TalonSRX(Constants.shooterTwo); 
  }

  public void forwardShoot(double speed) {
    System.out.println("B");
    shooterTalon.set(speed); 
    altShooterTalon.set(speed); 
  }

  public void backwardShoot(double speed) {
    shooterTalon.set(-speed); 
    altShooterTalon.set(-speed); 
  }

  public void stop() {
    shooterTalon.set(0); 
    altShooterTalon.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
