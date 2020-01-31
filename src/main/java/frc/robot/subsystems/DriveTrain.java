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
import frc.robot.Constants.RoboDir;
import edu.wpi.first.wpilibj.SPI;

/** 
* @author: Lou DeZeeuw  
*/

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
  
  private static double kWheelRadius; 
  private static double kGearRatio; 

  private static CANEncoder[] encArr; 

  Boolean sparkMaxUse; 

  public DriveTrain(double wheelRadius, double gearRatio) {
    //MOTOR DEFINITIONS
    frontRightDrive = new CANSparkMax(Constants.frd, MotorType.kBrushless); 
    frontLeftDrive = new CANSparkMax(Constants.fld, MotorType.kBrushless); 
    rearRightDrive = new CANSparkMax(Constants.rrd, MotorType.kBrushless); 
    rearLeftDrive = new CANSparkMax(Constants.rld, MotorType.kBrushless); 

    frontRightDrive.setInverted(false); 
    rearRightDrive.setInverted(false); 
    
    //ENCODER DEFINITIONS
    frEncoder = frontRightDrive.getEncoder(); 
    flEncoder = frontLeftDrive.getEncoder(); 
    rrEncoder = rearRightDrive.getEncoder(); 
    rlEncoder = rearLeftDrive.getEncoder(); 

    rightDrive = new SpeedControllerGroup(frontRightDrive, rearRightDrive); 
    leftDrive = new SpeedControllerGroup(frontLeftDrive, rearLeftDrive); 

    rDrive = new DifferentialDrive(rightDrive, leftDrive); 
    gyro = new AHRS(SPI.Port.kMXP); 

    kWheelRadius = wheelRadius; 
    kGearRatio = gearRatio; 
  
    encArr = new CANEncoder[]{frEncoder, flEncoder, rrEncoder, rlEncoder}; 

    rDrive.setSafetyEnabled(false);

    frEncoder.setPositionConversionFactor(42); 
    flEncoder.setPositionConversionFactor(42); 
    rrEncoder.setPositionConversionFactor(42); 
    rlEncoder.setPositionConversionFactor(42); 
  }

  //AUTON MATH
  private double countsPerRot() {
    return 42; 
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
      if ((i+1)%2 == 0)
        arr[i] = encArr[i].getPosition(); 
      else
        arr[i] = -encArr[i].getPosition(); 

    }
    return arr; 
  }

  public double getIndividualEncCount() {
    return flEncoder.getPosition(); 
  }

  public double getAvgEncCount(double[] arr) {
    int average = 0; 
    for(int i = 0; i < arr.length; i++) {
      average += arr[i]; 
    }
    average /= (arr.length+1); 
    
    return average; 
  }

  public double getCountConstant() {
    return (inchPerRot(kWheelRadius, kGearRatio)/countsPerRot());
  }


  public void drive(double speed, RoboDir direction) {
    if(direction == RoboDir.STRAIGHT) {
      frontLeftDrive.set(speed); 
      frontRightDrive.set(-speed); 
      rearLeftDrive.set(speed);
      rearRightDrive.set(-speed);  
    } else {
      frontLeftDrive.set(direction.getNum()*speed); 
      frontRightDrive.set(direction.getNum()*speed); 
      rearLeftDrive.set(direction.getNum()*speed); 
      rearRightDrive.set(direction.getNum()*speed); 
    }
  }

  public void drive(double speed) {
    frontLeftDrive.set(speed); 
    frontRightDrive.set(-speed); 
    rearLeftDrive.set(speed);
    rearRightDrive.set(-speed);
  }

  public void zeroEncoders() {
    frEncoder.setPosition(0); 
    flEncoder.setPosition(0); 
    rrEncoder.setPosition(0); 
    rlEncoder.setPosition(0); 
  }

  public void zeroGyro() {
    gyro.zeroYaw(); 
  }

  public double getYaw() {
    return gyro.getAngle(); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
