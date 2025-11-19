package org.firstinspires.ftc.teamcode.controllers;

public class TurretMath
{
    double x, y, xv, yv, xa, ya;
    public double angularVelocity(double deltaX, double deltaY, double xVel, double yVel){
        double aV = (yVel*deltaX-xVel*deltaY)/((deltaY*deltaY)+(deltaX*deltaX));
        return aV;
    }
    public double angularAcceleration(double deltaX, double deltaY, double xVel, double yVel, double xAc, double yAc){
        double sq = (deltaY*deltaY)+(deltaX*deltaX);
        double aA = ((deltaX * yAc - deltaY * xAc) * (sq)-(deltaX * yVel - deltaY * xVel) * (2 * (deltaX * xVel + deltaY * yVel)))/(sq*sq);
        return aA;
    }
}
