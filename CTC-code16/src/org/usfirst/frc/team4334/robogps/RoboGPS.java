package org.usfirst.frc.team4334.robogps;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * @authors Jayden Chan, Ayla Chase
 * 
 * Date Created: December 28 2015
 * Last Updated: February 15 2016
 * 
 * @version 0.3 BETA
 * 
 * Class that uses encoder values on a tank drivetrain to return the coordinates and rotational value of the robot.
 * 
 */

public class RoboGPS 
{
    private double roboPosX, roboPosY, roboRotation;
    private double leftLast = 0, rightLast = 0, referenceAngle = 0;
    private Gyro gyro;
    private AHRS navx;
    
    public enum rotationMethod {
        GYRO, NAVX;
    }
    
    public RoboGPS(double startX, double startY, double startR, int gyroPort)
    {
        this.roboPosX = startX;
        this.roboPosY = startY;
        this.roboRotation = startR;
        this.gyro = new AnalogGyro(gyroPort);
    }
    public RoboGPS(double startX, double startY, double startR, AHRS navx)
    {
        this.roboPosX = startX;
        this.roboPosY = startY;
        this.roboRotation = startR;
        this.navx = navx;
    }
    
    public void init()
    {
        gyro.calibrate();
    }

    private double degreesToRadians(double deg)
    {
        return (deg * Math.PI) / 180;
    }
    
    public void update(double leftDistance, double rightDistance, rotationMethod rotationMethod)
    {
        double diffR = rightDistance - rightLast;
        double diffL = leftDistance - leftLast;
        rightLast = rightDistance;
        leftLast = leftDistance;
        switch(rotationMethod) {
        case GYRO: roboRotation = gyro.getAngle();
        case NAVX: roboRotation = navx.getAngle();
        }
        
        double magnitude = ((diffR + diffL)/2);
        
        roboPosX += magnitude * Math.sin(degreesToRadians(roboRotation));
        roboPosY += magnitude * Math.cos(degreesToRadians(roboRotation));
    }
    
    public double getX()
    {
        return roboPosX;
    }
    public double getY()
    {
        return roboPosY;
    }
    public double getR()
    {
        return roboRotation;
    }
}
