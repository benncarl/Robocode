package robot;

import robocode.AdvancedRobot;
import robocode.Bullet;
import robocode.ScannedRobotEvent;

public class MyRobot extends AdvancedRobot {

	private boolean targetFound;
	
	@Override
	public void run() {
		double heading = this.getHeading();
		if(heading > 180) {
			this.turnRight(heading - 180);
		} else {
			this.turnLeft(heading);
		}
		
		while(!targetFound) {
			this.turnGunRight(5);
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		targetFound = true;
		
		this.turnRight(e.getBearing());
		
		double myHeading = this.getHeading();
		double gunHeading = this.getGunHeading();
		
		double move = myHeading - gunHeading;
		
		this.turnGunLeft(move);
		
		
		Bullet bullet = this.fireBullet(3.0);
	}
}
