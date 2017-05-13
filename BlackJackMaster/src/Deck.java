/* This class will represent our 52 cards Deck*/

public class Deck {
	// The deck is an array of Cards
	private static Card[] deck;
	
	//array espejo de la baraja que marcara si ya salieron o no.
	public Boolean[] deckBool;
	
		
	// Numero de cartas robadas
	private int stolen;
	
	// Construction Method. Recorremos todos los valores posibles y todos los palos posibles
	//Iniciaremos la matriz booleana en espejo, todos en true porque estan en la baraja. 
	public Deck() {
		deck = new Card[52];
		deckBool = new Boolean[52];
		int creaded = 0;
		for ( int suit = 0; suit <= 3; suit++ ) {
			for ( int value = 1; value <= 13; value++ ) {
				deck[creaded] = new Card(value,suit);
				deckBool[creaded] = true;
				creaded++;
			}
		}
		stolen = 0;
	}
	
	
	// Numero de cartas que nos quedan en la baraja
	public int left() {
		return 52 - stolen;
	}
	
	//Introduce la carta que salio y  la borra del boolDeck
	
	public void remove (Card r){
		int pos;
		int auxval,auxsuit;
		pos = r.getSuit()*13 + r.getValue();
	
		//Verifying the card isn't duplicated
		while(deckBool[pos]==false){
			TextIO.putln("That card was already out, please retype");
			TextIO.putln("Which is the Card Value? (Special Cards: "
					+ "ACE = 1,"
					+ "JACK = 11,"
					+ "QUEEN = 12,"
					+ "KING = 13 )");
			auxval = TextIO.getlnInt();
			
			TextIO.putln("Which is the Card Suit? ("
					+ "BLADES = 0, "
					+ "HEARTS = 1, "
					+ "DIAMONDS = 2, "
					+ "CLUBS = 3 )");
			auxsuit = TextIO.getlnInt();
			
			pos = auxsuit*13 + auxval;
		}
		deckBool[pos] = false; //we "remove it from desk
		stolen ++;
	}
}
