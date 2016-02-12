package org.usfirst.frc.team4334.robot;

import acilibj.actuators.SuperJoystickModule;
import acilibj.actuators.TankDrivetrain;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends SampleRobot 
{
    Victor R1 = new Victor(Constants.RIGHT_MOTOR);
    Victor L1 = new Victor(Constants.LEFT_MOTOR);
    SuperJoystickModule driver = new SuperJoystickModule(Constants.JOYSTICK_1);
    
    TankDrivetrain drivetrain;
    
    public Robot() 
    {
       
    }
    
    public void robotInit() 
    {
        drivetrain = new TankDrivetrain(L1, R1);
    }
    
    public void autonomous()
    {
        
    }
    
    public void operatorControl()
    {
        while(isOperatorControl() && isEnabled())
        {
            drivetrain.getHalo(driver.getAxisWithDeadzone(4, 0.15, false), 
                               driver.getAxisWithDeadzone(1, 0.15, true), 1, 0.8);
            
            Timer.delay(0.05);
        }
    }
    
    public void test() 
    {
        
    }
}
