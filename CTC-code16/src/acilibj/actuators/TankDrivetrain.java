package acilibj.actuators;

import acilibj.robogps.RoboGPS;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * @author Jayden Chan
 * Date Created: April 17 2015
 * Last Updated: February 11 2016
 * 
 * Class that constructs working code for a tank drivetrain. Supports 2 CIM, 4 CIM and 6 CIM configurations,
 *  as well as arcade mode and tank mode.
 */

public class TankDrivetrain 
{
    private final SpeedController left, right;
    private RoboGPS gps = null;
    private Encoder rightD, leftD;
    private double FEET_PER_TICK = 0;
    
    public TankDrivetrain(SpeedController L1, SpeedController L2, SpeedController L3, SpeedController R1,
            SpeedController R2, SpeedController R3, int leftEncoderA, int leftEncoderB, int rightEncoderA, int rightEncoderB, int gyro)
    { //6 CIM tank drive
        this.left = new SpeedControllerArray(new SpeedController[]{L1, L2, L3});
        this.right = new SpeedControllerArray(new SpeedController[]{R1, R2, R3});
        this.rightD = new Encoder(leftEncoderA, leftEncoderB, true, EncodingType.k4X);
        this.leftD = new Encoder(rightEncoderA, rightEncoderB, true, EncodingType.k4X);
        this.gps = new RoboGPS(0, 0, 0, gyro);
    }
    public TankDrivetrain(SpeedController L1, SpeedController L2, SpeedController R1, SpeedController R2)
    { //4 CIM tank drive
        this.left = new SpeedControllerArray(new SpeedController[]{L1, L2});
        this.right = new SpeedControllerArray(new SpeedController[]{R1, R2});
    }
    public TankDrivetrain(SpeedController L1, SpeedController L2, SpeedController R1, SpeedController R2,
            int leftEncoderA, int leftEncoderB, int rightEncoderA, int rightEncoderB, int gyro)
    { //4 CIM tank drive
        this.left = new SpeedControllerArray(new SpeedController[]{L1, L2});
        this.right = new SpeedControllerArray(new SpeedController[]{R1, R2});
        this.rightD = new Encoder(leftEncoderA, leftEncoderB, true, EncodingType.k4X);
        this.leftD = new Encoder(rightEncoderA, rightEncoderB, true, EncodingType.k4X);
        this.gps = new RoboGPS(0, 0, 0, gyro);
    }
    public TankDrivetrain(SpeedController L1, SpeedController R1)
    { //2 CIM tank drive
        this.left = L1;
        this.right = R1;
    }
    public TankDrivetrain(SpeedController L1, SpeedController R1, 
            int leftEncoderA, int leftEncoderB, int rightEncoderA, int rightEncoderB, int gyro)
    { //2 CIM tank drive
        this.left = L1;
        this.right = R1;
        this.rightD = new Encoder(leftEncoderA, leftEncoderB, true, EncodingType.k4X);
        this.leftD = new Encoder(rightEncoderA, rightEncoderB, true, EncodingType.k4X);
        this.gps = new RoboGPS(0, 0, 0, gyro);
    }
    
    public double getX()
    {
        return gps.getX();
    }
    public double getY()
    {
        return gps.getY();
    }
    public double getR()
    {
        return gps.getR();
    }
    public void setFPT(double FPT)
    {
        FEET_PER_TICK = FPT;
    }
    public void init()
    {
        gps.init();
    }
    
    public void getTank(double rightThumb, double leftThumb, double speedMod)
    { //Controls left and right sides of drivetrain with left and right thumbsticks respectively
        left.set(leftThumb * speedMod);
        right.set(rightThumb * speedMod);
    }
    public void getHalo(double rightThumb, double leftThumb, double speedMod, double turnMod)
    {
        if(gps != null)
        {
            updateGps();
        }
        
        left.set(-((leftThumb - (rightThumb * turnMod)) * speedMod));
        right.set((leftThumb + (rightThumb * turnMod)) * speedMod);
    }
    private void updateGps()
    {
        gps.update((leftD.get() * FEET_PER_TICK), (rightD.get() * FEET_PER_TICK));
        SmartDashboard.putNumber("Right Encoder" , rightD.get());
        SmartDashboard.putNumber("Left Encoder", leftD.get());
    }
    public void getHaloA(double rightThumb, double leftThumb, double speedMod, double turnMod)
    { //Left thumbstick controls throttle and right thumbstick controls turning
        if(gps != null)
        {
            updateGps();
        }
        
        if(leftThumb > 0)
        { //If left thumbstick is pushed forward
            if(rightThumb > 0)
            { //If right thumbstick is pushed right
                left.set(-((leftThumb  - (rightThumb * turnMod)) * speedMod));
                right.set(leftThumb* speedMod);
            }
            else
            { //If right thumbstick is pushed left
                left.set(-(leftThumb* speedMod));
                right.set((leftThumb + (rightThumb * turnMod))  * speedMod);
            }
        }
        else if(leftThumb < 0)
        { //If left thumbstick is pushed backward
            if(rightThumb > 0)
            { //If right thumbstick is pushed right
                left.set(-(leftThumb* speedMod));
                right.set((leftThumb + (rightThumb * turnMod))  * speedMod);
            }
            else
            { //If right thumbstick is pushed left
                left.set(-((leftThumb - (rightThumb * turnMod))  * speedMod));
                right.set(leftThumb * speedMod);
            }
        }
        else
        { //If left thumbstick is centered
            left.set((rightThumb * speedMod));
            right.set((rightThumb * speedMod));
        }
    
    }
}
