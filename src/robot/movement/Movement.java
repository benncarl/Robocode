package robot.movement;

import java.util.ArrayList;
import java.util.List;

import robot.MyRobot;
import robot.PositionUtils;
import robot.board.Enemy;
import robot.board.Point;

public class Movement {

	private final static double ENEMY_STRENGTH = 3000;
	private final static double WALL_STRENGTH = 20000;
	
	private final List<GravityPoint> gravPoints = new ArrayList<GravityPoint>();
	
	private double boardHeight;
	private double boardWidth;
	private Point destination;
	
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
			
			int decayRate = gravPoint.isWallPoint() ? 3 : 2;
			double force = gravPoint.getStrength() * 1000 / Math.pow(distance, decayRate);
			
			xForce += getForceComponent(force, heading, true);
			yForce += getForceComponent(force, heading, false);
		}
		
		this.destination = new Point(robot.getX() + xForce, robot.getY() + yForce);
		double force = Math.sqrt(Math.pow(xForce, 2) + Math.pow(yForce, 2));
		
		robot.moveToDestination(force, this.destination);
	}
	
	public void updateGravPoints(List<Enemy> enemies, double myX, double myY) {
		this.gravPoints.clear();
		
		for(Enemy enemy : enemies) {
			this.gravPoints.add(new GravityPoint(enemy.getPoint(), ENEMY_STRENGTH, false));
		}
		
		this.addWallGravPoints(myX, myY);
	}
	
	private double getForceComponent(double force, double heading, boolean isXComponent) {
		double xAngle = PositionUtils.getXAngleFromHeading(heading);
		double yAngle = 90 - xAngle;
		
		double forceComponent;
		if(isXComponent) {
			int xDirection = PositionUtils.getXDirectionFromHeading(heading);
			forceComponent = xDirection * force * Math.sin(Math.toRadians(xAngle));
		} else {
			int yDirection = PositionUtils.getYDirectionFromHeading(heading);
			forceComponent = yDirection * force * Math.sin(Math.toRadians(yAngle));
		}
		
		return forceComponent;
	}
	
	private void addWallGravPoints(double myX, double myY) {
		this.gravPoints.add(new GravityPoint(myX, this.boardHeight, WALL_STRENGTH, true));
		this.gravPoints.add(new GravityPoint(this.boardWidth, myY, WALL_STRENGTH, true));
		this.gravPoints.add(new GravityPoint(myX, 0, WALL_STRENGTH, true));
		this.gravPoints.add(new GravityPoint(0, myY, WALL_STRENGTH, true));
	}
	
	public Point getDestination() {
		return this.destination;
	}
}
