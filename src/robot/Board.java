package robot;

import java.util.HashMap;

import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class Board {
	
	private HashMap<String, Enemy> enemies = new HashMap<String, Enemy>();
	private MyRobot me;
	
	public Board(MyRobot me){
		this.me = me;
		
	}
	
	public void updateState(ScannedRobotEvent e){
		
		double absoluteHeading = Utils.normalAbsoluteAngleDegrees(me.getHeading() + e.getBearing());
		Point enemyPoint = triangulate(absoluteHeading, e.getDistance(), me.getX(), me.getY());
		
		Enemy enemy = enemies.get(e.getName());
		
		if(enemy == null){
			enemy = new Enemy(e, enemyPoint);
		}
		else{
			enemy.updateState(e, enemyPoint);
		}
		
		System.out.println("myX: " + me.getX() + ", myY: " + me.getY());
		System.out.println("x: " + enemyPoint.getX() + ", y: " + enemyPoint.getY());

		
	}
	
	public Point triangulate(double absoluteHeading, double distance, double myX, double myY){
		
		Point point;
		double knownAngle;
		
		if(absoluteHeading == 0){
			point = new Point(myX, myY - distance);	
		}
		else if(absoluteHeading == 90){
			point = new Point(myX + distance, myY);
		}
		else if(absoluteHeading == 180){
			point = new Point(myX, myY + distance);
		}
		else if(absoluteHeading == 270){
			point = new Point(myX - distance, myY);
		}
		else{
			
			if(absoluteHeading < 90){
				knownAngle = absoluteHeading;
			}
			else if(absoluteHeading < 180){
				knownAngle = 180 - absoluteHeading;
			}
			else if(absoluteHeading < 270){
				knownAngle = absoluteHeading - 180;
			}
			else{
				knownAngle = 360 - absoluteHeading;
			}
			
			double unknownAngle =  90 - knownAngle;
			
			int xDirection = absoluteHeading > 180 ? -1 : 1;
			int yDirection = absoluteHeading > 270 || absoluteHeading < 90 ? 1 : -1;
			
			double x = xDirection * distance * Math.sin(knownAngle);
			double y = yDirection * distance * Math.sin(unknownAngle);
			
			point = new Point(myX + x, myY + y);
		}
		
		return point;
		
	}

}
