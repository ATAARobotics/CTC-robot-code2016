package org.usfirst.frc.team4334.actuators;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * @author Jayden Chan
 * Date Created: April 17 2015
 * Last Updated: April 17 2015
 */
public class SolenoidController 
{
    private final DoubleSolenoid piston;
    
    public SolenoidController(int openPort, int closedPort)
    { //Constructs the Solenoid controller using the given solenoid
        this.piston = new DoubleSolenoid(openPort, closedPort);
    }
    
    public void toggle()
    { //Toggles the position of the solenoid. Useful for buttons in teleop
        if(piston.get() == DoubleSolenoid.Value.kForward)
        { //If piston is forward set to reverse
            piston.set(DoubleSolenoid.Value.kReverse);
        }
        else
        { //If piston is reverse set to forward
            piston.set(DoubleSolenoid.Value.kForward);
        }
    }
    
    public void setForward()
    { //Sets solenoid to forward position. Useful for auto
        piston.set(DoubleSolenoid.Value.kForward);
    }
    
    public void setReverse()
    { //Sets the solenoid to reverse position. Useful for auto
        piston.set(DoubleSolenoid.Value.kReverse);
    }
}
