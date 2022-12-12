package net;

import java.io.IOException;
import java.net.Socket;

public abstract class Client {

	private Connection connection;
	private Thread thread = new Thread(() -> {
		try {
			while(true)
				processMessage(connection.receive());
		} catch(IOException e) {} // exit
	});


	public Client(String ip, int port) {
		try {
			connection = new Connection(new Socket(ip, port));
			thread.start();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return connection.isConnected();
	}

	public void send(String message) {
		connection.send(message);
	}

	public void close() {
		thread.interrupt();
		connection.close();
	}

	public abstract void processMessage(String message);

}
