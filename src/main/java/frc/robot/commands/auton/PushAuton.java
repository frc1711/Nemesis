/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PushAuton extends SequentialCommandGroup {
  /**
   * Creates a new PushAuton.
   */
  public PushAuton(DriveTrain driveTrain) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new StraightAuton(driveTrain),
      new Drive(driveTrain, 0.1, 15, 100),
      new Turn(driveTrain, 0.3, -90),
      new Drive(driveTrain, 0.1, 45, 100),
      new Turn(driveTrain, 0.3, 90),
      new Drive(driveTrain, 0.3, 61, 100), //Run into the robots
      new Drive(driveTrain, 0.1, -30, 100)
    );
  }
}
