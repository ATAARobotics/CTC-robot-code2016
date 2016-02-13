package acilibj.actuators;

import edu.wpi.first.wpilibj.SpeedController;
 /**
  * @author Jayden
  * Date Created: April 17 2015
  * Last Updated: August 18 2015
  * 
  * Class that can be used to construct working code for a kiwi drivetrain. Supports 3 or 6 CIM drivetrains.
  */
public class KiwiDrivetrain 
{
    SpeedController left, right, middle;
    
    public KiwiDrivetrain(SpeedController left, SpeedController right, SpeedController middle)
    { //3 CIM kiwi drivetrain
        this.left = left;
        this.right = right;
        this.middle = middle;
    }
    
    public KiwiDrivetrain(SpeedController L1, SpeedController L2, SpeedController R1, SpeedController R2, SpeedController M1, SpeedController M2)
    { //6 CIM kiwi drivetrain
        this.left = new SpeedControllerArray(new SpeedController[]{L1, L2});
        this.right = new SpeedControllerArray(new SpeedController[]{R1, R2});
        this.middle = new SpeedControllerArray(new SpeedController[]{M1, M2});
    }
    
    public void getKiwi(double xAxis, double yAxis, double zAxis, double speedMod)
    { //Y axis moves forward and back, X axis moves left and right, Z axis rotates [relative to the robot]
        middle.set(-((xAxis - zAxis) * speedMod));
        left.set((((0.5 * xAxis) - (Math.sqrt(3/2) * yAxis)) + zAxis) * speedMod);
        right.set((((0.5 * xAxis) + (Math.sqrt(3/2) * yAxis)) + zAxis) * speedMod);
    }
    
    public void getfKiwi(double xAxis, double yAxis, double zAxis, double speedMod, double theta)
    { // Drives robot relative to the field rather than relative to itself.
        double x, y, z;
        //Calculate required values for x, y, and z
        x = xAxis * Math.cos(theta) + yAxis * Math.sin(theta);
        y = -xAxis * Math.sin(theta) + yAxis * Math.cos(theta);
        z = zAxis;
        //Apply those values to the motors
        middle.set(x + z);
        left.set(-x/2 + 0.866 * (y) + z);
        right.set(-x/2 - 0.866 * (y) + z);
    }
}
