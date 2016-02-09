package robot.targeting;

import java.util.Collections;
import java.util.List;

import robocode.Bullet;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import robot.MyRobot;
import robot.board.Enemy;
import robot.board.EnemyComparator;
import robot.board.Point;

public class Target {
	
	private List<Enemy> enemies;
	private double angle;
	private double bearing;
	private Bullet bullet;
	
	public Target(List<Enemy> enemies){
		this.enemies = enemies;
	}
	
	public void autoTarget(MyRobot myRobot){
		
		if(!enemies.isEmpty()){
			
//			myRobot.setTurnGunRight(myRobot.getHeading() - myRobot.getGunHeading() + enemies.get(0).getBearing());
//			myRobot.fire(10);
			bearing = (myRobot.getHeading() + enemies.get(0).getBearing());
			double bearingFromGun = Utils.normalRelativeAngle(bearing - myRobot.getGunHeading());
		
			if(Math.abs(bearingFromGun) <= 3){
				myRobot.turnGunRight(bearingFromGun);	
				if (myRobot.getGunHeat() == 0) {
					final Bullet lbullet = myRobot.fireBullet(Math.min(3 - Math.abs(bearingFromGun), enemies.get(0).getEnergy() - .1));
					bullet = lbullet;
				}
				else {
					  myRobot.turnGunRight(bearingFromGun);
				}
	}
}
		
//		if(!enemies.isEmpty()){	
//			bearing = (myRobot.getHeading() + enemies.get(0).getBearing());
//			if (bearing < 0){ 
//				bearing += 360;
//			}
//			
//				myRobot.turnGunLeft(bearing);
//				System.out.println(bearing);
//			while(myRobot.getGunTurnRemaining() == 0){
//				myRobot.fire(10);
//			}
//			
//		}	
	}
	public void updateEnemies(List<Enemy> enemies){
		this.enemies = enemies;
		Collections.sort(enemies, new EnemyComparator());
	}
}
