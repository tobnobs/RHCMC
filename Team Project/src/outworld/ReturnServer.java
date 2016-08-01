package outworld;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import serialized.Data;
import GameState.Command;
import GameState.Player;

/**
 * Ran for each new connection to the server. Class aims to recieve input from
 * the connection it is handling and broadcast that input to all connections.
 * 
 * @author Tim Ryan (txr398), Liam, Toby, Zhuo
 *
 */
public class ReturnServer implements Runnable {

	private Socket socket;
	private ObjectOutputStream out;
	private DataInputStream in;
	private ServerOperations op;
	private OutputQueue queue;

	public ReturnServer(Socket x, ServerOperations op, OutputQueue queue) {

		this.socket = x;
		this.op = op;
		this.queue = queue;
	}

	/**
	 * Checks whether the connection is still functioning. If not the connection
	 * is removed from the array.
	 */
	public void checkConnection() {

		if (!socket.isConnected()) {

			for (int i = 0; i < WorldServer.connectionArray.size(); i++) {

				if (WorldServer.connectionArray.get(i) == socket) {
					WorldServer.connectionArray.remove(i);
					WorldServer.playerArray.remove(i);
					System.out.println("Player Removed");
				}

			}
		}
	}

	/**
	 * Main method to recieve requests from the client in the form of bytes, a
	 * switch case is implemented based on what is received makes changes to
	 * world server data. All byte arrays have a single byte appended to the
	 * front which is a char. This char discriminates as to which case the array
	 * will fall under.
	 */
	@Override
	public void run() {

		try {
			try {

				out = new ObjectOutputStream(socket.getOutputStream());
				out.flush();
				// in = new ObjectInputStream(socket.getInputStream());
				in = new DataInputStream(socket.getInputStream());

				while (true) {
					checkConnection();
					int length = in.readInt();
					byte[] byteInput = new byte[length];
					in.read(byteInput);

					String caseMessage = new String(Arrays.copyOfRange(
							byteInput, 0, 1));
					System.out.println("caseMesage: " + caseMessage);
					switch (caseMessage) {
					// Down
					case "1":
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								int y = WorldServer.playerArray.get(i).getY() + 4;
								// System.out.println(y);
								WorldServer.playerArray.get(i).setY(y);
								break;
							}
						}
						break;
					// Up
					case "2":
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								WorldServer.playerArray.get(i)
										.setY(WorldServer.playerArray.get(i)
												.getY() - 4);
								break;

							}
						}
						break;
					// Left
					case "3":
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								WorldServer.playerArray.get(i)
										.setX(WorldServer.playerArray.get(i)
												.getX() - 4);
								break;

							}
						}
						break;
					// Right
					case "4":
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								WorldServer.playerArray.get(i)
										.setX(WorldServer.playerArray.get(i)
												.getX() + 4);
								break;

							}
						}
						break;
					// toggles THE COMBAT.
					case "5":
						System.out.println("case 5 triggered");
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								WorldServer.playerArray.get(i).toggleCombat();
								System.out.println("Player "
										+ i
										+ "combat ready set to "
										+ WorldServer.playerArray.get(i)
												.isInCombat());
								break;
							}

						}
						break;

					case "6": {
						System.out.println("case 6 triggered");
						String message = new String(Arrays.copyOfRange(
								byteInput, 1, length));
						WorldServer.data.getChatLog().add(message);
						break;
					}

					case "7": {
						System.out.println("case 7 triggered");
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								WorldServer.playerArray.get(i).reset();
								break;
							}
						}
						break;
					}
					case "8": {
						System.out.println("map to cave");
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								WorldServer.playerArray.get(i).setSwitchMap(
										true);
								WorldServer.playerArray.get(i).setInCave(true);
								WorldServer.playerArray.get(i).setX(487);
								WorldServer.playerArray.get(i).setY(410);
								WorldServer.playerArray.get(i).setSwitchMap(
										false);
								break;

							}
						}
						break;
					}
					case "9": {
						System.out.println("map to cave");
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								WorldServer.playerArray.get(i).setSwitchMap(
										true);
								WorldServer.playerArray.get(i).setInCave(false);
								WorldServer.playerArray.get(i).setX(100);
								WorldServer.playerArray.get(i).setY(242);
								WorldServer.playerArray.get(i).setSwitchMap(
										false);
								break;

							}
						}
						break;
					}
					case "l":
						System.out.println("Case 10: make left");
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								WorldServer.playerArray.get(i).setFacingLeft(
										true);
								break;

							}
						}
						break;
					case "r":
						System.out.println("Case 11: make right");
						for (int i = 0; i < WorldServer.playerArray.size(); i++) {
							if (WorldServer.connectionArray.get(i) == (socket)) {
								WorldServer.playerArray.get(i).setFacingLeft(
										false);
								break;

							}
						}
						break;
					case "c":
						System.out.println("COMMAND");
						byte[] command = Arrays.copyOfRange(byteInput, 1,
								length);
						try {
							ByteArrayInputStream bis = new ByteArrayInputStream(
									command);
							ObjectInputStream ois = new ObjectInputStream(bis);
							Command commandObject = (Command) ois.readObject();
							WorldServer.commandArray.add(commandObject);
						} catch (IOException ex) {
							ex.printStackTrace();
						} catch (ClassNotFoundException ex) {
							ex.printStackTrace();
						}
						break;
					case "p":
						System.out.println("COMMAND");
						byte[] player = Arrays
								.copyOfRange(byteInput, 1, length);
						try {
							ByteArrayInputStream bis = new ByteArrayInputStream(
									player);
							ObjectInputStream ois = new ObjectInputStream(bis);
							Player playerObject = (Player) ois.readObject();
							for (int i = 0; i < WorldServer.playerArray.size(); i++) {
								if (WorldServer.playerArray.get(i)
										.getPlayerID() == playerObject
										.getPlayerID()) {
									WorldServer.playerArray
											.set(i, playerObject);
									break;
								}

							}
						} catch (IOException ex) {
							ex.printStackTrace();
						} catch (ClassNotFoundException ex) {
							ex.printStackTrace();
						}
						break;
					}
					if (caseMessage.equals("c") || caseMessage.equals("6")
							|| caseMessage.equals("p")) {
						System.out.println("case message c/6/p called");
					} else {
						System.out.println("case message " + caseMessage
								+ " called & data sent");
						Data data = WorldServer.data;
						queue.add(data);
					}

				}
			} finally {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
