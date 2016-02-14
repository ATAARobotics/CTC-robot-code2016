package org.usfirst.frc.team4334.driveControl;

public class Utils {
	
	public static double deadzone(double input, double min){
		return Math.abs(input) < Math.abs(min) ?  0 : input;
	}
	
	//returns input clamped between -max and max
	public static double clamp(double input, double max){
		if(Math.abs(input) > max){
			return max * input/Math.abs(input);
		}
		return input;
	}
	
	public static double signedMod(double a, double b){
		return a - Math.floor(a/b) * b;
	}
	

	public static double getAngleDifferenceDeg(double target, double source){
		double a = target - source;
		return Utils.signedMod(a + 180, 360) - 180;
	}
	
	public static double getAngleDifferenceRad(double target, double source){
		double a = target - source;
		return Utils.signedMod(a + Math.PI, 2 * Math.PI) - Math.PI;
	}
	
	
	
}
