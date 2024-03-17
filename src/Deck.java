import java.util.ArrayList;



public class Deck {
    private ArrayList<Card> deck= new ArrayList<Card>();
    private void initDeck(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                deck.add(new Card(i,j));
            }
        }
    }

    public Deck(){
        initDeck();
    }

    public String toString(){
        return toString(false);
    }

    public String toString(boolean concise){
        String outputString = "";
        for(int i = 0; i < 52; i++){
            outputString += deck.get(i).toString(concise) + (i == 51 ? "" : "\n");
        }
        return outputString;
    }
}
