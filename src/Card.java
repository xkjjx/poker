public class Card {
    private String Suit;
    private char level;

    public Card(String Suit,char level){
        this.Suit = Suit;
        this.level = level;
    }
    public String getSuit(){
        return this.Suit;
    }

    public char getLevel(){
        return this.level;
    }

    public String getDescriptiveLevel(){
        switch(level){
            case(12): return "K";
            case(11): return "Q";
            case(10): return "J";
            case(0): return "A";
            default: return Integer.toString(level + 1);
        }
    }

    public boolean highCardBeat(Card other){
        int otherCardLevel = other.level == 0 ? 14 : other.level;
        return this.level > otherCardLevel;
    }
}
