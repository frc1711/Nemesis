/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveSystem.
   */

  private AHRS gyro; 

  private SpeedController frontRightDrive;
  private SpeedController frontLeftDrive; 
  private SpeedController rearRightDrive; 
  private SpeedController rearLeftDrive; 
  
  private SpeedControllerGroup rightDrive; 
  private SpeedControllerGroup leftDrive; 

  private DifferentialDrive rDrive; 

  Boolean sparkMaxUse; 

  public DriveTrain(Boolean sparkMaxUse) {
    this.sparkMaxUse = sparkMaxUse; 

    if (sparkMaxUse) {
      frontRightDrive = new CANSparkMax(Constants.frd, MotorType.kBrushless); 
      frontLeftDrive = new CANSparkMax(Constants.fld, MotorType.kBrushless); 
      rearRightDrive = new CANSparkMax(Constants.frd, MotorType.kBrushless); 
      rearLeftDrive = new CANSparkMax(Constants.frd, MotorType.kBrushless); 
    } else {
      frontRightDrive = new WPI_TalonSRX(Constants.frd); 
      frontLeftDrive = new WPI_TalonSRX(Constants.fld); 
      rearRightDrive = new WPI_TalonSRX(Constants.rrd); 
      rearLeftDrive = new WPI_TalonSRX(Constants.rld); 
    }

    rightDrive = new SpeedControllerGroup(frontRightDrive, rearRightDrive); 
    leftDrive = new SpeedControllerGroup(frontLeftDrive, rearLeftDrive); 

    rDrive = new DifferentialDrive(rightDrive, leftDrive); 

  }

  public Boolean getSparkMaxUse() {
    return sparkMaxUse; 
  }

  public void stop() {
    frontRightDrive.set(0);
    frontLeftDrive.set(0);
    rearRightDrive.set(0);
    rearLeftDrive.set(0); 
  }

  public void rawWestCoast(double speed, double rot) {
    if(Math.abs(speed) > .01 || Math.abs(rot) > .01)
      rDrive.arcadeDrive(speed, rot); 
    else 
      stop();
  }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
