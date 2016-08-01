package outworld;

import java.net.Socket;

import GameState.Player;

/**
 * 
 * @author Tim Ryan, Liam Howe
 *
 */
public class ServerOperations implements Runnable {

	boolean threadStarted = false;
	public OutputQueue queue;

	public ServerOperations(OutputQueue queue) {
		this.queue = queue;
	}

	/*
	 * public boolean checkCollision(Socket socket) {
	 * 
	 * for (int i = 0; i < WorldServer.connectionArray.size(); i++) {
	 * 
	 * if (WorldServer.connectionArray.get(i) == socket) {
	 * 
	 * for (int k = 0; k < WorldServer.connectionArray.size(); k++) { if
	 * (WorldServer.connectionArray.get(i) != WorldServer.connectionArray
	 * .get(k)) { if ((Math.abs(WorldServer.playerArray.get(i).getX() -
	 * WorldServer.playerArray.get(k).getX())) < 15 &&
	 * (Math.abs(WorldServer.playerArray.get(i) .getY() -
	 * WorldServer.playerArray.get(k).getY()) < 15)) {
	 * 
	 * return true;
	 * 
	 * 
	 * } } }
	 * 
	 * }
	 * 
	 * } return false; }
	 */

	/**
	 * Constant checking of whether all four players are readied up in order to
	 * start a 2vs2 competitive multiplayer battle. Upon the condition being
	 * satisfied then a combat thread server is initialised with the four players.
	 */
	public void startBattle2vs2() {
		if (WorldServer.gameFinished) {
			WorldServer.gameFinished = false;
			threadStarted = false;
		}
		if (!threadStarted)
			System.out.print("");
		if (WorldServer.playerArray.size() == 4 && !threadStarted) {
			for (int i = 0; i < WorldServer.connectionArray.size(); i++)
				System.out.print("");
			if (WorldServer.playerArray.get(0).isInCombat()
					&& WorldServer.playerArray.get(1).isInCombat()
					&& WorldServer.playerArray.get(2).isInCombat()
					&& WorldServer.playerArray.get(3).isInCombat()
					&& !threadStarted) {
				System.out.println("Server Thread created");

				CombatThreadServer cts = new CombatThreadServer(
						WorldServer.playerArray.get(0),
						WorldServer.playerArray.get(1),
						WorldServer.playerArray.get(2),
						WorldServer.playerArray.get(3), queue);
				System.out.println("Server Thread executed");
				threadStarted = true;
				cts.execute();
			}
		}
	}

	@Override
	public void run() {
		while (true) {

			startBattle2vs2();
		}

	}

}
