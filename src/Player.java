import java.util.ArrayList;
import java.util.Scanner;

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

    public Player(String name, int coinCount){
        this.name = name;
        this.coinCount = coinCount;
    }

    public Player(String name, ArrayList<Card> cards, int coinCount){
        this.name = name;
        this.cards = cards;
        this.coinCount = coinCount;
    }

    public  void setCards(ArrayList<Card> inputCards){
        this.cards = inputCards;
    }

    public ArrayList<Card> getCards(){
        return this.cards;
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        return String.format("Name: %s\nCoins: %d\nDeck: %s %s",this.name, this.coinCount, this.cards.get(0).toString(true),this.cards.get(1).toString(true));
    }

    public int getCoins(int idealAmount){
        if(idealAmount < this.coinCount){
            this.coinCount -= idealAmount;
        }
        else{
            this.coinCount = 0;
        }
        return idealAmount;
    }

    public void addCoins(int amount){
        this.coinCount += amount;
    }

    public ArrayList<Integer> promptDecision(){
        ArrayList<Integer> returnList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("What would you(%s) like to do?", this.name));
        System.out.println("0. Check/Call");
        System.out.println("1. Raise");
        System.out.println("2. Fold");
        int decision = scanner.nextInt();
        returnList.add(decision);
        if(decision == 1){
            System.out.println("How much would you like to raise?");
            int raiseAmount = scanner.nextInt();
            returnList.add(raiseAmount);
        }


        
        return returnList;
    }

}
