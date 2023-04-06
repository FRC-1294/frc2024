// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.armcontrolcmds;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmControlSubsystem;

public class ExtensionCmd extends CommandBase {
  ArmControlSubsystem armControl;
  double desiredExtension;

  public ExtensionCmd(ArmControlSubsystem arm, double desiredExtension) {
    
    this.armControl = arm;
    this.desiredExtension = desiredExtension;


    addRequirements(armControl);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    armControl.setDesiredExtension(desiredExtension);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return armControl.atTelescopeSetpoint();

  }
}
