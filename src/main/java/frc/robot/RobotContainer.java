/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.RunClimber;
import frc.robot.commands.RunShooter;
import frc.robot.commands.WestCoastDrive;
import frc.robot.commands.auton.DriveForward;
import frc.robot.commands.GetColor;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ColorSensor;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final DriveTrain driveTrain = new DriveTrain(24.125, 10.71);
  //private final Shooter shooter = new Shooter(); 
  //private final Climber climber = new Climber(); 
  //private final ColorSensor colorSensor = new ColorSensor();

  private final Command autonomousCommand = new DriveForward(driveTrain, -11.75, .1, 100); 
  
  public Joystick driverOne = new Joystick(0); 
  public Joystick driverTwo = new Joystick(1); 
  public JoystickButton getColorButton = new JoystickButton(driverOne, 1);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    //Assign default commands 
    driveTrain.setDefaultCommand(new WestCoastDrive(driveTrain, () -> driverOne.getRawAxis(1), () -> driverOne.getRawAxis(4))); 
    //shooter.setDefaultCommand(new RunShooter(shooter, driverOne)); 
    //climber.setDefaultCommand(new RunClimber(climber, () -> driverTwo.getRawAxis(1)));

    // Configure button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //getColorButton.whenHeld(new GetColor(colorSensor));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autonomousCommand; 
  }
}
