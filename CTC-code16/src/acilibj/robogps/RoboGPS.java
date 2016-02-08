package acilibj.robogps;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * @authors Jayden Chan, Ayla Chase
 * 
 * Date Created: December 28 2015
 * Last Updated: January 20 2015
 * 
 * @version 0.2 BETA
 * 
 * Class that uses encoder values on a tank drivetrain to return the coordinates and rotational value of the robot.
 * 
 */

public class RoboGPS 
{
	private double roboPosX, roboPosY, roboRotation;
	private double leftLast = 0, rightLast = 0, referenceAngle = 0;
	private Gyro gyro;
	
	public RoboGPS(double startX, double startY, double startR, int gyroPort)
	{
		this.roboPosX = startX;
		this.roboPosY = startY;
		this.roboRotation = startR;
		this.gyro = new AnalogGyro(gyroPort);
	}
	
	public void init()
	{
		gyro.calibrate();
	}

	public double degreesToRadians(double deg)
	{
		return (deg * Math.PI) / 180;
	}
	
	public void update(double leftDistance, double rightDistance)
	{
		double diffR = rightDistance - rightLast;
		double diffL = leftDistance - leftLast;
		rightLast = rightDistance;
		leftLast = leftDistance;
		roboRotation = gyro.getAngle() - referenceAngle;
		
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
