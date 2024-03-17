import java.util.ArrayList;



public class Deck {
    private ArrayList<Card> deck= new ArrayList<Card>();
    private void initDeck(){
        deck.clear();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                deck.add(new Card(i,j));
            }
        }
        //this.shuffle();
    }

    public Deck(){
        initDeck();
    }

    public String toString(){
        return toString(false);
    }

    public String toString(boolean concise){
        String outputString = "";
        for(int i = 0; i < deck.size(); i++){
            outputString += deck.get(i).toString(concise) + (i == deck.size() ? "" : "\n");
        }
        return outputString;
    }

    public void shuffle(){
        //TODO
    }

    public Card draw(){
        Card drawedCard = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);
        return drawedCard;
    }

    public ArrayList<Card> drawRiver(){
        ArrayList<Card> river = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            river.add(this.draw());
        }
        return river;
    }

    public ArrayList<Card> drawHandForPlayer(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(this.draw());
        cards.add(this.draw());
        return cards;
    }

    public ArrayList<ArrayList<Card>> drawHandsForPlayers(int numPlayers){
        ArrayList<ArrayList<Card>> playerCards = new ArrayList<>();
        for(int i = 0 ; i < numPlayers; i++){
            playerCards.add(drawHandForPlayer());
        }
        return playerCards;
    }
}
