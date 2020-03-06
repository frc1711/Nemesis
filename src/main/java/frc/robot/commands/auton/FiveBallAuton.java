/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class FiveBallAuton extends SequentialCommandGroup {
  /**
   * Creates a new FiveBallAuton.
   */
  public FiveBallAuton(DriveTrain driveTrain, Shooter shooter, Pulley pulley, Intake intake) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new DriveCorrect(driveTrain, 0.4, 0.075, 35),
      new Delay(.3),
      new IntakeDrive(driveTrain, intake, pulley),
      new DriveCorrect(driveTrain, 0.4, .075, -82),
      new DriveCorrect(driveTrain, 0.4, .075, -40),
      new Turn(driveTrain, 0.5, 22),
      new Delay(.4), 
      new DriveCorrect(driveTrain, 0.3, .075, -40), 
      new Shoot(shooter, pulley, 5), 
      new ResetDrive(driveTrain)    
    );
  }
}
