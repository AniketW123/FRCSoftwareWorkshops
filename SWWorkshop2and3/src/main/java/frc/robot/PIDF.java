package frc.robot;

public class PIDF{
    private double kP, kI, kD, kF, setpoint;
    double previousError = 0;
    double previousTime = 0;
    double integral = 0;
    public PIDF(double kP, double kI, double kD, double kF, double setpoint){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.setpoint = setpoint;
    }
    public void set(int i, double v){
        switch(i) {
            case 0: kP = v;
            break;
            case 1: kI = v;
            break;
            case 2: kD = v;
            break;
            case 3: kF = v;
            break;
            case 4: setpoint = v;
            break;
        }
    }
    public double update(double pos){
        double output = 0;
        double dt = System.currentTimeMillis() - previousTime;
        double error = setpoint - pos;
        error = setpoint - pos;
        integral += error * dt;
        double derivative = (error - previousError) / dt;
        output = kP*error + kI*integral + kD*derivative + kF*setpoint;
        previousError = error;
        previousTime = dt;
        return output;
    }
}