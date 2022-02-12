import java.util.Scanner;
public class test {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        while (true){
            double throttle = input.nextDouble();
            double turn = input.nextDouble();
            double LT;
            double RT;
            if (Math.signum(throttle) != 0) {
                LT = Math.signum(throttle) * turn;
                RT = Math.signum(throttle) * -turn;
            } else {
                LT = turn;
                RT = -turn;
            }
            System.out.println(Math.max(-1, Math.min(throttle + LT, 1)));
            System.out.println(Math.max(-1, Math.min(throttle + RT, 1)));
    }
    }
}