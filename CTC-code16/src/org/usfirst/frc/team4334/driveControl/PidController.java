package org.usfirst.frc.team4334.driveControl;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class PidController {
	//our gains, we will define these later
	private double kP;
	private double kI;
	private double kD;
	
	//episilon is the amount of error we are happy with (error no longer integrates when error falls below epsilon)
	private double epsilon;
	private double slewRate;
	private double integralLimit;
	
	public double errorSum;
	private double lastError;
	private long lastTime;
	
	private boolean zeroOnCross;
	
	public PidController(double p, double i, double d, double intLim, boolean zeroOnCross){
		kP = p;
		kI = i;
		kD = d;
		integralLimit = intLim;
		
		epsilon = 0;
		errorSum = 0;
		lastError = 0;
		//on by default
		zeroOnCross = true;
	}
	
	public void setZeroOnCross(boolean zero){
		zeroOnCross = zero;
	}
	
	public void reset(){
		errorSum = 0;
		lastError = 0;
	}
	
	public double calculate(double error){
		long dTime = (System.currentTimeMillis() - lastTime)/10;
		//System.out.println("dtime = " + dTime + "  " + errorSum);
		
		lastTime = System.currentTimeMillis();
		double changeInError = 0;
		if(Math.abs(error) > epsilon){
			errorSum += error * dTime;
		} else{
			errorSum = 0;
		}
		
		//limits error sum to +integralLimit or -integralLimit
		errorSum = Utils.clamp(errorSum, integralLimit);
		
		if(zeroOnCross){
			if(Math.signum(error) != Math.signum(lastError)){
				errorSum = 0;
			}
		}

		if(dTime != 0){
			changeInError = (error - lastError) / dTime;
		}
		else{
			changeInError = 0;
		}
		
		lastError = error;
		
		return kP * error + kI * errorSum + kD * changeInError;
	}
	boolean valuesSent = false;
	public void sendValuesToDashboard(String mod){
		if(!valuesSent){
			SmartDashboard.putNumber(mod + "_kP", kP);
			SmartDashboard.putNumber(mod + "_kI", kI);
			SmartDashboard.putNumber(mod + "_kD", kD);
			SmartDashboard.putNumber(mod + "_intlim", integralLimit);
			valuesSent = true;
		}
		kP = SmartDashboard.getNumber(mod + "_kP", kP);
		kI = SmartDashboard.getNumber(mod + "_kI", kI);
		kD = SmartDashboard.getNumber(mod + "_kD", kD);
		integralLimit = SmartDashboard.getNumber(mod + "_intlim", integralLimit);	
	}
	
	
}
