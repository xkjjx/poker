import java.util.Map;

public class Card {
    private int Suit;
    private int level;
    private static Map<Integer,String> numToStringMap= Map.of(1,"One",2,"Two",3,"Three",4,"Four",5,"Five",6,"Six",7,"Seven",8,"Eight",9,"Nine",10,"Ten");
    public Card(int Suit, int level){
        this.Suit = Suit;
        this.level = level;
    }
    public int getSuit(){
        return this.Suit;
    }

    public String getDescriptiveSuit(boolean concise){
        switch(Suit){
            case(0): return concise ? "H" : "Hearts";
            case(1): return concise ? "D" : "Diamonds";
            case(2): return concise ? "S" : "Spades";
            case(3): return concise ? "C" : "Clubs";
            default: return "";
        }
    }

    public String getDescriptiveSuit(){
        return getDescriptiveSuit(false);
    }
    
    
    public int getLevel(){
        return this.level;
    }

    public String getDescriptiveLevel(boolean concise){
        switch(level){
            case(12): return concise ? "A" : "Ace";
            case(11): return concise ? "K" : "King";
            case(10): return concise ? "Q" : "Queen";
            case(9): return concise ? "J" : "Jack";
            default: return concise ? Integer.toString(level + 2) : numToStringMap.get(level + 2);
        }
    }

    public boolean highCardBeat(Card other){
        return this.level > other.level;
    }

    public String toString(){
        return toString(false);
    }

    public String toString(boolean concise){
        return getDescriptiveLevel(concise) + (concise ? "|":" of ") + getDescriptiveSuit(concise);
    }
}
