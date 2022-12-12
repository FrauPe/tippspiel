package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

// nicht mit der Abi-Klasse zu tun
public final class Connection {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public Connection(Socket s) {
		try {
			socket = s;
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			socket.close();
		} catch(IOException e) {}
	}

	public void send(String message) {
		out.println(message);
	}

	public String receive() throws IOException {
		try {
			return in.readLine();
		} catch(SocketTimeoutException e) {
			return null;
		}
	}

	public boolean isConnected() {
		return socket.isConnected();
	}

	public String getIP() {
		return socket.getInetAddress().toString();
	}

	public int getPort() {
		return socket.getPort();
	}

	@Override
	public String toString() {
		return getIP() + ':' + getPort();
	}

}
