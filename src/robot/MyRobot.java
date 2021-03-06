package robot;

import java.awt.Graphics2D;

import robocode.AdvancedRobot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robot.board.Board;
import robot.board.Point;
import robot.movement.Movement;
import robot.targeting.Target;

public class MyRobot extends AdvancedRobot {
	
	private Board board = new Board(this);
	private Movement movement;
	private Target scanner;
	
	@Override
	public void run() {
		this.movement = new Movement(this.getBattleFieldHeight(), this.getBattleFieldWidth());
		this.scanner = new Target(this.board.getEnemyList());
		this.setAdjustRadarForGunTurn(true);
		this.setAdjustRadarForRobotTurn(true);
		
		while(true){
			this.setTurnRadarLeft(360);
			this.movement.move(this); 
			this.execute();
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		board.updateEnemy(e);
		movement.updateGravPoints(this.board.getEnemyList(), this.getX(), this.getY());
		this.scan();
		
	}
	
	public void moveToDestination(double distance, Point destination) {
		double dy = destination.getY() - this.getY();
		double dx = destination.getX() - this.getX();
		
		double angle = this.getPoint().headingFrom(destination);
		double bearing;
		
		if(dx > 0){
			bearing = 90 - Math.toDegrees(Math.atan2(dy, dx));
		} else if(dx < 0){
			bearing = 270 - Math.toDegrees(Math.atan2(dy, dx));
		} else{
			bearing = dy >= 0 ? 0 : 180;
		}
		
		int moveDir;
		if(bearing > 90) {
			bearing -= 180;
			moveDir = -1;
		} else if(bearing < -90) {
			bearing += 180;
			moveDir = -1;
		} else {
			moveDir = 1;
		}
		
		if(distance > 5) {
			this.setTurnLeft(bearing);
		}
		
		this.setAhead(moveDir * distance);
	}
	
	@Override
	public void onPaint(Graphics2D g) {
		Point destination = this.movement.getDestination();
		g.drawLine((int)this.getX(), (int)this.getY(), 
				(int)destination.getX(), (int)destination.getY());
		
	}
	
	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		board.removeEnemy(e.getName());
	}
	
	public Point getPoint() {
		return new Point(this.getX(), this.getY());
	}
}
