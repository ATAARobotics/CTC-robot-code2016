package acilibj.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * @author Jayden Chan
 * Date Created: April 20 2015
 * Last Updated: December 11 2015
 * 
 * Class that makes it easier to work with analog potentiometers by 
 * giving the return type in degrees rather than a number between 0 & 5.
 */
public class Potentiometer implements PIDSource
{
	private final AnalogInput pot;
	private final double range;
	
	public Potentiometer(int port, int degrees)
	{ //Constructs potentiometer class using the given port and range
		this.pot = new AnalogInput(port);
		this.range = (degrees / 5);
	}
	
	public double getDegrees()
	{ //Returns value of potentiometer in degrees
		return (pot.getVoltage() * range);
	}
	
	public double getVoltage()
	{ //Returns value of potentiometer as a voltage
		return pot.getVoltage();
	}
	
	public double pidGet() 
	{ //Returns value of potentiometer in degrees (used for PID)
		return (pot.getVoltage() * range);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}
}
