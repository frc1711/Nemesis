/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class DriveTrain extends SubsystemBase {

  private AHRS gyro; 

  private CANSparkMax frontRightDrive;
  private CANSparkMax frontLeftDrive; 
  private CANSparkMax rearRightDrive; 
  private CANSparkMax rearLeftDrive; 
  
  private SpeedControllerGroup rightDrive; 
  private SpeedControllerGroup leftDrive; 

  private DifferentialDrive rDrive; 

  private CANEncoder frEncoder; 
  private CANEncoder flEncoder; 
  private CANEncoder rrEncoder; 
  private CANEncoder rlEncoder; 
  
  private CANEncoder[] encArr = {frEncoder, flEncoder, rrEncoder, rlEncoder}; 

  private static double kWheelRadius; 
  private static double kGearRatio; 


  Boolean sparkMaxUse; 

  public DriveTrain(double wheelRadius, double gearRatio) {
    //MOTOR DEFINITIONS
    frontRightDrive = new CANSparkMax(Constants.frd, MotorType.kBrushless); 
    frontLeftDrive = new CANSparkMax(Constants.fld, MotorType.kBrushless); 
    rearRightDrive = new CANSparkMax(Constants.frd, MotorType.kBrushless); 
    rearLeftDrive = new CANSparkMax(Constants.frd, MotorType.kBrushless); 

    //ENCODER DEFINITIONS
    frEncoder = frontRightDrive.getEncoder(); 
    flEncoder = frontLeftDrive.getEncoder(); 
    rrEncoder = rearRightDrive.getEncoder(); 
    rlEncoder = rearLeftDrive.getEncoder(); 

    rightDrive = new SpeedControllerGroup(frontRightDrive, rearRightDrive); 
    leftDrive = new SpeedControllerGroup(frontLeftDrive, rearLeftDrive); 

    rDrive = new DifferentialDrive(rightDrive, leftDrive); 
    gyro = new AHRS(Port.kUSB); 

    kWheelRadius = wheelRadius; 
    kGearRatio = gearRatio; 
  }

  //AUTON MATH
  private double countsPerRot() {
    return frEncoder.getCountsPerRevolution(); 
  }

  private double inchPerRot(double wheelRadius, double gearRatio) {
    return (wheelRadius / gearRatio); 
  }
  
  //AUTON CONVERSION METHODS
  public double inchToCount(double inches) {
    return (countsPerRot()/inchPerRot(kWheelRadius, kGearRatio)) * inches; 
  }

  public double countToinch(double counts) {
    return (inchPerRot(kWheelRadius, kGearRatio)/countsPerRot())* counts; 
  }

  //PUBLIC DRIVE METHODS
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

  //AUTON EXECUTION CODE
  public double[] getEncCount() {
    double[] arr = new double[4]; 
    for(int i = 0; i < encArr.length; i++) {
      arr[i] = encArr[i].getPosition(); 
    }
    return arr; 
  }

  public double getAvgEncCount() {
    double[] arr = getEncCount(); 
    int average = 0; 
    for(int i = 0; i < arr.length; i++) {
      average += arr[i]; 
    }
    return average; 
  }

  public void zeroEncoders() {
    //TODO: do Spark MAXes tare automatically? 
  }

  public void zeroGryo() {
    gyro.zeroYaw(); 
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
