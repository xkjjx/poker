import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

public class Game {
    //private static ArrayList<String> actions = new ArrayList<{"Check/Call", "Raise", "Fold"}>();
    private int pot;
    private int turn;
    // turn 0 is first turn
    //turn 1 is after first three cards are revealed
    //turn 2 is after 4th card
    //turn 3 is after 5th card - final chance to bet
    private int numPlayers;
    private ArrayList<String> history;
    private ArrayList<Player> players;
    private Deck deck = new Deck();
    
    private ArrayList<Card> river;

    private int dealerInd = 0;

    public Game(ArrayList<Player> players){
        this.players = players;
        this.numPlayers = this.players.size(); 
        this.pot = 10;
    }

    public int oneCycle(ArrayList<Player> inGamePlayers, int costToCheck, int totalCash){
        for(int i = 0; i < inGamePlayers.size(); i++){
            ArrayList<Integer> decisions = inGamePlayers.get(i).promptDecision();
            if(decisions.get(0) == 0){
                inGamePlayers.get(i).getCoins(costToCheck);
                totalCash += costToCheck;
            }
            else if(decisions.get(0) == 1){
                inGamePlayers.get(i).getCoins(decisions.get(1));
                totalCash += decisions.get(1);
                costToCheck = decisions.get(1);
            }
            else{
                inGamePlayers.remove(i);
                i--;
            }
        }
        return totalCash;
    }


    private int hasRoyalFlush(Player player, ArrayList<Card> river){
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        allCards.sort(null);
        for(int i = 0; i < allCards.size() - 4; i++){
            if(allCards.get(i).getLevel() == 8 && allCards.get(i + 1).getLevel() == 9 && allCards.get(i + 2).getLevel() == 10 && allCards.get(i + 3).getLevel() == 11 && allCards.get(i + 4).getLevel() == 12){
                if(allCards.get(i).getSuit() == allCards.get(i + 1).getSuit() && allCards.get(i).getSuit() == allCards.get(i + 2).getSuit() && allCards.get(i).getSuit() == allCards.get(i + 3).getSuit() && allCards.get(i).getSuit() == allCards.get(i + 4).getSuit()){
                    return allCards.get(i).getLevel();
                }
            }
        }
        return -1;
    }

    private int hasStraightFlush(Player player, ArrayList<Card> river){
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        allCards.sort(null);
        for(int i = 0; i < allCards.size() - 4; i++){
            if(allCards.get(i).getLevel() == allCards.get(i + 1).getLevel() - 1 && allCards.get(i).getLevel() == allCards.get(i + 2).getLevel() - 2 && allCards.get(i).getLevel() == allCards.get(i + 3).getLevel() - 3 && allCards.get(i).getLevel() == allCards.get(i + 4).getLevel() - 4){
                if(allCards.get(i).getSuit() == allCards.get(i + 1).getSuit() && allCards.get(i).getSuit() == allCards.get(i + 2).getSuit() && allCards.get(i).getSuit() == allCards.get(i + 3).getSuit() && allCards.get(i).getSuit() == allCards.get(i + 4).getSuit()){
                    return allCards.get(i).getLevel();
                }
            }
        }
        return -1;
    }

    private ArrayList<Integer> hasFlush(Player player, ArrayList<Card> river){
        ArrayList<Integer> retList = new ArrayList<>();
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        allCards.sort(null);
        for(int i = 0; i < allCards.size() - 4; i++){
            if(allCards.get(i).getSuit() == allCards.get(i + 1).getSuit() && allCards.get(i).getSuit() == allCards.get(i + 2).getSuit() && allCards.get(i).getSuit() == allCards.get(i + 3).getSuit() && allCards.get(i).getSuit() == allCards.get(i + 4).getSuit()){
                retList.add(allCards.get(i).getLevel());
                retList.add(allCards.get(i + 1).getLevel());
                retList.add(allCards.get(i + 2).getLevel());
                retList.add(allCards.get(i + 3).getLevel());
                retList.add(allCards.get(i + 4).getLevel());
                return retList;
            }
        }
        return retList;
    }

    private int hasStraight(Player player, ArrayList<Card> river){
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        allCards.sort(null);
        for(int i = 0; i < allCards.size() - 4; i++){
            if(allCards.get(i).getLevel() == allCards.get(i + 1).getLevel() - 1 && allCards.get(i).getLevel() == allCards.get(i + 2).getLevel() - 2 && allCards.get(i).getLevel() == allCards.get(i + 3).getLevel() - 3 && allCards.get(i).getLevel() == allCards.get(i + 4).getLevel() - 4){
                return allCards.get(i).getLevel();
            }
        }
        return -1;
    }

    private int hasFourOfAKind(Player player, ArrayList<Card> river){
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        allCards.sort(null);
        for(int i = 0; i < allCards.size() - 3; i++){
            if(allCards.get(i).getLevel() == allCards.get(i + 1).getLevel() && allCards.get(i).getLevel() == allCards.get(i + 2).getLevel() && allCards.get(i).getLevel() == allCards.get(i + 3).getLevel()){
                return allCards.get(i).getLevel();
            }
        }
        return -1;
    }

    private int hasTriple(ArrayList<Card> allCards){
        allCards.sort(null);
        for(int i = 0; i < allCards.size() - 2; i++){
            if(allCards.get(i).getLevel() == allCards.get(i + 1).getLevel() && allCards.get(i).getLevel() == allCards.get(i + 2).getLevel()){
                return allCards.get(i).getLevel();
            }
        }
        return -1;
    }

    private int hasDouble(ArrayList<Card> allCards){
        allCards.sort(null);
        for(int i = 0; i < allCards.size() - 1; i++){
            if(allCards.get(i).getLevel() == allCards.get(i + 1).getLevel()){
                return allCards.get(i).getLevel();
            }
        }
        return -1;
    }

    private int hasTriple(Player player, ArrayList<Card> river){
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        return hasTriple(allCards);
    }

    private int hasDouble(Player player, ArrayList<Card> river){
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        return hasDouble(allCards);
    }

    private ArrayList<Integer> hasFullHouse(Player player, ArrayList<Card> river){
        ArrayList<Integer> retList = new ArrayList<>();
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        allCards.sort(null);
        int tripleLevel = hasTriple(allCards);
        if(tripleLevel != -1){
            retList.add(tripleLevel);
            for(int i = 0; i < allCards.size() - 2; i++){
                if(allCards.get(i).getLevel() == allCards.get(i + 1).getLevel() && allCards.get(i).getLevel() == allCards.get(i + 2).getLevel()){
                    allCards.remove(i);
                    allCards.remove(i);
                    allCards.remove(i);
                    int doubleLevel = hasDouble(allCards);
                    if(doubleLevel != -1){
                        retList.add(doubleLevel);
                        return retList;
                    }
                }
            }
        }
        retList.clear();
        return retList;
    }

    private ArrayList<Integer> hasTwoPair(Player player, ArrayList<Card> river){
        ArrayList<Integer> retList = new ArrayList<>();
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        allCards.sort(null);
        int double1 = hasDouble(allCards);
        if(double1 != -1){
            retList.add(double1);
            for(int i = 0; i < allCards.size() - 1; i++){
                if(allCards.get(i).getLevel() == allCards.get(i + 1).getLevel()){
                    allCards.remove(i);
                    allCards.remove(i);
                    int double2 = hasDouble(allCards);
                    if(double2 != -1){
                        retList.add(double2);
                        return retList;
                    }
                }
            }
        }
        retList.clear();
        return retList;
    }

    private int findHighCard(Player player, ArrayList<Card> river){
        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(player.getCards());
        allCards.addAll(river);
        allCards.sort(null);
        return allCards.get(allCards.size() - 1).getLevel();
    }


    private ArrayList<Player> findWinner(ArrayList<Player> inGamePlayers, ArrayList<Card> river){
        //order each player's best hands by a code - 0 is high card, 1 is pair, 2 is two pair, 3 is triple, 4 is straight, 5 is flush, 6 is full house, 7 is four of a kind, 8 is straight flush, 9 is royal flush
        //if two players have the same hand, compare the highest card in the hand
        //if two players have the same hand and the same highest card, compare the second highest card in the hand
        //if two players have the same hand and the same highest and second highest card, compare the third highest card in the hand
        //if two players have the same hand and the same highest, second highest, and third highest card, compare the fourth highest card in the hand
        //if two players have the same hand and the same highest, second highest, third highest, and fourth highest card, compare the fifth highest card in the hand
        //if two players have the same hand and the same highest, second highest, third highest, fourth highest, and fifth highest card, the pot is split
        ArrayList<Player> winners = new ArrayList<>();

        Map <Integer,Integer> bestHands = new HashMap<>();
        Map <Integer,Integer> bestLevels = new HashMap<>();
        for(int i = 0; i < inGamePlayers.size(); i++){
            if(hasRoyalFlush(inGamePlayers.get(i), river) != -1){
                bestHands.put(inGamePlayers.get(i).getId(), 9);
                bestLevels.put(inGamePlayers.get(i).getId(), hasRoyalFlush(inGamePlayers.get(i), river));
            }
            else if(hasStraightFlush(inGamePlayers.get(i), river) != -1){
                bestHands.put(inGamePlayers.get(i).getId(), 8);
                bestLevels.put(inGamePlayers.get(i).getId(), hasStraightFlush(inGamePlayers.get(i), river));
            }
            else if(hasFourOfAKind(inGamePlayers.get(i), river) != -1){
                bestHands.put(inGamePlayers.get(i).getId(), 7);
                bestLevels.put(inGamePlayers.get(i).getId(), hasFourOfAKind(inGamePlayers.get(i), river));
            }
            else if(hasFullHouse(inGamePlayers.get(i), river).size() != 0){
                bestHands.put(inGamePlayers.get(i).getId(), 6);
                bestLevels.put(inGamePlayers.get(i).getId(), hasFullHouse(inGamePlayers.get(i), river).get(0));
            }
            else if(hasFlush(inGamePlayers.get(i), river).size() != 0){
                bestHands.put(inGamePlayers.get(i).getId(), 5);
                bestLevels.put(inGamePlayers.get(i).getId(), hasFlush(inGamePlayers.get(i), river).get(0));
            }
            else if(hasStraight(inGamePlayers.get(i), river) != -1){
                bestHands.put(inGamePlayers.get(i).getId(), 4);
                bestLevels.put(inGamePlayers.get(i).getId(), hasStraight(inGamePlayers.get(i), river));
            }
            else if(hasTriple(inGamePlayers.get(i), river) != -1){
                bestHands.put(inGamePlayers.get(i).getId(), 3);
                bestLevels.put(inGamePlayers.get(i).getId(), hasTriple(inGamePlayers.get(i), river));
            }
            else if(hasTwoPair(inGamePlayers.get(i), river).size() != 0){
                bestHands.put(inGamePlayers.get(i).getId(), 2);
                bestLevels.put(inGamePlayers.get(i).getId(), hasTwoPair(inGamePlayers.get(i), river).get(0));
            }
            else if(hasDouble(inGamePlayers.get(i), river) != -1){
                bestHands.put(inGamePlayers.get(i).getId(), 1);
                bestLevels.put(inGamePlayers.get(i).getId(), hasDouble(inGamePlayers.get(i), river));
            }
            else{
                bestHands.put(inGamePlayers.get(i).getId(), 0);
                bestLevels.put(inGamePlayers.get(i).getId(), findHighCard(inGamePlayers.get(i), river));
            }
        }
        Comparator<Player> comparator = Comparator.comparingInt(obj -> bestHands.get(obj.getId()));
        comparator = comparator.thenComparingInt(obj -> bestLevels.get(obj.getId()));
        Collections.sort(inGamePlayers, comparator);
        winners.add(inGamePlayers.get(inGamePlayers.size() - 1));
        for(int i = inGamePlayers.size() - 2; i >= 0; i--){
            if(bestHands.get(inGamePlayers.get(i).getId()) == bestHands.get(inGamePlayers.get(inGamePlayers.size() - 1).getId()) && bestLevels.get(inGamePlayers.get(i).getId()) == bestLevels.get(inGamePlayers.get(inGamePlayers.size() - 1).getId())){
                winners.add(inGamePlayers.get(i));
            }
            else{
                break;
            }
        }
        for(int i = 0; i < inGamePlayers.size(); i++){
            switch(bestHands.get(inGamePlayers.get(i).getId())){
                case 0:
                    System.out.println(inGamePlayers.get(i).getName() + " has a high card of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
                case 1:
                    System.out.println(inGamePlayers.get(i).getName() + " has a pair of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
                case 2:
                    System.out.println(inGamePlayers.get(i).getName() + " has two pairs of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
                case 3:
                    System.out.println(inGamePlayers.get(i).getName() + " has a triple of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
                case 4:
                    System.out.println(inGamePlayers.get(i).getName() + " has a straight of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
                case 5:
                    System.out.println(inGamePlayers.get(i).getName() + " has a flush of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
                case 6:
                    System.out.println(inGamePlayers.get(i).getName() + " has a full house of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
                case 7:
                    System.out.println(inGamePlayers.get(i).getName() + " has four of a kind of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
                case 8:
                    System.out.println(inGamePlayers.get(i).getName() + " has a straight flush of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
                case 9:
                    System.out.println(inGamePlayers.get(i).getName() + " has a royal flush of " + bestLevels.get(inGamePlayers.get(i).getId()));
                    break;
            }
        }
        return winners;
    }


    public void processRound(){
        int costToCheck = pot;
        int totalCash = 0;
        deck.resetDeck();
        ArrayList<Player> inGamePLayers = players;
        ArrayList<ArrayList<Card>> playerCards = deck.drawHandsForPlayers(numPlayers);
        for(int i = dealerInd + 1; i < numPlayers + dealerInd + 1; i++){
            players.get(i % numPlayers).setCards(playerCards.get(i % numPlayers));
            System.out.println(players.get(i % numPlayers).getName() + " has been dealt " + playerCards.get(i % numPlayers).get(0).toString(true) + " and " + playerCards.get(i % numPlayers).get(1).toString(true));
        }
        river = deck.drawRiver();
        System.out.println("The cost to stay in is currently " + costToCheck);
        for(turn = 0; turn < 5; turn++){
            if(inGamePLayers.size() == 1){
                System.out.println(inGamePLayers.get(0).getName() + " has won the pot of " + totalCash);
                inGamePLayers.get(0).addCoins(totalCash);
                break;
            }
            if(turn == 0){
                totalCash = oneCycle(inGamePLayers, pot, totalCash);
                System.out.println(String.format("There is now %d money in the pot.", totalCash));
            }
            else if(turn == 1){
                System.out.println("The first three cards are " + river.get(0).toString(true) + ", " + river.get(1).toString(true) + ", and " + river.get(2).toString(true));
                System.out.println("The river is " + river.get(0).toString(true) + ", " + river.get(1).toString(true) + ", and " + river.get(2).toString(true));
                totalCash = oneCycle(inGamePLayers, 0, totalCash);
                System.out.println(String.format("There is now %d money in the pot.", totalCash));
            }
            else if(turn == 2){
                System.out.println("The fourth card is " + river.get(3).toString(true));
                System.out.println("The river is " + river.get(0).toString(true) + ", " + river.get(1).toString(true) + ", " + river.get(2).toString(true) + ", and " + river.get(3).toString(true));
                totalCash = oneCycle(inGamePLayers, 0, totalCash);
                System.out.println(String.format("There is now %d money in the pot.", totalCash));
            }
            else if(turn == 3){
                System.out.println("The fifth card is " + river.get(4).toString(true));
                System.out.println("The river is " + river.get(0).toString(true) + ", " + river.get(1).toString(true) + ", " + river.get(2).toString(true) + ", " + river.get(3).toString(true) + ", and " + river.get(4).toString(true));
                totalCash = oneCycle(inGamePLayers, 0, totalCash);
            }
            else{
                System.out.println("The final pot is " + totalCash);
                ArrayList<Player> winners = findWinner(inGamePLayers, river);
                System.out.println("The winner(s) are: ");
                for(int i = 0; i < winners.size(); i++){
                    System.out.println(winners.get(i).getName());
                    winners.get(i).addCoins(totalCash / winners.size());
                }
                break;
            }
        }
    }
}
