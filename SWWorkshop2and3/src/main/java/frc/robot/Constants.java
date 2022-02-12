package frc.robot;

public class Constants {
    //Motor IDs
    public static final int kLMId = 1;
    public static final int kLFId = 2;
    public static final int kRMId = 3;
    public static final int kRFId = 4;

    //Joysticks
    public static final int kLJ = 1;
    public static final int kRJ = 2;

    //PIDF
    public final static double kP = 0;
    public final static double kI = 0;
    public final static double kD = 0;
    public final static double kF = 0;
    public final static double kSetpoint = 0;


    // Wheel parameters
    public static final double kDriveWheelTrackWidthInches = 30.0;
    public static final double kTrackScrubFactor = 1.0;

    // Deadband
    public static final double kDriveThrottleDeadband = 0.04;
    public static final double kDriveWheelDeadband = 0.035;
}
