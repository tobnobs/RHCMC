package serverChat;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 
 * @author Tim Ryan
 *
 */
public class ReturnServer implements Runnable {

	private static Socket socket;
	private Scanner in;
	private PrintWriter out;
	String message = "";

	public ReturnServer(Socket x) {

		ReturnServer.socket = x;
	}

	public static void checkConnection() {

		if (!socket.isConnected()) {

			for (int i = 0; i < AsyncServer.connectionArray.size(); i++) {

				if (AsyncServer.connectionArray.get(i) == socket)
					AsyncServer.connectionArray.remove(i);
			}

			for (int i = 0; i < AsyncServer.connectionArray.size(); i++) {
				try {
					Socket temp_socket = AsyncServer.connectionArray.get(i);
					PrintWriter out = new PrintWriter(
							temp_socket.getOutputStream());
					out.println(temp_socket.getLocalAddress().getHostName()
							+ " has disconnected.");
					out.flush();

					// Show server side.
					System.out.println(temp_socket.getLocalAddress()
							.getHostName() + " has disconnected.");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	@Override
	public void run() {

		try {
			try {
				in = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream());

				while (true) {

					checkConnection();

					if (!in.hasNext()) {
						return;
					}

					message = in.nextLine();

					System.out.println("Message recieved from client saying "
							+ message);

					for (int i = 0; i < AsyncServer.connectionArray.size(); i++) {

						Socket temp_socket = AsyncServer.connectionArray.get(i);
						PrintWriter out = new PrintWriter(
								temp_socket.getOutputStream());
						out.println(message);
						out.flush();

						System.out.println(message + " Sent to: "
								+ temp_socket.getLocalAddress().getHostName());

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
