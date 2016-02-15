package org.usfirst.frc.team4334.driveControl;

import org.usfirst.frc.team4334.actuators.TankDrivetrain;
import org.usfirst.frc.team4334.robot.Robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveController {
	TankDrivetrain drive;
	public Encoder leftEnc;
	public Encoder rightEnc;
	AnalogGyro gyro;
	
	PidController master = new PidController(0.040 ,0.001,0.200,200,true);
	PidController slave = new PidController(0.020,0,0.080, 200, true);
	PidController turnPid = new PidController(0.05,0.0006,0.02,600,true);

	public DriveController(TankDrivetrain dr, Encoder left, Encoder right, AnalogGyro g){
		drive = dr;
		leftEnc = left;
		rightEnc = right;
		gyro = g;
	}
	
	public void driveFeet(double feet){
		double setPoint = feet * DriveConstants.TICKS_PER_FEET;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = 2 * 0.083333 * DriveConstants.TICKS_PER_FEET;
		
		int satTime = 2000;
		
		master.reset();
		slave.reset();
		
		int initLeft = leftEnc.get();
		int initRight = rightEnc.get();
		while(!atSetpoint && notDisabled()){
			master.sendValuesToDashboard("master " );
			slave.sendValuesToDashboard("slave ");
			//master.sendValuesToDashboard("drive");
			double driveErr = setPoint - (leftEnc.get() - initLeft);
			double slaveErr = (leftEnc.get() - initLeft) - (rightEnc.get() - initRight);			
			double driveOut = master.calculate(driveErr);
			double slaveOut = slave.calculate(slaveErr);
			
			SmartDashboard.putNumber("LEFT", leftEnc.get());
			SmartDashboard.putNumber("RIGHT", rightEnc.get());
			SmartDashboard.putNumber("Err ", driveErr);
			SmartDashboard.putNumber("Slave Err", slaveErr);
			SmartDashboard.putNumber("driveOut" , driveOut);
			SmartDashboard.putNumber("error sum", master.errorSum );
			
			System.out.println("not disabled : " + notDisabled());
			//System.out.println(driveErr);
			//System.out.println(driveOut);
			
			if(Math.abs(driveOut) > 0.6){
				driveOut = 0.6 * driveOut / Math.abs(driveOut);
			}
			
			if(Math.abs(slaveOut) > 0.6){
				slaveOut = 0.6 * slaveOut / Math.abs(slaveOut);
			}
			
			drive.setRaw(driveOut - slaveOut, driveOut + slaveOut );
			
			if(!(Math.abs(driveErr) < errorThresh)){
				initTime = System.currentTimeMillis();
			}
			else{
				if( System.currentTimeMillis() - initTime > satTime){
					atSetpoint = true;
					return;
				}
			}
			
			try{
				Thread.sleep(DriveConstants.THREAD_SLEEP_MS);
			}
			catch(Exception e){
			
			}
			
		}
		drive.setRaw(0, 0);
		
	}
	
	private boolean notDisabled(){
		return Robot.gameState != Robot.RobotStates.DISABLED;
	}
	
	
	public void turnDegreesRel(double degrees){
		turnDegreesAbsolute(gyro.getAngle() + degrees);
	}
	
	public void turnDegreesAbsolute(double degrees){
		double setPoint = degrees;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = 2;

		int satTime = 2000;
		
		turnPid.reset();
		slave.reset();
		
		int initLeft = leftEnc.get();
		int initRight = rightEnc.get();
		while(!atSetpoint && notDisabled()){
			turnPid.sendValuesToDashboard("turn");
			double driveErr = Utils.getAngleDifferenceDeg(setPoint,gyro.getAngle());
			//System.out.println("turn err " + driveErr + "   set " + setPoint + " actual " + gyro.getAngle());
			double slaveErr = (leftEnc.get() - initLeft) + (rightEnc.get() - initRight);			
			double driveOut = turnPid.calculate(driveErr);
			double slaveOut = slave.calculate(slaveErr);
			
			SmartDashboard.putNumber("gyro heading", gyro.getAngle());
			SmartDashboard.putNumber("LEFT", leftEnc.get());
			SmartDashboard.putNumber("RIGHT", rightEnc.get());
			SmartDashboard.putNumber("Err ", driveErr);
			SmartDashboard.putNumber("Slave Err", slaveErr);
			SmartDashboard.putNumber("driveOut" , driveOut);
			SmartDashboard.putNumber("error sum", master.errorSum );
			//System.out.println(driveErr);
			
			if(Math.abs(driveOut) > 0.6){
				driveOut = 0.6 * driveOut / Math.abs(driveOut);
			}
			
			if(Math.abs(slaveOut) > 0.6){
				slaveOut = 0.6 * slaveOut / Math.abs(slaveOut);
			}
			
			slaveOut = 0;
			drive.setRaw(driveOut - slaveOut, -(driveOut + slaveOut )  );
			
			if(!(Math.abs(driveErr) < errorThresh)){
				initTime = System.currentTimeMillis();
			}
			else{
				if( System.currentTimeMillis() - initTime > satTime){
					return;
				}
			}
			
			try{
				Thread.sleep(DriveConstants.THREAD_SLEEP_MS);
			}
			catch(Exception e){
			
			}
			
		}
		drive.setRaw(0, 0);
	}
	
	
	
	
	public void printEncoders(){
		SmartDashboard.putNumber("LEFT", leftEnc.get());
		SmartDashboard.putNumber("RIGHT", rightEnc.get());
	}
	
	public void calc(){
	
	}
	

}
