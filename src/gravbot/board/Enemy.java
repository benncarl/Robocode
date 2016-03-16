package gravbot.board;

import robocode.ScannedRobotEvent;
import gravbot.board.Point;

public class Enemy {

	private String name;
	private long scanTime;

	private double energy;
	private double heading;
	private double velocity;
	private double lastDistance;
	private double bearing;
	
	private double x;
	private double y;
	
	public Enemy(ScannedRobotEvent e, Point enemyPoint){
		this.name = e.getName();
		this.scanTime = e.getTime();
		this.bearing = e.getBearing();
		this.energy = e.getEnergy();
		this.heading = e.getHeading();
		this.velocity = e.getVelocity();
		this.lastDistance = e.getDistance();

		this.x = enemyPoint.getX();
		this.y = enemyPoint.getY();
	}
	
	public Point getPoint() {
		return new Point(this.x, this.y);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getScanTime() {
		return scanTime;
	}
	public void setScanTime(long scanTime) {
		this.scanTime = scanTime;
	}

	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getHeading() {
		return heading;
	}
	public void setHeading(double heading) {
		this.heading = heading;
	}

	public double getVelocity() {
		return velocity;
	}
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getLastDistance() {
		return lastDistance;
	}
	public void setLastDistance(double lastDistance) {
		this.lastDistance = lastDistance;
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}
}
