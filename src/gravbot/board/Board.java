package gravbot.board;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gravbot.GravBot;
import gravbot.PositionUtils;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import gravbot.board.Point;

public class Board {
	
	private HashMap<String, Enemy> enemies = new HashMap<String, Enemy>();
	private GravBot me;
	
	DecimalFormat df = new DecimalFormat("#"); 
	
	public Board(GravBot me){
		this.me = me;
	}
	
	public void updateEnemy(ScannedRobotEvent e){
		double absoluteHeading = Utils.normalAbsoluteAngleDegrees(me.getHeading() + e.getBearing());
		Point enemyPoint = PositionUtils.triangulatePoint(absoluteHeading, e.getDistance(), me.getPoint());
		
		Enemy enemy = new Enemy(e, enemyPoint);
		enemies.put(enemy.getName(), enemy);
		
		this.printEnemies();
	}
	
	public List<Enemy> getEnemyList() {
		List<Enemy> enemyList = new ArrayList<Enemy>();
		
		for(Map.Entry<String, Enemy> enemy : this.enemies.entrySet()) {
			enemyList.add(enemy.getValue());
		}
		return enemyList;
	}
	
	public void removeEnemy(String name) {
		enemies.remove(name);
	}
	
	private void printEnemies() {
		System.out.println("-------Enemies-------");
		for(Map.Entry<String, Enemy> entry : this.enemies.entrySet()) {
			Enemy enemy = entry.getValue();
			System.out.println(enemy.getName() + ":\t" + df.format(enemy.getX()) + "x, " + df.format(enemy.getY()) + 
					             "y (" + df.format(enemy.getLastDistance()) + ")");
		}
		System.out.println("------------------------------");
	}
}
