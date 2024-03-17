public class Driver{
    public static void main(String args[]){
        Deck deck = new Deck();
        System.out.println(deck);
        Player player = new Player("Kevin", deck.drawHandForPlayer(), 500);
        System.out.println();
        System.out.println(player);
        System.out.println();
        System.out.println(deck);
    }
}