package gravbot;

import gravbot.board.Point;

public abstract class PositionUtils {

	public static int getXDirectionFromHeading(double absoluteHeading) {
		return absoluteHeading < 180 ? 1 : -1;
	}
	
	public static int getYDirectionFromHeading(double absoluteHeading) {
		return absoluteHeading > 270 || absoluteHeading < 90 ? 1 : -1;
	}
	
	public static Point triangulatePoint(double absoluteHeading, double distance, Point origin) {
		double xAngle = getXAngleFromHeading(absoluteHeading);
		double yAngle = 90 - xAngle;
		
		int xDirection = getXDirectionFromHeading(absoluteHeading);
		int yDirection = getYDirectionFromHeading(absoluteHeading);
		
		double x = xDirection * distance * Math.sin(Math.toRadians(xAngle));
		double y = yDirection * distance * Math.sin(Math.toRadians(yAngle));
		
		return new Point(origin.getX() + x, origin.getY() + y);
	}
	
	public static double getXAngleFromHeading(double absoluteHeading) {
		return absoluteHeading < 90  ? absoluteHeading : 
			   absoluteHeading < 180 ? 180 - absoluteHeading :
			   absoluteHeading < 270 ? absoluteHeading - 180 : 
			    	                   360 - absoluteHeading;
	}
}
