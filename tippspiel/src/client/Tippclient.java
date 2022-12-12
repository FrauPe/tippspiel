package client;

import javax.swing.JTextArea;
import abiturklassen.netzklassen.Client;

public class Tippclient extends Client {

	private JTextArea textbereich;

	public Tippclient(String serverIP, JTextArea pTextbereich){
		super(serverIP, 2000);
		textbereich = pTextbereich;
	}

	@Override
	public void processMessage(String pMessage){
		textbereich.append('\n' + pMessage);
	}

}
