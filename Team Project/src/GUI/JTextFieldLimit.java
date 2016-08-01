package GUI;

import javax.swing.text.AttributeSet;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//reference...
/**
 * 
 * @author Mohamed Omar
 * 
 * Class to create a limit for the amount of characters inputable in a JTextField
 *
 */
public class JTextFieldLimit extends PlainDocument {
		  private int limit;
		  public JTextFieldLimit(int limit) {
		    super();
		    this.limit = limit;
		  }

		  JTextFieldLimit(int limit, boolean upper) {
		    super();
		    this.limit = limit;
		  }

		  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if (str == null)
		      return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
		}
