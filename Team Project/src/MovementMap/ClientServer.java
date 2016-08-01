package MovementMap;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import serialized.Data;
import GameState.Command;
import GameState.Player;

/**
 * Handles all interaction between the server except the initial connection
 * which is handled in the main class.
 * 
 * @author Tim Ryan (txr398)
 *
 */
public class ClientServer implements Runnable {

	Socket socket;
	private DataOutputStream out;
	ObjectInputStream in;
	TheMap map;

	/**
	 * Connects the map to the listening socket
	 * 
	 * @param s
	 *            The socket
	 * @param map
	 *            The map to send
	 */
	public ClientServer(Socket s, TheMap map) {

		this.socket = s;
		this.map = map;
	}

	/**
	 * Initialites streams and runs the infinite checkStream method.
	 */
	public void run() {

		try {
			try {
				out = new DataOutputStream(socket.getOutputStream());
				out.flush();
				in = new ObjectInputStream(socket.getInputStream());
				checkStream();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JOptionPane.showMessageDialog(null, "Closing sockets");
				socket.close();
				out.close();
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Disconnect cleanly from server on a disconnect.
	 */
	public void disconnect() {
		try {
			out.flush();
			socket.close();
			JOptionPane.showMessageDialog(null, "You have been disconnected");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * infinite loop to recieve player object data.
	 */
	public void checkStream() {

		// TheMap.setConnected(true);

		while (true) {
			recieve();
		}

	}

	/**
	 * Reads in Data objects and uses it's fields to change clients view of
	 * players and logs.
	 */
	public void recieve() {

		try {
			Data data = (Data) in.readObject();
			System.out.println("Data received");
			for (Player player : data.players) {
				System.out.println(player.getTeam());
			}
			map.others = data.players;
			map.csp2.combatLog = data.combatLog;
			map.csp2.chatLog = data.chatLog;
			if (data.players.get(0).playersUpdated) {
				map.playersUpdated = true;
				System.out.println("map playersUpdated set to "
						+ map.playersUpdated);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method called after every keypress in the main class. Player data is sent
	 * to the
	 * 
	 * @param player
	 * @throws IOException
	 */
	public void send(String s) {

		byte[] sendable = s.getBytes();
		try {
			out.writeInt(sendable.length);
			out.write(sendable);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to send messages during comabt. Messages will be appended to the
	 * chat window.
	 * 
	 * @param s
	 *            The message to be sent.
	 */
	public void sendMessage(String s) {

		String six = "6";
		String name = map.main.getName() + ":";

		String message = six.concat(name).concat(s).concat("\n");

		byte[] sendable = message.getBytes();
		try {
			out.writeInt(sendable.length);
			out.write(sendable);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Converts a Command object into a byte array and sends it over the socket.
	 * 
	 * @param c
	 *            The Command Object
	 */
	public void sendCommandBYTES(Command c) {

		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(c);
			oos.flush();
			oos.close();
			bos.close();
			bytes = bos.toByteArray();

			String s = "c";
			byte[] first = s.getBytes();

			byte[] combined = new byte[first.length + bytes.length];
			for (int i = 0; i < combined.length; i++) {
				if (i < first.length)
					combined[i] = first[i];
				else
					combined[i] = bytes[i - first.length];
			}

			String commandString = new String(combined.toString());
			out.writeInt(combined.length);
			out.write(combined);
			out.flush();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Initial sending of created player on connection.
	 */
	public void sendPlayer() {

		try {

			final int port = 5555;
			final String address = map.getAddress();
			Socket socket = new Socket(address, port);
			System.out.println("Connected to " + address
					+ " FOR SENDING PLAYER");

			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());
			out.flush();

			out.writeObject(map.getMain());
			out.flush();

			socket.close();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Converts a Player object into a byte array and sends it over the socket.
	 * 
	 * @param p
	 *            The Player Object
	 */
	public void sendPlayerBYTES(Player p) {

		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(p);
			oos.flush();
			oos.close();
			bos.close();
			bytes = bos.toByteArray();

			String s = "p";
			byte[] first = s.getBytes();

			byte[] combined = new byte[first.length + bytes.length];
			for (int i = 0; i < combined.length; i++) {
				if (i < first.length)
					combined[i] = first[i];
				else
					combined[i] = bytes[i - first.length];
			}

			String commandString = new String(combined.toString());
			out.writeInt(combined.length);
			out.write(combined);
			out.flush();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
