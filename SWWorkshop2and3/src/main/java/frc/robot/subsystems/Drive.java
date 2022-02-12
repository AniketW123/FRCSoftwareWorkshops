package frc.robot.subsystems;
import frc.robot.Constants;
import frc.robot.PIDF;
import java.lang.Math;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Drive extends Subsystem {
    private TalonFX LM = new TalonFX(Constants.kLMId);
    private TalonFX LF = new TalonFX(Constants.kLFId);
    private TalonFX RM = new TalonFX(Constants.kRMId);
    private TalonFX RF = new TalonFX(Constants.kLFId);
    private PIDF pidf = new PIDF(0,0,0,0,0);
    public Drive(){
        LF.set(ControlMode.Follower, 1);
        LM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 1000);
        RF.set(ControlMode.Follower, 3);
        RM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 1000);
    }
    public void setOpenLoop(double throttle, double turn){
        double LT;
        double RT;
        if (Math.signum(throttle) != 0) {
            LT = Math.signum(throttle) * -turn;
            RT = Math.signum(throttle) * turn;
        } else {
            LT = -turn;
            RT = turn;
        }
        LM.set(ControlMode.PercentOutput, (throttle+LT)/2);
        RM.set(ControlMode.PercentOutput, (throttle+RT)/2);
    }
    public void stop(){
        LM.set(ControlMode.PercentOutput, 0.0);
        RM.set(ControlMode.PercentOutput, 0.0);
    }
}