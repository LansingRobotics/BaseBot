// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class GUI extends SubsystemBase {
  /** Creates a new GUI. */
  // This sets up the fields that we can add selectable values to
  // It also sets up a camera object to be plugged into the Roborio's USB port
  private static UsbCamera theCamera = CameraServer.startAutomaticCapture("USB Camera", RobotMap.CAMERA_PORT);
  private static SendableChooser<Integer> drivingType = new SendableChooser<>();
  private static SendableChooser<Integer> autonomousCommand = new SendableChooser<>();
  private static SendableChooser<Double> drivingMotorPower = new SendableChooser<>();
  private static SendableChooser<Double> shootingMotorPower = new SendableChooser<>();
  private static SendableChooser<Double> intakeMotorPower = new SendableChooser<>();
  private static SendableChooser<Double> engagingMotorPower = new SendableChooser<>();
  private static SendableChooser<Boolean> resettingGyroscope = new SendableChooser<>();

  // I call this method in the Robot's initialize. It will just push all that
  // values to the choosers on the gui
  public void startGui() {
    SmartDashboard.putData("Driving Type", drivingType);
    drivingType.setDefaultOption("Tank Drive", RobotMap.TANK_DRIVE_DRIVING);
    drivingType.addOption("Video Game Driving", RobotMap.VIDEO_GAME_DRIVING);
    drivingType.addOption("Double Tank Drive", RobotMap.DOUBLE_TANK_DRIVING);
    drivingType.addOption("Double Video Game Drive", RobotMap.DOUBLE_VIDEO_GAME_DRIVING);

    SmartDashboard.putData("Driving Power", drivingMotorPower);
    drivingMotorPower.setDefaultOption("100%", 1.0);
    drivingMotorPower.addOption("90%", 0.9);
    drivingMotorPower.addOption("80%", 0.8);
    drivingMotorPower.addOption("70%", 0.7);
    drivingMotorPower.addOption("60%", 0.6);
    drivingMotorPower.addOption("50%", 0.5);
    drivingMotorPower.addOption("40%", 0.4);
    drivingMotorPower.addOption("30%", 0.3);
    drivingMotorPower.addOption("20%", 0.2);
    drivingMotorPower.addOption("10%", 0.1);
    drivingMotorPower.addOption("Off", 0.0);

    SmartDashboard.putData("Shooting Power", shootingMotorPower);
    shootingMotorPower.setDefaultOption("100%", 1.0);
    shootingMotorPower.addOption("90%", 0.9);
    shootingMotorPower.addOption("80%", 0.8);
    shootingMotorPower.addOption("70%", 0.7);
    shootingMotorPower.addOption("60%", 0.6);
    shootingMotorPower.addOption("50%", 0.5);
    shootingMotorPower.addOption("40%", 0.4);
    shootingMotorPower.addOption("30%", 0.3);
    shootingMotorPower.addOption("20%", 0.2);
    shootingMotorPower.addOption("10%", 0.1);
    shootingMotorPower.addOption("Off", 0.0);

    SmartDashboard.putData("Intake Power", intakeMotorPower);
    intakeMotorPower.setDefaultOption("100%", 1.0);
    intakeMotorPower.addOption("90%", 0.9);
    intakeMotorPower.addOption("80%", 0.8);
    intakeMotorPower.addOption("70%", 0.7);
    intakeMotorPower.addOption("60%", 0.6);
    intakeMotorPower.addOption("50%", 0.5);
    intakeMotorPower.addOption("40%", 0.4);
    intakeMotorPower.addOption("30%", 0.3);
    intakeMotorPower.addOption("20%", 0.2);
    intakeMotorPower.addOption("10%", 0.1);
    intakeMotorPower.addOption("Off", 0.0);

    SmartDashboard.putData("Engager Power", engagingMotorPower);
    engagingMotorPower.setDefaultOption("100%", 1.0);
    engagingMotorPower.addOption("90%", 0.9);
    engagingMotorPower.addOption("80%", 0.8);
    engagingMotorPower.addOption("70%", 0.7);
    engagingMotorPower.addOption("60%", 0.6);
    engagingMotorPower.addOption("50%", 0.5);
    engagingMotorPower.addOption("40%", 0.4);
    engagingMotorPower.addOption("30%", 0.3);
    engagingMotorPower.addOption("20%", 0.2);
    engagingMotorPower.addOption("10%", 0.1);
    engagingMotorPower.addOption("Off", 0.0);

    SmartDashboard.putData("Reset Gyroscope", resettingGyroscope);
    resettingGyroscope.setDefaultOption("Off", false);
    resettingGyroscope.addOption("On", true);
  }

  // The following methods are just public methods that can be used to see what
  // percent power the drive team wants the motors at
  public double getDrivingPower() {
    return drivingMotorPower.getSelected();
  }

  public double getIntakePower() {
    return intakeMotorPower.getSelected();
  }

  public double getShootingPower() {
    return shootingMotorPower.getSelected();
  }

  public double getEngagerPower() {
    return engagingMotorPower.getSelected();
  }

  public int getDrivingType() {
    return drivingType.getSelected();
  }

  public int getAutonomousCommand() {

    if (autonomousCommand.getSelected() == null) {
      return 0;
    } else {
      return autonomousCommand.getSelected();
    }
  }

  public boolean getResettingGyroscope() {
    return resettingGyroscope.getSelected();
  }

  public String sayCurrentCommand() {
    switch (getDrivingType()) {
      case RobotMap.TANK_DRIVE_DRIVING:
        return "Tank Driving";
      case RobotMap.VIDEO_GAME_DRIVING:
        return "Video Game Driving";
      case RobotMap.DOUBLE_TANK_DRIVING:
        return "Double Tank Driving";
      case RobotMap.DOUBLE_VIDEO_GAME_DRIVING:
        return "Double Video Game Driving";
      default:
        return "Nothing";
    }
  }

  // This starts up the camera and creates the way that it is compatible with the
  // gui formatting
  public void startCamera() {
    theCamera.setVideoMode(PixelFormat.kMJPEG, 150, 75, 20);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
