// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.IOException;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;

public class PoseEstimation extends SubsystemBase {
  /** Creates a new PoseEstimation. */
  public AprilTagFieldLayout layout;
  public PhotonPoseEstimator estimator;
 // Transformation from robot to 
 SwerveSubsystem swerve;
 Limelight lime;
 SwerveDrivePoseEstimator poseEstimator;
 Field2d field;
 
  public PoseEstimation(Limelight limelight, SwerveSubsystem swerve) {
    this.swerve = swerve;
    lime = limelight;
    try {
      layout = new AprilTagFieldLayout("C:\\Users\\Akyea\\Documents\\frc2023\\src\\main\\deploy\\biggestbird.json");
    } catch (IOException e) {
      e.printStackTrace();
    }


    estimator = new PhotonPoseEstimator(layout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, limelight.photonCamera, VisionConstants.robotToCam);

    poseEstimator = new SwerveDrivePoseEstimator(swerve.m_kinematics, swerve.getRotation2d(), swerve.getModulePositions(), getOdometry());
    field = new Field2d();
    SmartDashboard.putData("Field", field);
  }

  public Optional<EstimatedRobotPose> getVision(Pose2d prevPose) {
    estimator.setReferencePose(prevPose);
    return estimator.update();

  }

  public Pose2d getOdometry() {
    return swerve.getRobotPose();
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
     
    poseEstimator.update(swerve.getRotation2d(), swerve.getModulePositions());

    Optional<EstimatedRobotPose> result = getVision(poseEstimator.getEstimatedPosition());

    if (result.isPresent()) {
      EstimatedRobotPose pose = result.get();
      poseEstimator.addVisionMeasurement(pose.estimatedPose.toPose2d(), pose.timestampSeconds);
    }
    field.setRobotPose(getPosition());
  }

  public Pose2d getPosition() {
    return poseEstimator.getEstimatedPosition();
  }
}
