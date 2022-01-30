import java.util.*;

public class Blackjack{
    static Scanner in = new Scanner(System.in);
    static int findValue(ArrayList<String> thecards){
        int val = 0;
        for(int i = 0; i < thecards.size(); i++){
            char ch = thecards.get(i).charAt(0);
            int cardval = ch - 48;
            if(cardval < 10){
                val += cardval;
            } else{
                val += 10;
            }
        }
        return val;
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    static void end(String pv, String dv){
        System.out.println("Game over. The value of your cards was " + pv + ".");
        System.out.println("The value of the dealer's cards was " + dv + ".");
    }
    public static void main(String[] args){
        Random rand = new Random();
        ArrayList<String> deck = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 1; j <= 13; j++){
                String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
                if(j < 10){
                    deck.add(String.valueOf(j) + suits[i]);
                } else if(j == 10){
                    deck.add("T" + suits[i]);
                } else if(j == 11){
                    deck.add("J" + suits[i]);
                } else if(j == 12){
                    deck.add("Q" + suits[i]);
                } else if(j == 13){
                    deck.add("K" + suits[i]);
                }
            }
        }

        class Dealer{
            private ArrayList<String> cards = new ArrayList<String>();
            private int value = 0;
            public Dealer(){
            }
            public void start(){
                int r1 = rand.nextInt(deck.size()-1);
                cards.add(deck.get(r1));
                deck.remove(r1);
                int r2 = rand.nextInt(deck.size()-1);
                cards.add(deck.get(r2));
                deck.remove(r2);
                value = findValue(cards);
            }
        }

        class Player{
            private ArrayList<String> cards  = new ArrayList<String>();
            private int value = 0;
            public Player() {
            }
            public void start(){
                int r1 = rand.nextInt(deck.size()-1);
                cards.add(deck.get(r1));
                deck.remove(r1);
                int r2 = rand.nextInt(deck.size()-1);
                cards.add(deck.get(r2));
                deck.remove(r2);;
                value = findValue(cards);
            }
            public void draw(){
                int r = rand.nextInt(deck.size()-1);
                cards.add(deck.get(r));
                deck.remove(r);
                value = findValue(cards);
                System.out.println("You drew a " + cards.get(cards.size()-1).substring(0,1) + " of " + cards.get(cards.size()-1).substring(1) + ".");
                System.out.println("The value of your cards is now " + value + ".");
            }
        }
        Dealer dealer = new Dealer();
        Player player = new Player();
        player.start();
        dealer.start();
        String cardtext = "";
        for(int i = 0; i < player.cards.size(); i++){
            if(i < player.cards.size() - 1){
                cardtext += (player.cards.get(i).substring(0,1) + " of " + player.cards.get(i).substring(1) + ", ");
            } else{
                cardtext += ("and " + player.cards.get(i).substring(0,1) + " of " + player.cards.get(i).substring(1));
            }
        }
        System.out.println("Your cards are " + cardtext + " with a total value of " + findValue(player.cards) + ".");
        while(true){
            System.out.println("Press 1 to hit or press 2 to stand.");
            String inp = in.nextLine();
            if(isNumeric(inp)){
                if(Integer.parseInt(inp) == 1){
                    player.draw();
                    if(player.value > 21){
                        end(String.valueOf(player.value), String.valueOf(dealer.value));
                        break;
                    }
                } else if(Integer.parseInt(inp) == 2){
                    System.out.println("The value of your cards was " + player.value + ".");
                    System.out.println("The value of the dealer's cards was " + dealer.value + ".");
                    if(player.value > dealer.value){
                        System.out.println("Congratulations, you won!");
                    } else if(player.value < dealer.value){
                        System.out.println("Unfortunately, you lost.");
                    } else if(player.value == dealer.value){
                        System.out.println("Wow, you and the dealer tied!");
                    }
                    break;
                }
            }
        }
    }
}

