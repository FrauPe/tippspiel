package server;

import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public final class User {

	private final String name, password;
	private final TreeMap<Integer, Result> bets = new TreeMap<>();

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String setBet(String game, String goals1, String goals2) {
		boolean parsedGame = false;
		try {
			int iGame = Integer.parseInt(game);
			parsedGame = true;
			bets.put(iGame, new Result(Integer.parseInt(goals1), Integer.parseInt(goals2)));
			return "+OK Tipp für Spiel-Nr. " + game + " erhalten.";
		} catch(NumberFormatException e) {
			return parsedGame ?
				"-ERR Der Tipp ist ungültig." :
				"-ERR Die Spiel-Nr. " + game + " ist ungültig.";
		}
	}

	public String getBet(String game) {
		try {
			Result r = bets.get(Integer.parseInt(game));
			return r == null ?
				"-ERR Du hast noch keinen Tipp abgegeben." :
				"+OK Dein Tipp für Spiel " + game + " ist " + r.goals1 + ':' + r.goals2 + '.';
		} catch(NumberFormatException e) {
			return "-ERR Die Spiel-Nr. ist ungültig.";
		}
	}

	public String getName() {
		return name;
	}

	public boolean isPasswordCorrect(String password) {
		return this.password.equals(password);
	}

	public Set<Entry<Integer, Result>> getBets() {
		return bets.entrySet();
	}

}
