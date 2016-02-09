package robot.board;

import java.util.Comparator;

public class EnemyComparator implements Comparator<Enemy>{

	@Override
	public int compare(Enemy o1, Enemy o2) {
		// TODO Auto-generated method stub
		int comparison = compare(o1.getLastDistance(), o2.getLastDistance());
		return comparison;
	}

	private static int compare(double x, double y){
		
		
		return x < y ? -1
			 : x > y ? 1 
		     : 0;
	}
}
