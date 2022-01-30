package subsystems;
import com.ctre.phoenix.*;
import java.util.concurrent.TimeUnit;

public class SWWorkshop2and3 {

    public class Drive{
        private TalonFX LTalon1 = new TalonFX(1);
        private TalonFX LTalon2 = new TalonFX(2);
        private TalonFX RTalon1 = new TalonFX(3);
        private TalonFX RTalon2 = new TalonFX(4);
        public Drive(){
            LTalon2.set(ControlMode.Follower, 1);
            LTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 1000);
            RTalon2.set(ControlMode.Follower, 3);
            RTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 1000);
        }
        public void setOpenLoop(double throttle, double turn){
            if(turn == 0) {
                LTalon1.set(ControlMode.PercentOutput, throttle);
                RTalon1.set(ControlMode.PercentOutput, throttle);
            } else {
                LTalon1.set(ControlMode.PercentOutput, -turn * throttle);
                RTalon1.set(ControlMode.PercentOutput, turn * throttle);
            }
        }
        public void stop(){
            LTalon1.set(ControlMode.PercentOutput, 0.0);
            RTalon1.set(ControlMode.PercentOutput, 0.0);
        }
    }
    public class Robot{
        private Joystick joystick1 = new Joystick(1);
        private Joystick joystick2 = new Joystick(1);
        private Drive drive = new Drive();
        public Robot(){}
        public void teleopPeriodic(){
            double turn = joystick1.getRawAxis(0);
            double throttle = joystick2.getRawAxis(1);
            drive.setOpenLoop(throttle,turn);
        }
        public void disabledInit(){
            drive.stop();
        }
        public void testInit(){
            drive.stop();
        }
        public void autoInit(){
            drive.stop();
        }
    }
    public class PIDF{
        private double Kp;
        private double Ki;
        private double Kd;
        private double Kf;
        private double setpoint;
        public PIDF(double Kp, double Ki, double Kd, double Kf){
            this.Kp = Kp;
            this.Ki = Ki;
            this.Kd = Kd;
            this.Kf = Kf;
        }

        public double get(int i){
            switch(i){
                case 0:
                    return Kp;
                case 1:
                    return Ki;
                case 2:
                    return Kd;
                case 3:
                    return Kf;
                case 4:
                    return setpoint;
                default:
                    return 0.0;
            }
        }
        public void set(int inp, double val){
            switch(inp){
                case 0:
                    Kp = val;
                    break;
                case 1:
                    Ki = val;
                    break;
                case 2:
                    Kd = val;
                    break;
                case 3:
                    Kf = val;
                    break;
                case 4:
                    setpoint = val;
                
            }
        }
        public double getError(){
            return 1;
        }
        public double update(double pos, double val, double allowable_error, int dt){
            double prev_error = 0;
            double integral = 0;
            double output = 0;
            double error = allowable_error + 1;
            while(error > allowable_error){
                error = setpoint - val;
                integral = integral + error * dt;
                double derivative = (error - prev_error) / dt;
                output = Kp*error + Ki*integral + Kd*derivative;
                prev_error = error;
                try {
                    Thread.sleep(dt);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return output;
        }
    }
}
