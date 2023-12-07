// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WCSubsystem;

public class DriveForward extends CommandBase {
  private final int DISTANCE = 2; // inches

  WCSubsystem wc;
  private boolean finished = false;

  /** Creates a new DriveForward. */
  public DriveForward(WCSubsystem wc) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(wc);

    this.wc = wc;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    wc.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    finished = wc.moveRobot(DISTANCE);
  }

  @Override
  public boolean isFinished() {
    return finished;
  }
  // Evaluate boolean ifDistance to check if the robot has reached distance.


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Reset everything before the next command call
    finished = false;
  }

  // Returns true when the command should end.

}
