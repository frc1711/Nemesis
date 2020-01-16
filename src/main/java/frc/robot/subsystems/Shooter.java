/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
  
    shooterTalon.setSafetyEnabled(false); 
    altShooterTalon.setSafetyEnabled(false); 
    altShooterTalon.set(ControlMode.Follower, Constants.shooter);
    
    shooterTalon.config_kP(0, Constants.shooterkP); 
    shooterTalon.config_kI(0, Constants.shooterkI); 
    shooterTalon.config_kD(0, Constants.shooterkD);
    shooterTalon.config_kF(0, Constants.shooterkF);
    
  }

  public void forwardShoot(double speed) {
    shooterTalon.set(speed); 
  }

  public void backwardShoot(double speed) {
    shooterTalon.set(-speed); 
  }

  public void stop() {
    shooterTalon.set(ControlMode.PercentOutput, 0); 
  }

  public double getRPM() {
    double nativeUnitVelocity = shooterTalon.getSelectedSensorVelocity(); 
    nativeUnitVelocity /= .1;
    nativeUnitVelocity /= 4096;
    nativeUnitVelocity *= 60; 
    return nativeUnitVelocity; 
  }

  public double getVelocity() {
    return shooterTalon.getSelectedSensorVelocity(); 
  }

  public void toVelocity(double velocity) {
    shooterTalon.set(ControlMode.Velocity, velocity); 
  }

  public void toRPM(double velocity) {
    double RPM = velocity * 4096; 
    shooterTalon.set(ControlMode.Velocity, RPM); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
