// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Autonomous extends CommandBase {

  long beginningTime;
  double timeSec;

  public Autonomous() {
    addRequirements(Robot.driveTrain);
  }

  // This piece of code will run at the first time the command is called and never
  // again
  // It notes the time in milliseconds of when it's started (beginning of the
  // Auton period), and as a safety measure,
  // it zeros out all motors
  @Override
  public void initialize() {
    beginningTime = System.currentTimeMillis(); // create this object so that I know the original time
    Robot.driveTrain.arcadeDrive(RobotMap.NOTHING, RobotMap.NOTHING);
  }

  // Called repeatedly when this Command is scheduled to run
  // This excute method will run multiple times per second. The time in
  // milliseconds is constantly being updated and will reflect how long
  // autonomous has been going on for. Through trial and error, you can use the
  // set up below to say like set these motors to this speed
  // for this long. This is not exact and will just be trial and error. The
  // alternativee is the use of sensors.
  // I hope we will have that in 2023

  @Override
  public void execute() {
    timeSec = (double) ((System.currentTimeMillis() - beginningTime) / 1000);
    // since this method is constantly executed, it will update and show me the time
    // in seconds

    while (timeSec > 3) {
      // Do stuff that you want done right at the beginning before 3 second has passed
    }
    while (timeSec > 2) {
      // Do Stuff that you want done after 2 seconds have passed
    }
    while (timeSec > 1) {
      // Do Stuff that you want done after 1 seconds have passed
    }
    while (timeSec > 0) {
      // Do Stuff that you want done after 0 seconds have passed
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  // For example since autonomous period is only 15 seconds I can set this method
  // to return true when time == 15 seconds
  @Override
  public boolean isFinished() {
    return (timeSec == 15);
  }

  // Called once after isFinished returns true
  // This method will only run once and it will do whatever you want to do when
  // it's finished for example you could start a new command
  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.arcadeDrive(RobotMap.NOTHING, RobotMap.NOTHING);
  }
}
