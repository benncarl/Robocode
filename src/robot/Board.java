package robot;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class Board {
	
	private HashMap<String, Enemy> enemies = new HashMap<String, Enemy>();
	private MyRobot me;
	
	DecimalFormat df = new DecimalFormat("#"); 
	
	public Board(MyRobot me){
		this.me = me;
	}
	
	public void updateState(ScannedRobotEvent e){
		double absoluteHeading = Utils.normalAbsoluteAngleDegrees(me.getHeading() + e.getBearing());
		Point enemyPoint = triangulate(absoluteHeading, e.getDistance());
		
		Enemy enemy = new Enemy(e, enemyPoint);
		enemies.put(enemy.getName(), enemy);
		
		this.printEnemies();
	}
	
	public Point triangulate(double absoluteHeading, double distance){
		double xAngle = getXAngleFromHeading(absoluteHeading);
		double yAngle = 90 - xAngle;
		
		int xDirection = absoluteHeading < 180 ? 1 : -1;
		int yDirection = absoluteHeading > 270 || absoluteHeading < 90 ? 1 : -1;
		
		double x = xDirection * distance * Math.sin(Math.toRadians(xAngle));
		double y = yDirection * distance * Math.sin(Math.toRadians(yAngle));
		
		return new Point(this.me.getX() + x, this.me.getY() + y);
	}
	
	private double getXAngleFromHeading(double absoluteHeading) {
		return absoluteHeading < 90  ? absoluteHeading : 
			   absoluteHeading < 180 ? 180 - absoluteHeading :
			   absoluteHeading < 270 ? absoluteHeading - 180 : 
			    	                   360 - absoluteHeading;
	}

	private void printEnemies() {
		System.out.println("-------Enemies-------");
		for(Map.Entry<String, Enemy> entry : this.enemies.entrySet()) {
			Enemy enemy = entry.getValue();
			System.out.println(enemy.getName() + ":\t" + df.format(enemy.getX()) + "x, " + df.format(enemy.getY()) + 
					             "y (" + df.format(enemy.getLastDistance()) + ")");
		}
		System.out.println("------------------------------");
	}
}
