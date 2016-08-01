package outworld;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import serialized.Data;
import GameState.Command;
import GameState.Player;

/**
 * Initialises all required serverside threads and handles all connections made
 * to the server as well as the majority of the internal game state. Clients
 * make requests throug their return threads which will alter the game state.
 * 
 * @param connectionArray
 *            Stores all connections that are currently with the server.
 * @param PORT
 *            The port that is connectable to.
 * @author Tim Ryan (txr398)
 *
 */
public class WorldServer {

	public static ArrayList<Socket> connectionArray = new ArrayList<Socket>();
	public static ArrayList<Player> playerArray = new ArrayList<Player>();
	public static ArrayList<Command> commandArray = new ArrayList<Command>();
	public static LinkedList<String> combatLog = new LinkedList<String>();
	public static LinkedList<String> chatLog = new LinkedList<String>();
	private final static int PORT = 4444;
	public static Data data;
	private static Random rand = new Random();
	public static boolean gameFinished = false;

	/**
	 * Creates a serversocket which listens for connections. If a connection is
	 * made a new returnServer is made in a new thread.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			OutputQueue queue = new OutputQueue();
			Thread q = new Thread(queue);
			q.start();
			ServerOperations operations = new ServerOperations(queue);
			Thread s = new Thread(operations);
			s.start();

			ServerSocket serverSocket = new ServerSocket(PORT);
			ServerSocket playerSocket = new ServerSocket(5555);
			System.out.println("Server sock created...waiting...");

			while (true) {
				Socket socket = serverSocket.accept();
				connectionArray.add(socket);
				Socket playerSock = playerSocket.accept();
				ObjectInputStream in = new ObjectInputStream(
						playerSock.getInputStream());
				Player player = (Player) in.readObject();
				in.close();
				player.setPlayerID(playerArray.size());

				if (playerArray.size() < 2)
					player.setTeam(0);
				else
					player.setTeam(1);

				playerArray.add(player);
				playerSock.close();
				data = new Data(WorldServer.playerArray, WorldServer.combatLog,
						WorldServer.chatLog);

				System.out.println("Connection from.. "
						+ socket.getLocalAddress().getHostName());

				for (int i = 0; i < connectionArray.size(); i++) {
					System.out.println(connectionArray.get(i).getInetAddress()
							.toString());
				}

				ReturnServer server = new ReturnServer(socket, operations,
						queue);
				Thread t = new Thread(server);
				t.start();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
