package org.usfirst.frc.team4334.actuators;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

/**
 * @author Jayden Chan
 * Date Created: April 18 2015
 * Last Updated: February 13 2016
 * 
 * Class that adds functionality to the existing WPI Joystick class.
 */
public class SuperJoystickModule
{
    private final Joystick joy;
    private boolean pre;
    
    public SuperJoystickModule(int port)
    { //Constructing SuperControllerModule object with chosen joystick.
        //this.joy = joy;
        joy = new Joystick(port);
    }
    
    public void doWhenPressed(int button, Runnable action)
    { //Runs chosen object when chosen button is pressed. Does not repeat if held down.
        if((joy.getRawButton(button)) && (!pre))
        { //Check if button is pressed and make sure it's not being held down
            pre = true;
            action.run();
        }
        else if(!joy.getRawButton(button))
        { // Set the pre variable to false when button is released
            pre = false;
        }
    }
    
    public boolean getButton(int button)
    { //Returns button press as boolean
        return joy.getRawButton(button);
    }
    
    public double getRawAxis(int axis, double multiplier)
    { //Returns axis value as double
        return (joy.getRawAxis(axis) * multiplier);
    }
    
    public double getAxis(int axis, double deadzone, double multiplier)
    { //Returns a double value of the chosen axis. If the axis is within the chosen deadzone, method returns 0.
        return Math.abs(joy.getRawAxis(axis)) < deadzone ? 0 : joy.getRawAxis(axis) * multiplier;
    }
    
    public void setRumble(float strength, int setting)
    { //Sets the rumble modules in the controller. ayy lmao
        
        //Setting 0: Both left & right rumble
        //Setting 1: Left rumble only
        //Setting 2: Right rumble only
        
        switch(setting)
        {
        case 0: joy.setRumble(RumbleType.kLeftRumble, strength);
                joy.setRumble(RumbleType.kRightRumble, strength);
        case 1: joy.setRumble(RumbleType.kLeftRumble, strength);
        case 2: joy.setRumble(RumbleType.kRightRumble, strength);
        }
    
    }
    
    public boolean getDpad(int direction)
    {
        /*
         Directions:
         1 = Up
         2 = Right
         3 = Down
         4 = Left
        */
        
        if(direction > 4 || direction < 1)
        { //Check if supplied direction is valid
            throw new IllegalArgumentException("Direction provided was invalid");
        }
        
        // Check & return whether supplied direction is pressed or not.
        switch(direction)
        { 
            case 1: return joy.getPOV(0) == 0;
            case 2: return joy.getPOV(0) == 90;
            case 3: return joy.getPOV(0) == 180;
            case 4: return joy.getPOV(0) == 270;
        }
        return false;
    }
    
}
