package org.usfirst.frc.team4334.actuators;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

/**
 * @author Jayden Chan
 * Date Created: April 18 2015
 * Last Updated: February 15 2016
 * 
 * Class that adds functionality to the existing WPI Joystick class.
 */
public class SuperJoystickModule
{
    private final Joystick joy;
    private boolean pre;
    
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
    public enum rumbleSetting {
        RIGHT, LEFT, BOTH;
    }
    
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
    
    public double getAxis(int axis, double deadzone, double multiplier)
    { //Returns a double value of the chosen axis. If the axis is within the chosen deadzone, method returns 0.
        return Math.abs(joy.getRawAxis(axis)) < deadzone ? 0 : joy.getRawAxis(axis) * multiplier;
    }
    
    public void setRumble(float strength, rumbleSetting rs)
    { //Sets the rumble modules in the controller. ayy lmao
        switch(rs) {
        case BOTH:  joy.setRumble(RumbleType.kLeftRumble, strength);
                    joy.setRumble(RumbleType.kRightRumble, strength);
        case LEFT:  joy.setRumble(RumbleType.kLeftRumble, strength);
        case RIGHT: joy.setRumble(RumbleType.kRightRumble, strength);
        }
    
    }
    
    public boolean getDpad(Direction direction)
    { // Check & return whether supplied direction is pressed or not.
        switch(direction)
        { 
            case UP:    return joy.getPOV(0) == 0;
            case RIGHT: return joy.getPOV(0) == 90;
            case DOWN:  return joy.getPOV(0) == 180;
            case LEFT:  return joy.getPOV(0) == 270;
        }
        return false;
    }
    
}
