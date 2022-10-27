package frc.robot;

import java.text.DecimalFormat;

import frc.robot.Commands.Autonomous;
import frc.robot.Commands.SwitchDrive;
import frc.robot.Subsystems.Controllers;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.DriveTrain;
import frc.robot.Subsystems.GUI;

// This entire class is pretty much a representation of our robot in code
// It has initialize, periodic, end, and is finished. These work just like any other command
// Initialize runs once. Periodic constantly runs, end runs once when isfinished turns true
// It has an initialize and periodic for each command button in driver station

public class Robot extends TimedRobot {
  // This just creates a driveTrain object (all motors and sensors), a gui object,
  // and the two controllers. The commands have to be initialized too, for every
  // command you run you have to create an instance
  public static DriveTrain driveTrain = new DriveTrain();
  public static final DecimalFormat twoDecimalPlaceFormat = new DecimalFormat("###.##");
  public static final DecimalFormat wholeNumberFormat = new DecimalFormat("###");
  public static GUI gui = new GUI();
  public static Controllers controller1 = new Controllers(RobotMap.DRIVER_CONTROLLER1);
  public static Controllers controller2 = new Controllers(RobotMap.DRIVER_CONTROLLER2);
  private long rawStartTime;
  private double timeLeft;
  private static SwitchDrive switchDrive = new SwitchDrive();
  private static Autonomous auton = new Autonomous();

  // This just does all the very beginning basic things when the robot is first
  // turned on. This will only run once
  @Override
  public void robotInit() {
    gui.startGui();
    driveTrain.calibrate();
    DriveTrain.realAngle = RobotMap.NOTHING;
    CommandScheduler.getInstance().setDefaultCommand(driveTrain, switchDrive);
    gui.startCamera();
  }

  // This just puts values to the gui that can give the driver team so info about
  // the bot
  @Override
  public void robotPeriodic() {

    SmartDashboard.putString("Battery Voltage",
        twoDecimalPlaceFormat.format(driveTrain.getBatteryVoltage()) + " Volts");
    SmartDashboard.putString("Currently Doing", gui.getDrivingType() + "");

    SmartDashboard.putBoolean("Power Avaliability", driveTrain.doWeHavePower());

    SmartDashboard.putString("Gyroscope Angle", driveTrain.getAngle() + " Degrees");

    SmartDashboard.putString("Gyroscope Rate", driveTrain.getRate() + " Degrees / Sec");

    CommandScheduler.getInstance().run();

    if (gui.getResettingGyroscope()) {
      driveTrain.gyroReset();
    }

  }

  // Starts out autonomous program
  @Override
  public void autonomousInit() {
    auton.schedule();
  }

  // This line in the method says look at what command is scheduled and then tells
  // the computer to run that command
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  // This creates a timer that can be used for competition
  // schedules switch drive command for teleop
  @Override
  public void teleopInit() {
    rawStartTime = System.currentTimeMillis();
    switchDrive.schedule();
  }

  @Override
  public void teleopPeriodic() {
    timeLeft = ((135000 - (System.currentTimeMillis() - rawStartTime)) / 1000);
    SmartDashboard.putNumber("Time Left", timeLeft);
    CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
    CommandScheduler.getInstance().run();
  }

}
