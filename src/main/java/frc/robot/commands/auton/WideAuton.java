/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class WideAuton extends SequentialCommandGroup {
  /**
   * Creates a new WideAuton.
   */
  public WideAuton(DriveTrain driveTrain, Shooter shooter, Pulley pulley) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
      new Drive(driveTrain, 0.1, -32.6),
      new Turn(driveTrain, 0.3, -34),
      new Drive(driveTrain, 0.1, -164.2),
      new Shoot(shooter, pulley, 3)
    );
  }
}
