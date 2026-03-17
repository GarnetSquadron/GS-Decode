package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.PurelyCalculators.TrajectoryMath;
//
//public class Runner {
//
//    public static void main(String[] args) {
//
//        double vel = Double.parseDouble(args[0]);
//        //double dist = Double.parseDouble(args[1]);
//
//        double[] angles = TrajectoryMath.getAngles(vel,100);
//
//        System.out.println(angles[0]);
//    }
//
//}
public class Runner {

    public static void main(String[] args) {

        System.out.println("hi");
        System.out.println("Args length: " + args.length);

        if(args.length == 0) {
            System.out.println("No distance provided");
            return;
        }

        double dist = Double.parseDouble(args[0]);

        double[] angles = TrajectoryMath.getAngles(10,dist);

        if(angles.length>0){System.out.println(angles[0]);}
    }
}
