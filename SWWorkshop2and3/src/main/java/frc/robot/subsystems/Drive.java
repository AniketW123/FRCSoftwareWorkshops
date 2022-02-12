package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.PIDF;
import frc.robot.lib.util.DriveSignal;
import frc.robot.subsystems.Drive.DriveControlState;

import java.lang.Math;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Drive extends Subsystem {
    private TalonFX LM = new TalonFX(Constants.kLMId);
    private TalonFX LF = new TalonFX(Constants.kLFId);
    private TalonFX RM = new TalonFX(Constants.kRMId);
    private TalonFX RF = new TalonFX(Constants.kLFId);
    private PIDF pidf = new PIDF(Constants.kP, Constants.kP, Constants.kP, Constants.kP, Constants.kSetpoint);

    private DriveControlState mDriveControlState;
    private PeriodicIO mPeriodicIO;

    public static class PeriodicIO {
        // real outputs
        public double left_demand;
        public double right_demand;

        // INPUTS
        public double timestamp;
        public double left_voltage;
        public double right_voltage;
        public int left_position_ticks; // using us digital encoder
        public int right_position_ticks; // us digital encoder
        public double left_distance;
        public double right_distance;
        public int left_velocity_ticks_per_100ms; // using talonfx
        public int right_velocity_ticks_per_100ms; // talonfx
        public double left_velocity_in_per_sec;
        public double right_velocity_in_per_sec;
        public Rotation2d gyro_heading = Rotation2d.identity();

        // OUTPUTS
        public double left_accel;
        public double right_accel;
        public double left_feedforward;
        public double right_feedforward;
        public Pose2d error = Pose2d.identity();
        public TimedState<Pose2dWithCurvature> path_setpoint = new TimedState<Pose2dWithCurvature>(Pose2dWithCurvature.identity());
    }

    public Drive(){
        LF.set(ControlMode.Follower, 1);
        LM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 1000);
        RF.set(ControlMode.Follower, 3);
        RM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 1000);
    }
    public synchronized void setOpenLoop(DriveSignal signal) {
        if (mDriveControlState != DriveControlState.OPEN_LOOP) {
            System.out.println("switching to open loop");
            System.out.println(signal);
            mDriveControlState = DriveControlState.OPEN_LOOP;
        }

        mPeriodicIO.left_demand = signal.getLeft();
        mPeriodicIO.right_demand = signal.getRight();
        mPeriodicIO.left_feedforward = 0.0;
        mPeriodicIO.right_feedforward = 0.0;
    }
    public void stop(){
        setOpenLoop(DriveSignal.NEUTRAL);
    }

    public enum DriveControlState {
        OPEN_LOOP, // open loop voltage control,
        VELOCITY, // velocity control
        PATH_FOLLOWING
    }
}