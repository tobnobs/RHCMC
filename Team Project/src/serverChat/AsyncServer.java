package serverChat;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Tim Ryan
 *
 */
public class AsyncServer {

	public static ArrayList<Socket> connectionArray = new ArrayList<Socket>();
	public static ArrayList<String> currentUsers = new ArrayList<String>();
	private final static int PORT = 4444;
	private final String ADDRESS = "127.0.0.1";

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("Server sock created...waiting...");

			while (true) {
				Socket socket = serverSocket.accept();
				connectionArray.add(socket);

				System.out.println("Connection from.. "
						+ socket.getLocalAddress().getHostName());

				updateUsers(socket);

				ReturnServer server = new ReturnServer(socket);
				Thread t = new Thread(server);
				t.start();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void updateUsers(Socket s) {

		try {
			Scanner in = new Scanner(s.getInputStream());
			String userName = in.nextLine();

			currentUsers.add(userName);

			for (int i = 0; i < connectionArray.size(); i++) {
				Socket temp_socket = connectionArray.get(i);
				PrintWriter out = new PrintWriter(temp_socket.getOutputStream());
				out.println("#?!" + currentUsers);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<Socket> getConnectionArray() {
		return connectionArray;
	}
}
