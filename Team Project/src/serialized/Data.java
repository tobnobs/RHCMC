package serialized;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import GameState.Player;

/**
 * Custom serializable class that is sent to clients. Consisting of the updated
 * players, chat log and combat log.
 * 
 * @author Tim Ryan (txr398)
 *
 */
public class Data implements Serializable {

	private static final long serialVersionUID = 1L;
	public ArrayList<Player> players = new ArrayList<Player>();
	public LinkedList<String> combatLog = new LinkedList<String>();
	public LinkedList<String> chatLog = new LinkedList<String>();

	/**
	 * The data sent over the network
	 * @param players The list of players
	 * @param combatLog The log of commands played
	 * @param chatLog The chat log
	 */
	public Data(ArrayList<Player> players, LinkedList<String> combatLog,
			LinkedList<String> chatLog) {
		this.players = players;
		this.combatLog = combatLog;
		this.chatLog = chatLog;
	}

	/**
	 * Returns the list of players
	 * @return
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Sets the list of players
	 * @param players
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * Returns the combat log
	 * @return
	 */
	public LinkedList<String> getCombatLog() {
		return combatLog;
	}

	/**
	 * Sets the combat log
	 * @param combatLog
	 */
	public void setCombatLog(LinkedList<String> combatLog) {
		this.combatLog = combatLog;
	}

	/**
	 * Returns the chat log
	 * @return
	 */
	public LinkedList<String> getChatLog() {
		return chatLog;
	}

	/**
	 * Sets the chat log
	 * @param chatLog
	 */
	public void setChatLog(LinkedList<String> chatLog) {
		this.chatLog = chatLog;
	}

}
