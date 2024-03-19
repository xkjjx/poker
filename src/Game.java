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
        this.pot = 10;
    }


    public void processRound(){
        int costToCheck = pot;
        int totalCash = 0;
        deck.resetDeck();
        ArrayList<Player> inGamePLayers = players;
        ArrayList<ArrayList<Card>> playerCards = deck.drawHandsForPlayers(numPlayers);
        for(int i = dealerInd + 1; i < numPlayers + dealerInd + 1; i++){
            players.get(i % numPlayers).setCards(playerCards.get(i % numPlayers));
            System.out.println(players.get(i % numPlayers).getName() + " has been dealt " + playerCards.get(i % numPlayers).get(0).toString(true) + " and " + playerCards.get(i % numPlayers).get(1).toString(true));
        }
        river = deck.drawRiver();
        System.out.println("The cost to stay in is currently " + costToCheck);
        for(turn = 0; turn < 5; turn++){
            if(inGamePLayers.size() == 1){
                System.out.println(inGamePLayers.get(0).getName() + " has won the pot of " + totalCash);
                inGamePLayers.get(0).addCoins(totalCash);
                break;
            }
            if(turn == 0){
                costToCheck = pot;
                for(int i = 0; i < inGamePLayers.size(); i++){
                    ArrayList<Integer> decisions = inGamePLayers.get(i).promptDecision();
                    if(decisions.get(0) == 0){
                        inGamePLayers.get(i).getCoins(costToCheck);
                        totalCash += costToCheck;
                    }
                    else if(decisions.get(0) == 1){
                        inGamePLayers.get(i).getCoins(decisions.get(1));
                        totalCash += decisions.get(1);
                        costToCheck = decisions.get(1);
                    }
                    else{
                        inGamePLayers.remove(i);
                        i--;
                    }
                }
                System.out.println(String.format("There is now %d money in the pot.", totalCash));
            }
            else if(turn == 1){
                costToCheck = 0;
                System.out.println("The first three cards are " + river.get(0).toString(true) + ", " + river.get(1).toString(true) + ", and " + river.get(2).toString(true));
                for(int i = 0; i < inGamePLayers.size(); i++){
                    ArrayList<Integer> decisions = inGamePLayers.get(i).promptDecision();
                    if(decisions.get(0) == 0){
                        inGamePLayers.get(i).getCoins(costToCheck);
                        totalCash += costToCheck;
                    }
                    else if(decisions.get(0) == 1){
                        inGamePLayers.get(i).getCoins(decisions.get(1));
                        totalCash += decisions.get(1);
                        costToCheck = decisions.get(1);
                    }
                    else{
                        inGamePLayers.remove(i);
                        i--;
                    }
                }
                System.out.println(String.format("There is now %d money in the pot.", totalCash));
            }
            else if(turn == 2){
                costToCheck = 0;
                for(int i = 0; i < inGamePLayers.size(); i++){
                    ArrayList<Integer> decisions = inGamePLayers.get(i).promptDecision();
                    if(decisions.get(0) == 0){
                        inGamePLayers.get(i).getCoins(costToCheck);
                        totalCash += costToCheck;
                    }
                    else if(decisions.get(0) == 1){
                        inGamePLayers.get(i).getCoins(decisions.get(1));
                        totalCash += decisions.get(1);
                        costToCheck = decisions.get(1);
                    }
                    else{
                        inGamePLayers.remove(i);
                        i--;
                    }
                }
                System.out.println("The fourth card is " + river.get(3).toString(true));
                System.out.println(String.format("There is now %d money in the pot.", totalCash));
            }
            else if(turn == 3){
                costToCheck = 0;
                for(int i = 0; i < inGamePLayers.size(); i++){
                    ArrayList<Integer> decisions = inGamePLayers.get(i).promptDecision();
                    if(decisions.get(0) == 0){
                        inGamePLayers.get(i).getCoins(costToCheck);
                        totalCash += costToCheck;
                    }
                    else if(decisions.get(0) == 1){
                        inGamePLayers.get(i).getCoins(decisions.get(1));
                        totalCash += decisions.get(1);
                        costToCheck = decisions.get(1);
                    }
                    else{
                        inGamePLayers.remove(i);
                        i--;
                    }
                }
                System.out.println("The fifth card is " + river.get(4).toString(true));
            }
            else{
                System.out.println("The final pot is " + totalCash);
                break;
            }
        }
    }
}
