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
import frc.robot.helper_classes.PIDHelp;

/** 
* @author: Lou DeZeeuw, Gabriel Seaver
*/

public class Shooter extends SubsystemBase implements PIDHelp {
  /**
   * Creates a new Shooter.
   */
  private WPI_TalonSRX shooterTalon;
  private WPI_TalonSRX altShooterTalon;

  private WPI_TalonSRX flyWheel;

  

  public Shooter() {
    shooterTalon = new WPI_TalonSRX(Constants.shooter); 
    altShooterTalon = new WPI_TalonSRX(Constants.shooterTwo); 
    flyWheel = new WPI_TalonSRX(Constants.flyWheel);
  
    shooterTalon.setSafetyEnabled(false); 
    altShooterTalon.setSafetyEnabled(false); 
    altShooterTalon.set(ControlMode.Follower, Constants.shooter);
    
    shooterTalon.config_kP(0, Constants.shooterkP); 
    shooterTalon.config_kI(0, Constants.shooterkI); 
    shooterTalon.config_kD(0, Constants.shooterkD);
    shooterTalon.config_kF(0, Constants.shooterkF);
  }

  //SHOOTER MOTORS
  public void forwardShoot(double speed) {
    shooterTalon.set(speed); 
  }

  public void stopShooter() {
    shooterTalon.set(ControlMode.PercentOutput, 0); 
  }

  public double getDutyCycle() {
    return shooterTalon.getMotorOutputVoltage(); 
  }

  public double getRPM() {
    return PIDHelp.getRPM(shooterTalon); 
  }

  public double getVelocity() {
    return PIDHelp.getVelocity(shooterTalon);  
  }

  public void toVelocity(double velocity) {
    PIDHelp.toVelocity(shooterTalon, velocity);
  }

  public void toRPM(double RPM) {
    PIDHelp.toRPM(shooterTalon, RPM); 
  }

  //FLY-WHEEL KICKER
  public void runFlyWheel() {
    flyWheel.set(Constants.flyWheelSpeed);
  }

  public void stopFlyWheel() {
    flyWheel.set(0);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
