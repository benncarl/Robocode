package robot;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robot.Board;

public class MyRobot extends AdvancedRobot {
	
	private Board board = new Board(this);
	
	@Override
	public void run() {
		while(true){
			this.turnGunLeft(5);
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		board.updateState(e);
		this.fire(3);
	}
}
