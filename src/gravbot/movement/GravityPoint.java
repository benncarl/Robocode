package gravbot.movement;

import gravbot.board.Point;

public class GravityPoint extends Point {

	private double strength;
	private boolean isWallPoint;
	
	public GravityPoint(Point point, double strength, boolean isWallPoint) {
		super(point.getX(), point.getY());
		this.strength = strength;
		this.isWallPoint = isWallPoint;
	}
	
	public GravityPoint(double x, double y, double strength, boolean isWallPoint) {
		this(new Point(x, y), strength, isWallPoint);
	}

	public double getStrength() {
		return strength;
	}
	public void setStrength(double strength) {
		this.strength = strength;
	}

	public boolean isWallPoint() {
		return isWallPoint;
	}
	public void setWallPoint(boolean isWallPoint) {
		this.isWallPoint = isWallPoint;
	}
}
