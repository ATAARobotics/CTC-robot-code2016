package org.usfirst.frc.team4334.actuators;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * @author Jayden Chan
 * Date Created: April 17 2015
 * Last Updated: February 15 2016
 * 
 * Class that constructs working code for a tank drivetrain. Supports 2 CIM, 4 CIM and 6 CIM configurations,
 *  as well as arcade mode and tank mode.
 */

public class TankDrivetrain 
{
    private final SpeedController left, right;
    
    public TankDrivetrain(SpeedController L1, SpeedController L2, SpeedController L3, SpeedController R1, SpeedController R2, SpeedController R3)
    { //4 CIM tank drive
        this.left = new SpeedControllerArray(new SpeedController[]{L1, L2, L3});
        this.right = new SpeedControllerArray(new SpeedController[]{R1, R2, R3});
    }
    
    public TankDrivetrain(SpeedController L1, SpeedController L2, SpeedController R1, SpeedController R2)
    { //4 CIM tank drive
        this.left = new SpeedControllerArray(new SpeedController[]{L1, L2});
        this.right = new SpeedControllerArray(new SpeedController[]{R1, R2});
    }
    
    public TankDrivetrain(SpeedController L1, SpeedController R1)
    { //2 CIM tank drive
        this.left = L1;
        this.right = R1;
    }
    
    public void setRaw(double leftPow, double rightPow)
    {
        left.set(leftPow);
        right.set(-rightPow);
    }
    
    public void getTank(double rightThumb, double leftThumb, double speedMod)
    { //Controls left and right sides of drivetrain with left and right thumbsticks respectively
        left.set(leftThumb * speedMod);
        right.set(rightThumb * speedMod);
    }
    public void getHalo(double rightThumb, double leftThumb, double speedMod, double turnMod)
    {
        left.set(((leftThumb + (rightThumb * turnMod)) * speedMod));
        right.set(-((leftThumb - (rightThumb * turnMod)) * speedMod));
    }
    public void getHaloA(double rightThumb, double leftThumb, double speedMod, double turnMod)
    { //Left thumbstick controls throttle and right thumbstick controls turning
        
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
