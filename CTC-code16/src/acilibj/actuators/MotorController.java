package acilibj.actuators;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * @author Jayden Chan
 * Date Created: April 18 2015
 * Last Updated: April 18 2015
 * 
 * Simple class that controls motors with analog inputs or button inputs.
 */
public class MotorController 
{
	private final SpeedController motor;
	
	public MotorController(SpeedController motor)
	{ //Constructs the object using the chosen speed controller
		this.motor = motor;
	}
	
	public void manualControl(double axis, boolean inversed)
	{ //Controls the desired motor with one axis (usually a thumbstick)
		if(inversed)
		{ //If inversed inputs is selected, use the negative of the given axis
			motor.set(-axis);
		}
		else
		{ //If not get the normal value
			motor.set(axis);
		}	
	}
	
	public void manualControl(double axis1, double axis2, boolean squared)
	{ //Controls the desired motor with 2 axis (usually triggers)
		if(squared)
		{ //If squared inputs is selected, square root the given axis (because the axis input will be < 1)
			motor.set((Math.sqrt(axis1)) - (Math.sqrt(axis2)));
		}
		else
		{ //If squared inputs is not selected set the motor to the regular value
			motor.set(axis1 - axis2);
		}
	}
	
	public void manualControl(boolean button, boolean inverted)
	{ //Controls the motor with a button. (Either 100% or 0%)
		if(button)
		{ //While button is pressed, move motor at 100%
			if(inverted)
			{ //If inverted is selected, move at -100%
				motor.set(-1);
			}
			else
			{ //If not, move at 100%
				motor.set(1);
			}
		}
		else
		{ //Stop moving when button is not pressed
			motor.set(0);
		}
	}
	
}
