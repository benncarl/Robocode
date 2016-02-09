package robot.targeting;

import java.util.Collections;
import java.util.List;

import robocode.util.Utils;
import robot.MyRobot;
import robot.board.Enemy;
import robot.board.EnemyComparator;

public class Target {
	
	private List<Enemy> enemies;
	
	public Target(List<Enemy> enemies){
		this.enemies = enemies;
	}
	
	public void autoTarget(MyRobot myRobot){
		if(!enemies.isEmpty()){
			Enemy enemy = enemies.get(0);
			
			double absoluteBearing = Utils.normalRelativeAngleDegrees(myRobot.getPoint().headingFrom(enemy.getPoint()));
			double bearingFromGun = Utils.normalRelativeAngleDegrees(absoluteBearing - myRobot.getGunHeading());
			
			if(Math.abs(bearingFromGun) <= 3){
				myRobot.turnGunRight(bearingFromGun);	
				if (myRobot.getGunHeat() == 0) {
					double myEnergy = myRobot.getEnergy();
					double basePower = myEnergy > 70.0 ? 3.0 :
						               myEnergy > 35.0 ? 2.0 : 1.0;
					
				    double distance = enemy.getLastDistance();
					double distanceMod = distance < 250 ? 1.0  :
						                 distance < 500 ? 0.66 : 0.33;
					
					myRobot.fire(basePower * distanceMod);
				}
			} else {
				myRobot.setTurnGunRight(bearingFromGun);
			}
			
		}
	}
	
	public void updateEnemies(List<Enemy> enemies){
		Collections.sort(enemies, new EnemyComparator());
		this.enemies = enemies;
	}
}
