package robot.movement;

import java.util.ArrayList;
import java.util.List;

import robot.MyRobot;
import robot.board.Enemy;
import robot.board.Point;

public class Movement {

	private final static double ENEMY_STRENGTH = -1;
	private final static double WALL_STRENGTH = -5;
	
	private final List<GravityPoint> gravPoints = new ArrayList<GravityPoint>();
	
	private double boardHeight;
	private double boardWidth;
	
	public Movement(double boardHeight, double boardWidth) {
		this.boardHeight = boardHeight;
		this.boardWidth = boardWidth;
	}
	
	public void move(MyRobot robot) {
		if(this.gravPoints.isEmpty()) {
			return;
		}
		
		double xForce = 0;
		double yForce = 0;
		
		for(GravityPoint gravPoint : gravPoints) {
			double distance = gravPoint.distanceFrom(robot.getPoint());
			double heading = gravPoint.headingFrom(robot.getPoint());
			
			double force = gravPoint.getStrength() / Math.pow(distance, 2);
			xForce += getForceComponent(force, heading, true);
			yForce += getForceComponent(force, heading, false);
		}
		
		double force = Math.sqrt(Math.pow(xForce, 2) + Math.pow(yForce, 2));
	}
	
	public void updateGravPoints(List<Enemy> enemies, double myX, double myY) {
		this.gravPoints.clear();
		
		for(Enemy enemy : enemies) {
			this.gravPoints.add(new GravityPoint(enemy.getPoint(), ENEMY_STRENGTH));
		}
		
		this.addWallGravPoints(myX, myY);
	}
	
	private double getForceComponent(double force, double heading, boolean isXComponent) {
		double xAngle = getXAngleFromHeading(heading);
		double yAngle = 90 - xAngle;
		
		double forceComponent;
		if(isXComponent) {
			int xDirection = heading < 180 ? 1 : -1;
			forceComponent = xDirection * force * Math.sin(Math.toRadians(xAngle));
		} else {
			int yDirection = heading > 270 || heading < 90 ? 1 : -1;
			forceComponent = yDirection * force * Math.sin(Math.toRadians(yAngle));
		}
		
		return forceComponent;
	}
	
	private double getXAngleFromHeading(double absoluteHeading) {
		return absoluteHeading < 90  ? absoluteHeading : 
			   absoluteHeading < 180 ? 180 - absoluteHeading :
			   absoluteHeading < 270 ? absoluteHeading - 180 : 
			    	                   360 - absoluteHeading;
	}
	
	private void addWallGravPoints(double myX, double myY) {
		this.gravPoints.add(new GravityPoint(myX, this.boardHeight, WALL_STRENGTH));
		this.gravPoints.add(new GravityPoint(this.boardWidth, myY, WALL_STRENGTH));
		this.gravPoints.add(new GravityPoint(myX, 0, WALL_STRENGTH));
		this.gravPoints.add(new GravityPoint(0, myY, WALL_STRENGTH));
	}
}
