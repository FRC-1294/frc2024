// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.constants;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;

/** Add your docs here. */
public class VisionConstants {
    public static final Matrix<N3, N1> stateStdDevs = VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(1));
    public static final Matrix<N3, N1> visionMeasurementStdDevs = VecBuilder.fill(0.2, 0.2, Units.degreesToRadians(10)); 
}
