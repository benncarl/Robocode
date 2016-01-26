package robot.board;

public class Point {

	protected double x;
	protected double y;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double distanceFrom(Point point) {
		double x = Math.abs(this.x - point.getX());
		double y = Math.abs(this.y - point.getY());
		
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public double headingFrom(Point point) {
		double x = this.x - point.getX();
		double y = this.y - point.getY();
		double d = this.distanceFrom(point);
		
		double xAngle = Math.toDegrees(Math.asin(Math.abs(x)/d));
		
		return this.getHeadingFromXAngle(xAngle, x, y);
	}
	
	private double getHeadingFromXAngle(double xAngle, double x, double y) {
		return x >= 0 && y >= 0 ? xAngle :
			   x >= 0 && y <= 0 ? 180 - xAngle :
			   x <= 0 && y <= 0 ? 180 + xAngle :
			                      360 - xAngle;
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
