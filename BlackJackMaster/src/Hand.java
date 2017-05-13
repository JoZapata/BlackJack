/* This class will represent the cards that the player has */
import java.util.Vector;

public class Hand{
	private Vector hand;
	
	
	// Constructor
		public Hand() {
		hand = new Vector();
	}
	
	 //Restart the hand
	public void restart() {
		hand.removeAllElements();
	}
	
	// Hit a card
	public Card hit() {
		int suit, value;
		
		//We ask for the value of the Card just hit
		TextIO.putln("Which is your new Card Value? (Special Cards: "
				+ "ACE = 1,"
				+ "JACK = 11,"
				+ "QUEEN = 12,"
				+ "KING = 13 )");
		value = TextIO.getlnInt();
		
		//We ask for the suit of the Card just hit
		TextIO.putln("Which is your new Card Suit? ("
				+ "BLADES = 0, "
				+ "HEARTS = 1, "
				+ "DIAMONDS = 2, "
				+ "CLUBS = 3 )");
		suit = TextIO.getlnInt();

		Card c = new Card(value, suit);
		if (c != null)
			hand.addElement(c);
		return c;
	}
	
	public Card dealerHit() {
		int suit, value;
		
		//We ask for the value of the Card just hit
		TextIO.putln("Which is the Dealer's new Card Value? (Special Cards: "
				+ "ACE = 1,"
				+ "JACK = 11,"
				+ "QUEEN = 12,"
				+ "KING = 13 )");
		value = TextIO.getlnInt();
		
		//We ask for the suit of the Card just hit
		TextIO.putln("Which is the Dealer's new Card Suit? ("
				+ "BLADES = 0, "
				+ "HEARTS = 1, "
				+ "DIAMONDS = 2, "
				+ "CLUBS = 3 )");
		suit = TextIO.getlnInt();

		Card c = new Card(value, suit);
		if (c != null)
			hand.addElement(c);
		return c;
	}
	
	
	// Counts the cards in the hand
	public int count() {
		return hand.size();
	}
	
	
	// To get the Card in a determined position
	public Card getCard(int pos) {
		if (pos >= 0 && pos < hand.size())
			return (Card)hand.elementAt(pos);
		else
			return null;
	}
	
	// Calculates the points in the hand
	public int getBlackjackValor() {
		int val;
		boolean ace;
		int cards;
		val = 0;
		ace = false;
		cards = count();
		for ( int i = 0; i < cards; i++ ) {
			Card card;
			int cardVal;
			card = getCard(i);
			cardVal = card.getValue();
			if (cardVal > 10) {
				cardVal = 10;
			}
			if (cardVal == 1) {
				ace = true;
			}
			val = val + cardVal;
		}
		
		/* Ace has a value of 1 at the beginning, but it changes to 11 if then 
		 * the value we equals 21 or less*/
		if ( ace == true && val + 10 <= 21 )
		val = val + 10;
		return val;
	} 
}
