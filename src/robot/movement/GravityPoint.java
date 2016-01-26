package robot.movement;

import robot.board.Point;

public class GravityPoint extends Point {

	private double strength;
	
	public GravityPoint(Point point, double strength) {
		super(point.getX(), point.getY());
		this.strength = strength;
	}
	
	public GravityPoint(double x, double y, double strength) {
		this(new Point(x, y), strength);
	}

	public double getStrength() {
		return strength;
	}
	public void setStrength(double strength) {
		this.strength = strength;
	}
}
