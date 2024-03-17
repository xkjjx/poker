import java.util.ArrayList;

public class Driver{
    public static void main(String args[]){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("kevin",500));
        players.add(new Player("Jonas",500));
        Game game = new Game(players);
        game.processRound();
    }
}