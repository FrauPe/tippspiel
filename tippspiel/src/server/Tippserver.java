package server;

import abiturklassen.netzklassen.Server;
import server.User.Bet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

public class Tippserver extends Server {

	private final String GENERAL_ERROR = "-ERR";
	private final String NOT_SIGNED_IN = "-ERR Du bist nicht angemeldet.";

	// TODO alle User hinzufügen (unique names)
	private final User[] users = {};
	private HashMap<NetInfo, User> activeUsers = new HashMap<>();

	public Tippserver() {
		super(2000);
	}

	@Override
	public void processNewConnection(String pClientIP, int pClientPort) {}

	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		NetInfo info = new NetInfo(pClientIP, pClientPort);
		String[] split = pMessage.split(" ");
		ArrayList<String> words = new ArrayList<>(split.length);
		for(String word : split)
			if(!word.isEmpty())
				words.add(word);
		send(pClientIP, pClientPort, answer(info, words));
	}
	
	@Override
	public void processClosedConnection(String pClientIP, int pClientPort) {}

	private String answer(NetInfo info, ArrayList<String> words) {
		if(words.size() == 3 && words.get(0).equals("ANMELDEN"))
			return signIn(info, words.get(1), words.get(2));
		User user = activeUsers.get(info);
		if(user == null)
			return NOT_SIGNED_IN;
		switch(words.size()) {
		case 4:
			if(words.get(0).equals("TIPP"))
				return user.setBet(words.get(1), words.get(2), words.get(3));
			break;
		case 2:
			if(words.get(0).equals("SPIEL"))
				return user.getBet(words.get(1));
			break;
		case 1:
			if(words.get(0).equals("PUNKTE"))
				return points(user.getBets());
			if(words.get(0).equals("ABMELDEN"))
				return signOut(info);
		}
		return GENERAL_ERROR;
	}

	private String signIn(NetInfo info, String name, String pass) {
		User user = null;
		for(User u : users) {
			if(!u.getName().equals(name))
				continue;
			user = u;
			break;
		}
		if(user == null)
			return "-ERR " + name + " ist unbekannt.";
		if(!user.isPasswordCorrect(pass))
			return "-ERR Das Kennwort ist falsch.";
		if(activeUsers.containsValue(user))
			return "-ERR Der Name " + name + " ist bereits angemeldet.";
		activeUsers.put(info, user);
		return "+OK " + name + " ist angemeldet.";
	}

	private String points(Set<Entry<Integer, Bet>> bets) {
		int points = 0;
		for(Entry<Integer, Bet> e : bets) {
			int game = e.getKey();
			int goals1 = e.getValue().goals1;
			int goals2 = e.getValue().goals2;
			// TODO update points
			points++;
		}
		return "+OK Du hast bis jetzt " + points + " Punkte im Tippspiel erreicht.";
	}

	private String signOut(NetInfo info) {
		activeUsers.remove(info);
		return "+OK Du wirst abgemeldet.";
	}

	private final class NetInfo {

		final String ip;
		final int port;

		NetInfo(String ip, int port) {
			this.ip = ip;
			this.port = port;
		}

		@Override
		public int hashCode() {
			return ip.hashCode() + Integer.hashCode(port);;
		}

	}
	
}
