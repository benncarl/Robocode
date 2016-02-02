package robot;

import robocode.AdvancedRobot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robot.board.Board;
import robot.board.Point;
import robot.movement.Movement;

public class MyRobot extends AdvancedRobot {
	
	private Board board = new Board(this);
	private Movement movement;
	
	@Override
	public void run() {
		this.movement = new Movement(this.getBattleFieldHeight(), this.getBattleFieldWidth());
		this.setAdjustGunForRobotTurn(true);
		
		while(true){
			this.turnGunLeft(5);
			this.movement.move(this);
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		board.updateEnemy(e);
		movement.updateGravPoints(this.board.getEnemyList(), this.getX(), this.getY());
		
		this.fire(3);
	}
	
	public void moveToDestination(double distance, Point destination) {
		double dy = destination.getY() - this.getY();
		double dx = destination.getX() - this.getX();
		
		double bearing = 90 - Math.toDegrees(Math.atan2(dy, dx));
		
		if(this.getTurnRemaining() <= 0 && distance * 1000 > 5) {
			if(bearing <= 90) {
				this.setTurnRight(bearing);
			} else {
				this.setTurnLeft(bearing);
			}
		}
		
		int moveDir = dy <= 0 ? 1 : -1;
		
		this.setAhead(moveDir * distance * 1000);
		this.execute();
	}
	
	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		board.removeEnemy(e.getName());
	}
	
	public Point getPoint() {
		return new Point(this.getX(), this.getY());
	}
}
