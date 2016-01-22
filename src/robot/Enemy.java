package robot;

import robocode.ScannedRobotEvent;

public class Enemy {

	private String name;
	private long scanTime;

	private double energy;
	private double heading;
	private double velocity;
	
	private double x;
	private double y;
	
	public Enemy(ScannedRobotEvent e, Point point){
		this.updateState(e, point);
		
	}
	
	public void updateState(ScannedRobotEvent e, Point point) {
		
		this.name = e.getName();
		this.scanTime = e.getTime();
		
		this.energy = e.getEnergy();
		this.heading = e.getHeading();
		this.velocity = e.getVelocity();

		this.x = point.getX();
		this.y = point.getY();

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
	
}
