// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SPI;

public class DriveTrain extends SubsystemBase {
  // The drivetrain method creates every motor and sensor of our bot and the
  // methods with it
  // To create any piece of technology in code, you have to have the CAN value
  // associated with it that you can get from phoenix tuner
  // Or the PWM or other roborio port associated with it
  private static final ADXRS450_Gyro gyroscope = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
  private static final WPI_VictorSPX intake = new WPI_VictorSPX(RobotMap.INTAKE);
  private static final WPI_VictorSPX shooter = new WPI_VictorSPX(RobotMap.SHOOTER);
  private static final WPI_VictorSPX leftRearDrive = new WPI_VictorSPX(RobotMap.LEFT_REAR_DRIVE);
  private static final WPI_VictorSPX engager = new WPI_VictorSPX(RobotMap.ENGAGER);
  private static final WPI_VictorSPX rightFrontDrive = new WPI_VictorSPX(RobotMap.RIGHT_FRONT_DRIVE);
  private static final WPI_VictorSPX leftFrontDrive = new WPI_VictorSPX(RobotMap.LEFT_FRONT_DRIVE);
  private static final WPI_VictorSPX climber = new WPI_VictorSPX(RobotMap.CLIMBER);
  private static final WPI_VictorSPX rightRearDrive = new WPI_VictorSPX(RobotMap.RIGHT_REAR_DRIVE);
  private static double batteryVoltage;
  public static double realAngle;

  // This is the method that I will always pass driving motor info through. This
  // is crucial. If one left motor is going one way and the
  // other motor on the left side is going the opposite way, they will be
  // destroyed. Same with right. We have to make one of these
  // negative because if you think about it they are in an opposite orientation,
  // so they are inverted. If we didn't put a negative sign
  // it would turn in a circle when we wanted to go straight forward or back. I
  // also multiply by driving power that's selected in
  // our Shuffleboard gui. If we didn't multiply by a decimal, and the motors were
  // on 100% the bot would do wheelies every time it moved
  public void arcadeDrive(double leftSpeed, double rightSpeed) {
    leftRearDrive.set(ControlMode.PercentOutput, -leftSpeed * Robot.gui.getDrivingPower(),
        DemandType.ArbitraryFeedForward, RobotMap.NOTHING);
    leftFrontDrive.set(ControlMode.PercentOutput, -leftSpeed * Robot.gui.getDrivingPower(),
        DemandType.ArbitraryFeedForward, RobotMap.NOTHING);
    rightRearDrive.set(ControlMode.PercentOutput, rightSpeed * Robot.gui.getDrivingPower(),
        DemandType.ArbitraryFeedForward, RobotMap.NOTHING);
    rightFrontDrive.set(ControlMode.PercentOutput, rightSpeed * Robot.gui.getDrivingPower(),
        DemandType.ArbitraryFeedForward, RobotMap.NOTHING);
  }

  // Controlls intake
  public void takeIn(double power) {
    intake.set(ControlMode.PercentOutput, power * Robot.gui.getIntakePower(), DemandType.ArbitraryFeedForward,
        RobotMap.NOTHING);
  }

  // controller shooter motor
  public void shoot(double power) {
    shooter.set(ControlMode.PercentOutput, power * Robot.gui.getShootingPower(), DemandType.ArbitraryFeedForward,
        RobotMap.NOTHING);
  }

  // Controllers engager (thing that feeds the ball to the shooter)
  public void engage(double power) {
    engager.set(ControlMode.PercentOutput, power * Robot.gui.getEngagerPower(), DemandType.ArbitraryFeedForward,
        RobotMap.NOTHING);
  }

  // Controlls the climber (we will have to add safety features to this so we make
  // sure the climber doesn't get destroyed again)
  public void climb(double power) {
    climber.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, RobotMap.NOTHING);
  }

  // This just get the rate of turning on the bot. this really isn't used
  // functionally in the code, but with calc and physics
  // it might be useful in the future
  public double getRate() {
    return Math.round(gyroscope.getRate());
  }

  // This just get the angle from the spot that the bot was turned on at (it is
  // critical that if you functionally use the gyro
  // that the robot is completely still for a decent amount of time while it's
  // turning on because the gyro is calibrating
  // and if is moved during this start up stage, the gyro will be completely off)
  // It calibrates by infering that during calibration
  // it is completely still
  public double getAngle() {
    realAngle = Math.round(gyroscope.getAngle()) - (360 * Math.floor(Math.round(gyroscope.getAngle()) / 360));
    if (realAngle < RobotMap.NOTHING) {
      return (realAngle + 360);
    } else {
      return realAngle;
    }
  }

  // I just put this in the Robot initialize method just in case, but I think the
  // gyro calibrates automatically. But just to make sure lol
  public void calibrate() {
    gyroscope.calibrate();
  }

  // This is kind of useful if the gyro has drift. I made it so that you can zero
  // the gyro out directly from the gui if the drift is bad
  // or you want to set a new position as zero.
  public void gyroReset() {
    gyroscope.reset();
  }

  // This can just be displayed to the drive team can know what the battery is at
  // and if it needs to be changed. If the battery get too low,
  // the robot's motors will not be able to work properly.
  public double getBatteryVoltage() {
    batteryVoltage = RobotController.getBatteryVoltage();
    return batteryVoltage;
  }

  // This is what we should most always use. This means that whatever the
  // controller is set at, the motor will continue to stay there
  // If it at 70% and then 0% it will go to 0%
  public void brakeMotors() {
    rightFrontDrive.setNeutralMode(NeutralMode.Brake);
    rightRearDrive.setNeutralMode(NeutralMode.Brake);
    leftFrontDrive.setNeutralMode(NeutralMode.Brake);
    leftRearDrive.setNeutralMode(NeutralMode.Brake);
  }

  // This mode is only used for our climber really.
  // I made this as like a cruise control goofy thing but I don't think you should
  // ever use this.
  // The climber needs to be set to coast because climbing is usually at the end
  // and we get disabled and all communications cut
  // so without the climber motor being coast when we are forced to disconnect the
  // bot would fall to the ground
  // coast lets the motor stay at whatever it was at so it doesn't fall
  // Based on the 2022 season, it will start drifting down toward the ground very
  // slowly, but this can be avoided by using a small gear
  // box
  public void coastMotors() {
    leftRearDrive.setNeutralMode(NeutralMode.Coast);
    leftFrontDrive.setNeutralMode(NeutralMode.Coast);
    rightRearDrive.setNeutralMode(NeutralMode.Coast);
    rightFrontDrive.setNeutralMode(NeutralMode.Coast);
  }

  // I just made this like a car method if the driver hit the breaks
  // All the driving motors would zeroed. I don't think I used this at all in
  // 2022, but maybe you find a use
  public void haltMotors() {
    leftRearDrive.set(ControlMode.PercentOutput, RobotMap.NOTHING,
        DemandType.ArbitraryFeedForward, RobotMap.NOTHING);
    leftFrontDrive.set(ControlMode.PercentOutput, RobotMap.NOTHING,
        DemandType.ArbitraryFeedForward, RobotMap.NOTHING);
    rightRearDrive.set(ControlMode.PercentOutput, RobotMap.NOTHING,
        DemandType.ArbitraryFeedForward, RobotMap.NOTHING);
    rightFrontDrive.set(ControlMode.PercentOutput, RobotMap.NOTHING,
        DemandType.ArbitraryFeedForward, RobotMap.NOTHING);
  }

  // This just makes a green or red light that depends on if the robot is super
  // low on voltage. I think it should be replaced
  // before it gets to 8 V, but it would take some testing to see what the true
  // problematic voltage is
  public boolean doWeHavePower() {
    if (batteryVoltage <= 8) {
      return false;
    } else {
      return true;
    }
  }

}
