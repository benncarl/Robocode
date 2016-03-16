package gravbot.board;

import java.util.Comparator;

public class EnemyComparator implements Comparator<Enemy>{

	@Override
	public int compare(Enemy o1, Enemy o2) {
		return compare(o1.getLastDistance(), o2.getLastDistance());
	}

	private static int compare(double x, double y){
		return x < y ? -1
			 : x > y ? 1 
		     : 0;
	}
}
