import java.util.ArrayList;

public class Player {
    private ArrayList<Card> cards;
    private String name;
    private int coinCount;

    public Player(String name, ArrayList<Card> cards){
        this.name = name;
        this.cards = cards;
        this.coinCount = 0;
    }

    public Player(String name){
        this.name = name;
        this.coinCount = 0;
    }

    public Player(String name, ArrayList<Card> cards, int coinCount){
        this.name = name;
        this.cards = cards;
        this.coinCount = coinCount;
    }

    public  void setCards(ArrayList<Card> inputCards){
        this.cards = inputCards;
    }

    public String toString(){
        return String.format("Name: %s\nCoins: %d\nDeck: %s %s",this.name, this.coinCount, this.cards.get(0).toString(true),this.cards.get(1).toString(true));
    }

}
