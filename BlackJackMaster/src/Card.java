/* This class is goind to represent each one of the Deck's cards */


public class Card {
	/* Constantes enteras que definen los palos y las cartas que no tienen valor
	numerico */
	
	public final static int 
	BLADES = 0,
	HEARTS = 1,
	DIAMONDS = 2,
	CLUBS = 3;
	
	public final static int 
	ACE = 1,
	JACK = 11,
	QUEEN = 12,
	KING = 13;
	
	/* The card's properties are value and suit. */
	private final int suit;
	private final int value;
	
	/* Construction Method */
	public Card(int val, int _suit){
		value = val;
		suit = _suit;
	}
	
	/* Getting value and suit as integer and as a String */
	public int getSuit() {
		return suit;
	}
	public int getValue() {
		return value;
	}
	
	public String getSuitString() {
		switch ( suit ) {
			case BLADES: return "Blades";
			case HEARTS: return "Hearts";
			case DIAMONDS: return "Diamonds";
			case CLUBS: return "Suits";
			default: return "??";
		}
	}
	
	public String getValorString() {
		switch ( value ) {
			case 1: return "Ace";
			case 2: return "2";
			case 3: return "3";
			case 4: return "4";
			case 5: return "5";
			case 6: return "6";
			case 7: return "7";
			case 8: return "8";
			case 9: return "9";
			case 10: return "10";
			case 11: return "J";
			case 12: return "Q";
			case 13: return "K";
			default: return "??";
		}
	}
	
	public String toString() {
	return getValorString() + " of " + getSuitString();
	}

}