package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public abstract class Server {

	private ServerSocket server;
	private ArrayList<Connection> connections = new ArrayList<>();
	private Thread thread = new Thread(() -> {
		while(true) {
			try {
				Socket s = server.accept();
				s.setSoTimeout(1);
				Connection c = new Connection(s);
				connections.add(c);
				processNewConnection(c.getIP(), c.getPort());
			} catch(SocketTimeoutException e) { // nothing to accept
			} catch(IOException e) {
				e.printStackTrace();
			}
			ArrayList<Connection> copy;
			synchronized(connections) { copy = new ArrayList<>(connections); }
			for(Connection c : copy) {
				try {
					processMessage(c.getIP(), c.getPort(), c.receive());
				} catch(IOException e) {
					System.out.println("Verbindung zu " + c + " ist unterbrochen");
					closeConnection(c.getIP(), c.getPort());
				}
			}
		}
	});

	public Server(int port) {
		try {
			server = new ServerSocket(port);
			server.setSoTimeout(1);
			thread.start();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isOpen() {
		return !server.isClosed();
	}

	private int getIndexOf(String ip, int port) {
		for(int i = 0; i < connections.size(); ++i) {
			Connection c = connections.get(i);
			if(c.getPort() == port && c.getIP().equals(ip))
				return i;
		}
		return -1;
	}

	public boolean isConnectedTo(String ip, int port) {
		synchronized(connections) {
			int i = getIndexOf(ip, port);
			return i != -1 && connections.get(i).isConnected();
		}
	}

	public void send(String ip, int port, String message) {
		Connection c;
		synchronized(connections) {
			int i = getIndexOf(ip, port);
			if(i == -1)
				return;
			c = connections.get(i);
		}
		c.send(message);
	}

	public void sendToAll(String message) {
		synchronized(connections) {
			for(Connection c : connections)
				c.send(message);
		}
	}

	public void closeConnection(String ip, int port) {
		synchronized(connections) {
			int i = getIndexOf(ip, port);
			if(i == -1)
				return;
			processClosingConnection(ip, port);
			connections.remove(i).close();
		}
	}

	public void close() {
		thread.interrupt();
		try {
			server.close();
		} catch(IOException e) {}
		for(Connection c : connections)
			c.close();
	}

	public abstract void processNewConnection(String ip, int port);
	public abstract void processMessage(String ip, int port, String message);
	public abstract void processClosingConnection(String ip, int port);

}
