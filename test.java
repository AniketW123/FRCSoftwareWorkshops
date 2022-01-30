import java.util.Scanner;
public class test {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        while (true){
            double throttle = input.nextDouble();
            double turn = input.nextDouble()1;
            double LT;
            double RT;
            if (Math.signum(throttle) != 0) {
                LT = Math.signum(throttle) * turn;
                RT = Math.signum(throttle) * -turn;
            } else {
                LT = turn;
                RT = -turn;
            }
            System.out.println((throttle+LT)/2);
            System.out.println((throttle+RT)/2);
    }
    }
}