package GameState;

import java.util.Comparator;

/**
 * @author Zhuo
 * Class to compare commands based on their speed
 */
public class CommandComparator implements Comparator<Command> {
	/**
	 * Compares two commands returning -1 if the first is greater, 1 if the
	 * second is greater, and 0 if they are equal
	 * 
	 * @return The value for which command has greater speed
	 */
	public int compare(Command com1, Command com2) {
		if (com1.getSpeed() > com2.getSpeed()) {
			return -1;
		} else if (com1.getSpeed() < com2.getSpeed()) {
			return 1;
		}
		return 0;
	}

}
