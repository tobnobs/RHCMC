package outworld;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

import serialized.Data;

/**
 * Queue implementation in order to send data to client. All return servers
 * queue their data to this class and then send it out via ObjectOutputStream.
 * 
 * @author Tim Ryan (txr398)
 *
 */
public class OutputQueue implements Runnable {

	ArrayBlockingQueue<Data> queue = new ArrayBlockingQueue<Data>(100);

	/**
	 * Run method constantly running. As we are using an Array Blocking Queue it
	 * will wait untill there is actually some data to send to clients.
	 */
	@Override
	public void run() {
		while (true) {
			System.out.println("running");
			try {
				System.out.println("performing");
				Data data = queue.take();
				for (int i = 0; i < WorldServer.playerArray.size(); i++) {
					Socket temp_socket = WorldServer.connectionArray.get(i);
					AppendingObjectOutputStream out;
					out = new AppendingObjectOutputStream(
							temp_socket.getOutputStream());

					out.writeStreamHeader();
					out.writeObject(data);
					out.flush();

					System.out.println("Data Sent to  " + temp_socket);
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method return servers use in order to add Data to the queue.
	 * @param data Data object.
	 */
	public void add(Data data) {

		try {
			queue.put(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
