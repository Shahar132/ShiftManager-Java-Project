package Enums;

public enum Rank {
	WORKER, SUPERVISER, ADMINISTRATOR;

	public static Rank get(int i) {
		Rank[] ranks = Rank.values();
		if (i >= 0 && i < ranks.length) {
			return ranks[i];
		} else {
			throw new IllegalArgumentException("Invalid index for Rank: " + i);
		}
	}
}
