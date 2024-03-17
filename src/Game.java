import java.util.ArrayList;

public class Game {
    //private static ArrayList<String> actions = new ArrayList<{"Check/Call", "Raise", "Fold"}>();
    private int pot;
    private int turn;
    // turn 0 is first turn
    //turn 1 is after first three cards are revealed
    //turn 2 is after 4th card
    //turn 3 is after 5th card - final chance to bet
    private int numPlayers;
    private ArrayList<String> history;
    private ArrayList<Player> players;
    private Deck deck = new Deck();
    private ArrayList<Card> river;

    private int dealerInd = 0;

    public Game(ArrayList<Player> players){
        this.players = players;
        this.numPlayers = this.players.size(); 
    }



    public void processRound(){
        int costToCall = pot;
        deck.resetDeck();
        ArrayList<Player> inGamePLayers = players;
        ArrayList<ArrayList<Card>> playerCards = deck.drawHandsForPlayers(numPlayers);
        for(int i = dealerInd + 1; i < numPlayers + dealerInd + 1; i++){
            players.get(i % numPlayers).setCards(playerCards.get(i % numPlayers));
        }
        river = deck.drawRiver();
        System.out.println(playerCards);
        System.out.println();
        System.out.println(river);
    }
}
