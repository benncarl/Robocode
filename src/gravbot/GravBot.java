package gravbot;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import robocode.AdvancedRobot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import gravbot.board.Board;
import gravbot.board.Point;
import gravbot.movement.Movement;
import gravbot.targeting.Target;

public class GravBot extends AdvancedRobot {
	
	private Board board = new Board(this);
	private Movement movement;
	private Target scanner;
	
	@Override
	public void run() {
		this.movement = new Movement(this.getBattleFieldHeight(), this.getBattleFieldWidth());
		this.scanner = new Target(this.board.getEnemyList());
		this.setAdjustGunForRobotTurn(true);
		this.setAdjustRadarForGunTurn(true);
		this.setAdjustRadarForRobotTurn(true);
		
		while(true){
			this.setTurnRadarLeft(360);
			this.movement.move(this); 
			this.scanner.autoTarget(this);
			this.execute();
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		board.updateEnemy(e);
		movement.updateGravPoints(this.board.getEnemyList(), this.getX(), this.getY());
		scanner.updateEnemies(this.board.getEnemyList());
	}
	
	public void moveToDestination(double distance, Point destination) {
		double absoluteBearing = Utils.normalRelativeAngleDegrees(this.getPoint().headingFrom(destination));
		double bearing = Utils.normalRelativeAngleDegrees(absoluteBearing - this.getHeading());
		
		int moveDir = Math.abs(bearing) > 90 ? -1 : 1;
		
		if(Math.abs(bearing) > 90) {
			if(bearing > 0) {
				bearing -= 180;
			} else {
				bearing += 180;
			}
		}
		
		if(distance < 50) distance = 50;
		
		this.setTurnRight(bearing);
		this.setAhead(moveDir * distance);
	}
	
	@Override public void onPaint(Graphics2D g) {
		g.setStroke(new BasicStroke(10));
		g.drawLine((int)this.getX(), (int)this.getY(), (int)this.movement.getDestination().getX(), (int)this.movement.getDestination().getY());
	}
	
	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		board.removeEnemy(e.getName());
	}
	
	public Point getPoint() {
		return new Point(this.getX(), this.getY());
	}
}
