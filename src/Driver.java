public class Driver{
    public static void main(String args[]){
        Deck deck = new Deck();
        System.out.println(deck);
        System.out.println();
        System.out.println(deck.drawHandsForPlayers(2));
        System.out.println();
        System.out.println(deck.drawRiver());
        System.out.println();
        System.out.println(deck);
    }
}