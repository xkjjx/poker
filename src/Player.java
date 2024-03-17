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
//         ArrayList<Integer> returnList = new ArrayList<>();
//         System.out.println("Enter call/check(C), raise(R), fold(F)");
//         byte[] input = new byte[1];
//         try{
//             System.in.read(input);
//         }
//         catch(Exception e){
//             e.printStackTrace();
//         }
//         char inputChar = (char) input[0];
//         int inputAmount;
// ;        switch(inputChar){
//             case('C')
//         }

        

    }

}
