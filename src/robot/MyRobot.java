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
		this.movement.move(this);
		
		while(true){
			this.turnGunLeft(5);
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		board.updateEnemy(e);
		movement.updateGravPoints(this.board.getEnemyList(), this.getX(), this.getY());
		
		this.fire(3);
	}
	
	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		board.removeEnemy(e.getName());
	}
	
	public Point getPoint() {
		return new Point(this.getX(), this.getY());
	}
}
