// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Controllers extends SubsystemBase {
  int port;

  public Controllers(int portNum) {
    port = portNum;
  }

  /** Creates a new Controller object. **/
  private Joystick driverController = new Joystick(port);

  // This is an FRC method, but I'm making it public so it can be used anywhere.
  // On driver's station, you can see the number
  // associated with each axis (everything that isn't a button) by going to the
  // tab that looks like a cord. For example, the left stick's
  // up down is associated with axis 1. These values will always range from -1 to
  // 1 (besides triggers which are 0 to 1).
  // these can be used as percents to make a motor go faster or slower based on
  // how far it's pushed
  public double getDriverRawAxis(int axis) {
    return driverController.getRawAxis(axis);
  }

  // This is a very useful method because I like to use the triggers both for one
  // function usually having the right make a motor go forward
  // and left trigger making a motor go in reverse. This will make the rest of the
  // code files much cleaner rather than having a
  // subtraction of the get trigger axis every single time.
  public double getDifferenceInTriggers() {
    return (getDriverRawAxis(RobotMap.RIGHT_TRIGGER) - getDriverRawAxis(RobotMap.LEFT_TRIGGER));
  }

  // This method is very useful. Instead of polluting the other code files with if
  // statements, I can use this method to give an int
  // associated with if a button is pressed down. I can use this to controll
  // motors. If a button is not pressed, it will be 0
  // so the motor will not move and if it is pressed, it will be one and the motor
  // will move
  public int isButtonPressed(int buttonID) {
    if (driverController.getRawButton(buttonID) == true) {
      return 1;
    } else {
      return 0;
    }
  }

  // In rare cases I will need a boolean value instead of a 0 or 1, so I have this
  // public method to help with that
  public boolean getIsButtonPressed(int buttonID) {
    return driverController.getRawButton(buttonID);
  }

  // Just like triggers, I usually use bumpers for the same function with right
  // making a motor go forward and left making a more go reverse
  public double getDifferenceInBumpers() {
    return (isButtonPressed(RobotMap.RIGHT_BUMPER) - isButtonPressed(RobotMap.LEFT_BUMPER));
  }

  // I don't know what I would use this for, it's not really useful for the
  // purpose of this subsystem, but maybe you find a use
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
