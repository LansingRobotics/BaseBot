package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SwitchDrive extends CommandBase {
  static double rightTurnValue;
  static double leftTurnValue;

  public SwitchDrive() {
    addRequirements(Robot.driveTrain);
  }

  // Called when the command is initially scheduled.
  // This code only runs once at the very beginning.
  // This command is set as the default command for the DriveTrain subsystem, so
  // once we create a DriveTrain subsystem in Robot.java
  // this command will start to run
  @Override
  public void initialize() {
    Robot.driveTrain.arcadeDrive(0, 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  // This code is just saying get the selection of drive type that is selected on
  // the graphical user interface will make it switch
  // between the different methods. It will excute the drive type methods
  // continuously multiple times per second constantly checking and
  // changing the controls.
  @Override
  public void execute() {
    switch (Robot.gui.getDrivingType()) {
      case RobotMap.TANK_DRIVE_DRIVING:
        singleTankDriveExecute();
        break;
      case RobotMap.DOUBLE_TANK_DRIVING:
        doubleTankDriveExecute();
        break;
      case RobotMap.VIDEO_GAME_DRIVING:
        singleVideoGameDriveExecute();
        break;
      case RobotMap.DOUBLE_VIDEO_GAME_DRIVING:
        doubleVideoGameDriveExecute();
        break;
      default:
        singleTankDriveExecute();
        break;
    }
    SmartDashboard.putString("Drive Mode", Robot.gui.sayCurrentCommand());
  }

  // Called once the command ends or is interrupted.
  // This code will only run once. It will only end if Autonomous is enabled or
  // Teleop is disabled.
  @Override
  public void end(boolean interrupted) {
    Robot.driveTrain.arcadeDrive(RobotMap.NOTHING, RobotMap.NOTHING);
  }

  // Returns true when the command should end.
  // You really don't have to put anything in this method because you can just
  // finish teleop by disabling it.
  // Unless you are just having fun and experimenting this isn't a necessary
  // method
  @Override
  public boolean isFinished() {
    return false;
  }

  // This method is set up to continuously run multiple times per second when the
  // execute() method constantly checks the controller
  // This code is meant to work with a one person drive team. The buttons that are
  // used to control it can be changed
  // Depending on how we build the bot, we might have to put negative signs in if
  // it is going the reverse way we want
  private static void singleTankDriveExecute() {
    Robot.driveTrain.brakeMotors();
    double leftStickY = Robot.controller1.getDriverRawAxis(RobotMap.LEFT_STICK_Y);
    double rightStickY = Robot.controller1.getDriverRawAxis(RobotMap.RIGHT_STICK_Y);

    Robot.driveTrain.arcadeDrive((-leftStickY * Robot.gui.getDrivingPower()),
        (-rightStickY * Robot.gui.getDrivingPower()));

    Robot.driveTrain.takeIn(Robot.controller1.getDifferenceInBumpers() * Robot.gui.getIntakePower());

    Robot.driveTrain.shoot((Robot.controller1.isButtonPressed(RobotMap.A_BUTTON) * Robot.gui.getShootingPower())
        - (Robot.controller1.isButtonPressed(RobotMap.B_BUTTON) * Robot.gui.getShootingPower()));

    Robot.driveTrain.engage((Robot.controller1.isButtonPressed(RobotMap.X_BUTTON) * Robot.gui.getEngagerPower())
        - (Robot.controller1.isButtonPressed(RobotMap.Y_BUTTON) * Robot.gui.getEngagerPower()));

    Robot.driveTrain.climb(Robot.controller1.getDifferenceInTriggers());
  }

  // This method is set up to continuously run multiple times per second when the
  // execute() method constantly checks the controller
  // This code is meant to work with a one person drive team. The buttons that are
  // used to control it can be changed
  // Depending on how we build the bot, we might have to put negative signs in if
  // it is going the reverse way we want
  // This code makes the robot go forward and back with the triggers and left and
  // right with the left stick. The buttons for the
  // other functions can be changed, but these are just suggestions
  private static void singleVideoGameDriveExecute() {
    Robot.driveTrain.brakeMotors();
    double turnValue = Robot.controller1.getDriverRawAxis(RobotMap.LEFT_STICK_X);

    if (turnValue >= 0) {
      rightTurnValue = -turnValue;
    } else {
      rightTurnValue = 0;
    }
    if (turnValue < 0) {
      leftTurnValue = turnValue;
    } else {
      leftTurnValue = 0;
    }

    Robot.driveTrain.arcadeDrive(
        ((Robot.controller1.getDifferenceInTriggers() - leftTurnValue) * Robot.gui.getDrivingPower()),
        ((Robot.controller1.getDifferenceInTriggers() - rightTurnValue) * Robot.gui.getDrivingPower()));

    Robot.driveTrain.takeIn(Robot.controller1.getDifferenceInBumpers() * Robot.gui.getIntakePower());

    Robot.driveTrain.shoot((Robot.controller1.isButtonPressed(RobotMap.A_BUTTON) * Robot.gui.getShootingPower())
        - (Robot.controller1.isButtonPressed(RobotMap.B_BUTTON) * Robot.gui.getShootingPower()));

    Robot.driveTrain.engage(
        (Robot.controller1.isButtonPressed(RobotMap.X_BUTTON) - Robot.controller1.isButtonPressed(RobotMap.Y_BUTTON))
            * Robot.gui.getEngagerPower());

    Robot.driveTrain.climb(Robot.controller1.getDriverRawAxis(RobotMap.RIGHT_STICK_Y));

  }

  // This method is set up to continuously run multiple times per second when the
  // execute() method constantly checks the controller
  // This code is meant to work with a two person drive team. The buttons that are
  // used to control it can be changed
  // Depending on how we build the bot, we might have to put negative signs in if
  // it is going the reverse way we want
  // This code controlls the left side of the bot with the left stick and the
  // right side with the right stick. It can move like a tank
  // The buttons not related to driving are just suggestions and the way that
  // driving, shooting, engaging, intaking, and climbing can
  // be changed, but this is how we did it in the 2022 season
  private static void doubleTankDriveExecute() {
    Robot.driveTrain.brakeMotors();
    double leftStickY = Robot.controller1.getDriverRawAxis(RobotMap.LEFT_STICK_Y);
    double rightStickY = Robot.controller1.getDriverRawAxis(RobotMap.RIGHT_STICK_Y);

    Robot.driveTrain.arcadeDrive((-leftStickY * Robot.gui.getDrivingPower()),
        (-rightStickY * Robot.gui.getDrivingPower()));

    Robot.driveTrain.takeIn(Robot.controller2.getDifferenceInBumpers() * Robot.gui.getIntakePower());

    Robot.driveTrain.shoot(Robot.controller1.isButtonPressed(RobotMap.A_BUTTON) * Robot.gui.getShootingPower());

    Robot.driveTrain.engage(Robot.controller1.isButtonPressed(RobotMap.X_BUTTON) * Robot.gui.getEngagerPower());

    Robot.driveTrain.climb(Robot.controller2.getDifferenceInTriggers());
  }

  // This method is set up to continuously run multiple times per second when the
  // execute() method constantly checks the controller
  // This code is meant to work with a two person drive team. The buttons that are
  // used to control it can be changed
  // Depending on how we build the bot, we might have to put negative signs in if
  // it is going the reverse way we want
  // This code makes the robot go forward and back with the triggers and left and
  // right with the left stick. The buttons for the
  // other functions can be changed, but these are just suggestions
  // The way that the tasks are divided between the first and second driver are
  // just how we did it in 2022 and it can be changed
  private static void doubleVideoGameDriveExecute() {
    Robot.driveTrain.brakeMotors();
    double turnValue = Robot.controller1.getDriverRawAxis(RobotMap.LEFT_STICK_X);

    if (turnValue >= 0) {
      rightTurnValue = turnValue;
    } else {
      rightTurnValue = 0;
    }
    if (turnValue < 0) {
      leftTurnValue = -turnValue;
    } else {
      leftTurnValue = 0;
    }

    Robot.driveTrain.arcadeDrive(
        ((Robot.controller1.getDifferenceInTriggers() - leftTurnValue) * Robot.gui.getDrivingPower()),
        ((Robot.controller1.getDifferenceInTriggers() - rightTurnValue) * Robot.gui.getDrivingPower()));

    Robot.driveTrain.takeIn(Robot.controller2.getDriverRawAxis(RobotMap.LEFT_STICK_Y) * Robot.gui.getIntakePower());

    Robot.driveTrain.shoot(Robot.controller1.getDifferenceInBumpers() * Robot.gui.getShootingPower());

    Robot.driveTrain.engage(Robot.controller1.getDriverRawAxis(RobotMap.RIGHT_STICK_Y) * Robot.gui.getEngagerPower());

    Robot.driveTrain.climb(Robot.controller2.getDriverRawAxis(RobotMap.RIGHT_STICK_Y));

  }

}