import java.io.*;
/* Main class */

public class Blackjack02 {
	// Main method
	public static void main(String[] args) {
		int[] results; //contains results of all players, pos 0 is for dealer
		int numberofPlayers,i;
		Hand playerHand[]; //an array of hands of all players
		boolean first=true;
		boolean lost[]; //an array of hands of all players that have lost

		// Game presentation
		TextIO.putln();
		TextIO.putln("Welcome to the game of Blackjack.");
		TextIO.putln();
		TextIO.putln(" ARTIFICIAL INTELLIGENCE PROJECT");
		TextIO.putln();
		TextIO.putln("Aimee Arcila");
		TextIO.putln("Stephanie De la Vega");
		TextIO.putln("Rub�n Oma�a");
		TextIO.putln();

		//Getting the number of players (always one)
		TextIO.put("Number of players:");
		numberofPlayers = TextIO.getlnInt();


		// Initializing
		playerHand=new Hand[numberofPlayers];
		results=new int[numberofPlayers+1];
		lost=new boolean[numberofPlayers];
		for(i=0;i<numberofPlayers;i++){
			lost[i]=false;
		}

		// Starting the loop of the game
		while (true) {
			for(int j=0;j<numberofPlayers;j++){
				// Create the hand
				playerHand[j]= new Hand();
					TextIO.putln();
					TextIO.putln("PLAYER NUMBER " + (j+1) );
				if(!lost[j]){
					TextIO.putln("Please type 0 to leave or 1 to play " +  '.');
					int quit = TextIO.getlnInt();
					//Make sure the value is 0 or 1
					while (quit <0 || quit >1){
						TextIO.putln("Please type just 0 or 1 (0 to leave or 1 to play)");
						quit = TextIO.getlnInt();
					}
					if (quit == 0){
						TextIO.putln();
						TextIO.putln("BYE player "+(j+1));
						lost[j]=true;
					}

				}
			 }

			// If there's at least one player left, we play
			if(!queda_alguno(lost,numberofPlayers))
				results = play(numberofPlayers,playerHand,lost);
			else{
				TextIO.putln();
				TextIO.putln("No hay mas jugadores");
				TextIO.putln("Byeee");
				System.exit(-1);
			}

		}
	}



	// PLAY method. It controls the game
	public static int[] play(int players,Hand[] playerhand,boolean[] lost) {
		Deck deck;
		Hand dealerHand;
		boolean end=false;
		int i,j;

		/*In "results" we store what each player has done
		 *  0 is the initial situation
		 *  -1 if is  exceeds 21
		 *  1 if wins
		 *  2 means BlackJack
		 */

		int results[];
		results = new int[players+1];

		//we initialize it in 0
		for (int m=0; m<=players; m++){
			results[m]=0;
		}

		deck = new Deck();


		TextIO.putln();
		TextIO.putln("NEW GAME ");
		TextIO.putln();
		TextIO.putln();
		TextIO.putln("--------- DEALER ---------");
		// Dealer got his cards
		TextIO.putln("Dealer hits... (Face up card)");
		dealerHand = new Hand();
		deck.remove(dealerHand.dealerHit());
		TextIO.putln();
		TextIO.putln("Dealer hits again (Face down card");
		//deck.remove(dealerHand.dealerHit()); We can't see the 2nd Dealer's Card
		TextIO.putln();
		TextIO.putln();

		// Players getting their cards
		for(i=0;i<players;i++){
			TextIO.putln();
			TextIO.putln("--------- PLAYER "+(i+1)+" ---------");

			deck.remove(playerhand[i].hit()); //Get the info of the card and remove it from mirror deck
			deck.remove(playerhand[i].hit());

			// Checking BlackJack
			if ((playerhand[i].getBlackjackValor() == 21)&&(!lost[i])) {
				//TextIO.putln("Dealer has " + dealerHand.getCard(0) + " y " + dealerHand.getCard(1) + ".");
				TextIO.putln("Player "+(i+1)+" has " + playerhand[i].getCard(0) + " and " + playerhand[i].getCard(1) + ".");
				TextIO.putln();
				TextIO.putln("Player "+(i+1)+" made Blackjack and wins");
				results[i+1]=2;
				end=true;
			}
		}


		// Lo movimos hasta que la Banca muestre la otra carta
//		if (dealerHand.getBlackjackValor() == 21) {
//			TextIO.putln("The dealer has " + dealerHand.getCard(0) + " y " + dealerHand.getCard(1) + ".");
//			TextIO.putln();
//			TextIO.putln("The dealer made Blackjack and wins.");
//			results[0]=2;
//			end=true;
//		}

		if (end) return results;

		// If any player has BJ, the game starts
		// Starts the iteration for each player
		for(j=0;j<players;j++){
			// We got 2 cards for the players and the dealer
			TextIO.putln("--------- PLAYER "+(j+1)+" ---------");
			TextIO.putln();
			end=false;
			while (!lost[j]) {
				TextIO.putln();
				TextIO.putln("Player "+(j+1)+".These are your cards:");
				for ( i = 0; i < playerhand[j].count(); i++ )
					TextIO.putln(" " + playerhand[j].getCard(i));
				TextIO.putln("And they made a total of " +
						playerhand[j].getBlackjackValor()+" points.");
				TextIO.putln();

				TextIO.putln("The dealer shows " + dealerHand.getCard(0));
				TextIO.putln();


				//AQUI VA LA SUGERENCIA?



                                calculateOdds(playerhand[j],dealerHand);

				// Hit or Stand?
				TextIO.put("Hit (H) or Stand (S)? ");
				char accion;
				do { //validating H or S
					accion = Character.toUpperCase( TextIO.getlnChar() );
					if (accion != 'H'&& accion != 'S')
					TextIO.put("Please answer H or S: ");
				} while (accion != 'H'&& accion != 'S');

				// If stands, we break the loop
				if ( accion == 'S') {
					TextIO.putln();
					TextIO.putln("Player "+(j+1)+" Stands.");
					TextIO.putln();
					break;
				}
				// If not, we ask for a new card
				else {
					TextIO.putln();
					TextIO.putln("You hit.");
					TextIO.putln();
					Card newCard = playerhand[j].hit();
					deck.remove(newCard);

					TextIO.putln("Your card is " + newCard);
					TextIO.putln("And you have " +
					playerhand[j].getBlackjackValor()+" points");

					//If exceeds 21, lost and result is -1
					if (playerhand[j].getBlackjackValor() > 21) {
						TextIO.putln();
						TextIO.putln("Player "+(j+1)+" is over 21. Has lost");
						TextIO.putln();
						results[j+1]=-1;
						end=true;
					}
				}
					if (end) break;
			}
		}


		// Now is dealers turn
		TextIO.putln();
		TextIO.putln("--------- DEALER ---------");
		TextIO.putln("Dealer shows the other card");
		deck.remove(dealerHand.dealerHit());
		TextIO.putln("Dealer Cards are ");
		TextIO.putln(" " + dealerHand.getCard(0));
		TextIO.putln(" " + dealerHand.getCard(1));
		TextIO.putln("And has " +
				dealerHand.getBlackjackValor()+" points");

		if (dealerHand.getBlackjackValor() == 21) {
			TextIO.putln();
			TextIO.putln("The dealer made Blackjack and wins.");
			results[0]=2;
			//end=true;
		}

		while(dealerHand.getBlackjackValor()<=16){
			TextIO.putln("Dealer steals a new card");
			deck.remove(dealerHand.dealerHit());
			TextIO.putln("Dealer Cards are ");
			for(int k =0; k<dealerHand.count(); k++){
				TextIO.putln(" " + dealerHand.getCard(0));
			}
			TextIO.putln("And has " +
					dealerHand.getBlackjackValor()+" points");
		}

		if (dealerHand.getBlackjackValor() > 16 && dealerHand.getBlackjackValor()!= 21) {
			TextIO.putln();
			TextIO.putln("Dealer can't steal more cards");
		}


		if (dealerHand.getBlackjackValor() > 21) {
			results[0]=-1;
		}


		// Repeat this until dealer is over 21 or decides to stand
		TextIO.putln("The dealer total is " +
		dealerHand.getBlackjackValor()+" points");


		// Comparing Scores
		for(i=0;i<players;i++){
			if((results[i+1]==-1)||(lost[i])) continue;
			//Si el Dealer pierde de todos modos el jugador que est� sobre 21 pierde.
//			if(results[0]==-1){
//				results[i+1]=1;
//				continue;
//			}
			if(playerhand[i].getBlackjackValor()>dealerHand.getBlackjackValor()
					&& playerhand[i].getBlackjackValor()<=21)
				results[i+1]=1;
			else results[i+1]=-1;
		}
		TextIO.putln();
		TextIO.putln();

		// Print the results
		for(i=1;i<=players;i++){
			if(!lost[i-1]){
				if(results[i]>results[0]) TextIO.putln("Player "+i+" wins.");
			else{
				if(results[0]<0){
					TextIO.putln("Nobody wins.");
				}else{
					TextIO.putln("Dealers wins.");
				}
			}
			}
		}
		return results;
	}

	// Checking if all players has left the game
	public static boolean queda_alguno(boolean wontPlay[],int numero){
		boolean everyone_out=true;
		for(int p=0;(p<numero)&&everyone_out;p++){
			if(!wontPlay[p]){
				everyone_out=false;
			break;
			}
		}
		return everyone_out;
	}



        public static void calculateOdds(Hand playerhand , Hand dealerhand){

            String card1 = playerhand.getCard(0).getValorString();
            String card2 = playerhand.getCard(1).getValorString();
            int playerCardsValue = playerhand.getBlackjackValor();

            String upCard = dealerhand.getCard(0).getValorString();
            int upCardValue = dealerhand.getCard(0).getValue();
            
            if(upCardValue >10)
                upCardValue =10;

            //Basic Strategy
            if(playerhand.count() == 2){

                 //scenario with ace as one of the cards (Soft)
            if(card1.equals("Ace") | card2.equals("Ace")){

		//One Ace and a 2
		//Start
                if( (card1.equals("2") | card2.equals("2")) && (upCardValue >=5 && upCardValue <=6)){
                    TextIO.putln("You should Hit...");
                }
                else if ((card1.equals("2") | card2.equals("2")) && (upCardValue >=2 && upCardValue <=4)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("2") | card2.equals("2")) && (upCardValue >=7 && upCardValue <=10)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("2") | card2.equals("2")) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
		//End

		//One Ace and a 3
		//Start
                else if( (card1.equals("3") | card2.equals("3")) && (upCardValue >=5 && upCardValue <=6)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("3") | card2.equals("3")) && (upCardValue >=2 && upCardValue <=4)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("3") | card2.equals("3")) && (upCardValue >=7 && upCardValue <=10)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("3") | card2.equals("3")) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
		//End

		//One Ace and a 4
		//Start
                else if ((card1.equals("4") | card2.equals("4")) && (upCardValue >=4 && upCardValue <=6)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("4") | card2.equals("4")) && (upCardValue >=2 && upCardValue <=3)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("4") | card2.equals("4")) && (upCardValue >=7 && upCardValue <=10)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("4") | card2.equals("4")) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
		//End

		//One Ace and a 5
		//Start
                else if ((card1.equals("5") | card2.equals("5")) && (upCardValue >=4 && upCardValue <=6)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("5") | card2.equals("5")) && (upCardValue >=2 && upCardValue <=3)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("5") | card2.equals("5")) && (upCardValue >=7 && upCardValue <=10)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("5") | card2.equals("5")) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
		//End

		//One Ace and a 6
		//Start
                else if ((card1.equals("6") | card2.equals("6")) && (upCardValue >=3 && upCardValue <=6)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("6") | card2.equals("6")) && (upCardValue ==2 )){
                    TextIO.putln("You should Hit...");
                }
                else if ((card1.equals("6") | card2.equals("6")) && (upCardValue >=7 && upCardValue <=10)){
                    TextIO.putln("You should Hit...");
                }
		else if ((card1.equals("6") | card2.equals("6")) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
		//End

		//One Ace and a 7
		//Start
                else if ((card1.equals("7") | card2.equals("7")) && (upCardValue >=3 && upCardValue <=6)){
                    TextIO.putln("You should Hit...");
                }
                    else if ((card1.equals("7") | card2.equals("7")) && (upCardValue ==2)){
                    TextIO.putln("You should Stand...");
                }
                    else if ((card1.equals("7") | card2.equals("7")) && (upCardValue >=7 && upCardValue <=8)){
                    TextIO.putln("You should Stand...");
                }
		else if ((card1.equals("7") | card2.equals("7")) && (upCardValue >=9 && upCardValue <=10)){
                    TextIO.putln("You should Hit...");
                }
                    else if ((card1.equals("7") | card2.equals("7")) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
		//End

		//One Ace and a 8
		//Start
		else if ((card1.equals("8") | card2.equals("8")) && (upCardValue >=2 && upCardValue <=10)){
                    TextIO.putln("You should Stand...");
                }
		else if ((card1.equals("8") | card2.equals("8")) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
		//End

		//One Ace and a 9
		//Start
		else if ((card1.equals("9") | card2.equals("9")) && (upCardValue >=2 && upCardValue <=10)){
                    TextIO.putln("You should Stand...");
                }
		else if ((card1.equals("9") | card2.equals("9")) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Stand...");
                }
		//End
                
            }else{
             //scenario with no ace (Hard)
		
                //Player has 5-11
		//Start
                if((playerCardsValue >= 5 && playerCardsValue <= 11) && (upCardValue>= 2 && upCardValue <= 10)){
                    TextIO.putln("You should Hit...");
                }
                else if((playerCardsValue >= 5 && playerCardsValue <= 11) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
		//End
                
                //Player has 12
                //Start
                else if((playerCardsValue == 12) && (upCardValue>= 2 && upCardValue <= 3)){
                    TextIO.putln("You should Hit...");
                }
                else if((playerCardsValue == 12) && (upCardValue>= 4 && upCardValue <= 6)){
                    TextIO.putln("You should Stand...");
                }
                else if((playerCardsValue == 12) && (upCardValue>= 7 && upCardValue <= 10)){
                    TextIO.putln("You should Hit...");
                }
                else if((playerCardsValue == 12) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
                //End
                
                //Player has 13-16
                //Start
                else if((playerCardsValue >= 13 && playerCardsValue <= 16) && (upCardValue>= 2 && upCardValue <= 6)){
                    TextIO.putln("You should Stand...");
                }
                else if((playerCardsValue >= 13 && playerCardsValue <= 16) && (upCardValue>= 7 && upCardValue <= 10)){
                    TextIO.putln("You should Hit...");
                }
                else if((playerCardsValue >= 13 && playerCardsValue <= 16) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Hit...");
                }
                //End
                
                //Player has 17-20
                //Start
                else if((playerCardsValue >= 17 && playerCardsValue <= 20) && (upCardValue>= 2 && upCardValue <= 10)){
                    TextIO.putln("You should Stand...");
                }
                else if((playerCardsValue >= 17 && playerCardsValue <= 20) && (upCard.equals("Ace"))){
                    TextIO.putln("You should Stand...");
                }
                //End
                
            }

            //Probability Calculation
            }else{
                /**
                 * Adquired from: http://probability.infarom.ro/blackjack.html
                 * Formula used:
                 * P = (4m - nx)/ (52m -nv) if x != 10
                 * P = (16m -nx)/ (52m -nv) if x == 10
                 * Where m is the number of decks
                 * and x is the card we are looking
                 * Data:
                 *      nx is the number of cards with the value x
                 *      nv is the total number of cards showing
                 * 
                 * Description:
                 *      We use the acumulated formula for the n cases
                 *      the player has left until Bust.
                **/
                
                int actualValue = playerhand.getBlackjackValor();
                int requiredValue = 21 - actualValue;
                int nv = playerhand.count() + 1;
                
                double acumulatedProbability =  0.0F;
                double prob = 0;
                
                for(int i = 1; i <= requiredValue; i++){
                    prob = 0.0;
                    int nx = getNumberOfCards(playerhand,upCardValue,i);
                    if(requiredValue == 10){
                        float p1, p2;
                        p1 = (16-nx);
                        p2 = (52-nv);
                        prob = p1 / p2;
                    }else{
                        float p3, p4;
                        p3 = (4-nx);
                        p4 = (52-nv);
                        prob = p3/p4;
                    }
                    prob = prob*100;
                    acumulatedProbability += prob;
                }
                
                TextIO.put("Your probability to not Bust is... ");
                TextIO.put((int)acumulatedProbability + "%");
                TextIO.putln("");
                TextIO.put("Your probability to BlackJack is... ");
                TextIO.put((int)prob + "%");
                TextIO.putln("");
                if(acumulatedProbability >=40){
                    TextIO.putln("We sugest you Hit");                    
                }else{
                    TextIO.putln("We sugest you Stand");
                }


            }

        }
        /**
         * 
         * This function finds the number of cards of target type 
         */
        public static  int getNumberOfCards(Hand playerhand, int upCardValue, int target){
            int number = 0;
            
            int count = playerhand.count();
            
            for(int i = 0; i < count; i ++){
                int cardValue = playerhand.getCard(i).getValue();
                if(cardValue > 10 )
                    cardValue = 10;
                
                if(cardValue == target)
                    number++;
                
            }
            
            if(upCardValue == target)
                number++;
            
            return number;
        }
}
