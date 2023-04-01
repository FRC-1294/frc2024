// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Constants;

import java.util.HashMap;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoAlign;
import frc.robot.commands.AutoBalanceCommand;
import frc.robot.commands.LimelightAlign;
import frc.robot.commands.armcontrolcmds.ExtensionCmd;
import frc.robot.commands.armcontrolcmds.PivotCmd;
import frc.robot.commands.armcontrolcmds.ScoreConeHighNode;
import frc.robot.subsystems.ArmControlSubsystem;
import frc.robot.subsystems.ClawPnumatic;
import frc.robot.subsystems.LimelightPhoton;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.vision.PoseEstimation;
import frc.robot.vision.PoseEstimationBase;


// CODE ISN'T FUNCTIONAL YET


public class AutoMapConstants {

    public static enum GamePiece{
        CONE,
        CUBE
    }

    public static PathPlannerTrajectory ConeCubeChargeTraj = PathPlanner.loadPath("Cone+Ball+Charge", new PathConstraints(2, 1.5));
    public static PathPlannerTrajectory move1Meter = PathPlanner.loadPath("MoveOneMeters", new PathConstraints(2, 1.5));
    public static PathPlannerTrajectory move1MeterRotate = PathPlanner.loadPath("MoveOneMeters+180", new PathConstraints(2, 1.5));

 
    public static HashMap<String,Command> m_EventMap = new HashMap<>();
    public static HashMap<String,Command> emptyMap =  new HashMap<>();
    public static PathPlannerTrajectory backforth;

    public static void populateHashMaps(SwerveSubsystem swerve, LimelightPhoton lime, ArmControlSubsystem arm, PoseEstimationBase pose, ClawPnumatic clawPnumatic){
        m_EventMap.put("angle_N3", new PivotCmd(arm, ArmConstants.angleLevelsDeg[2]));
        m_EventMap.put("angle_N2", new PivotCmd(arm, ArmConstants.angleLevelsDeg[1]));
        m_EventMap.put("angle_N1", new PivotCmd(arm, ArmConstants.angleLevelsDeg[0]));
        m_EventMap.put("angle_N0",new PivotCmd(arm, ArmConstants.groundPick[0]));
        m_EventMap.put("angle_neutral",new PivotCmd(arm, ArmConstants.angleLevelsDeg[0]));

        m_EventMap.put("telescope_N3", new ExtensionCmd(arm, ArmConstants.extensionLevelsIn[2]));
        m_EventMap.put("telescope_N2", new ExtensionCmd(arm, ArmConstants.extensionLevelsIn[1]));
        m_EventMap.put("telescope_N1", new ExtensionCmd(arm, ArmConstants.extensionLevelsIn[0]));
        m_EventMap.put("telescope_N0",new ExtensionCmd(arm, ArmConstants.groundPick[1]));
        m_EventMap.put("telescope_neutral",new ExtensionCmd(arm, ArmConstants.extensionLevelsIn[0]));

        m_EventMap.put("score_cone", new ScoreConeHighNode(arm, clawPnumatic));
        m_EventMap.put("drop_cube", clawPnumatic.dropPiece(GamePiece.CUBE));
        m_EventMap.put("intake_cube", new SequentialCommandGroup(new InstantCommand(()->clawPnumatic.spinIntake(0.8)),new WaitCommand(0.8)));


        m_EventMap.put("auto_balance",new WaitCommand(1));
        m_EventMap.put("align_cube", new LimelightAlign(swerve, lime, VisionConstants.CubePipelineID, 0));
        m_EventMap.put("align_tag",new AutoAlign(pose, lime, swerve));

        emptyMap.put("wait", new WaitCommand(1));

    }

}

