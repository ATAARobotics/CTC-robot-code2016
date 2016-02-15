package org.usfirst.frc.team4334.robot;

import org.usfirst.frc.team4334.actuators.SuperJoystickModule;
import org.usfirst.frc.team4334.actuators.TankDrivetrain;

import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {
    
    Victor R1 = new Victor(Constants.RIGHT_MOTOR_1);
    Victor R2 = new Victor(Constants.RIGHT_MOTOR_2);
    Victor L1 = new Victor(Constants.LEFT_MOTOR_1);
    Victor L2 = new Victor(Constants.LEFT_MOTOR_2);
    SuperJoystickModule driver = new SuperJoystickModule(Constants.JOYSTICK_1);
    
    AHRS navx;
    
    TankDrivetrain drivetrain;
    
    public static RobotStates gameState = RobotStates.DISABLED;
    
    public static enum RobotStates {
        DISABLED,
        TELEOP,
        AUTO
    }
    
    public Robot() {
       navx = new AHRS(SPI.Port.kMXP);
    }
    
    public void robotInit() {
        drivetrain = new TankDrivetrain(L1, L2, R1, R2);
    }
    
    public void autonomous() {
        Robot.gameState = RobotStates.AUTO;
    }
    
    public void operatorControl() {
        Robot.gameState = RobotStates.TELEOP;
        
        while(isOperatorControl() && isEnabled()) {
            drivetrain.getHalo(driver.getAxis(4, 0.15, 1), 
                               driver.getAxis(1, 0.15, -1), 1, 0.8);
            
            SmartDashboard.putNumber("NavX angle", navx.getAngle());
            
            Timer.delay(0.05);
        }
    }
    
    public void test() {
        
    }
    
    public void disabled() {
        Robot.gameState = RobotStates.DISABLED;
    }
}
