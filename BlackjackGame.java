import java.util.*;

public class BlackjackGame {
    static Random rand = new Random();
    static Scanner sc = new Scanner(System.in);

    public static int drawCard(boolean isPlayer) {
        int card = rand.nextInt(13) + 1;

        if (card == 1) {
            if (isPlayer) {
                while (true) {
                    System.out.print("You got an Ace! Choose value (1 or 11): ");
                    String input = sc.nextLine().trim();
                    if (input.equals("1")) return 1;
                    if (input.equals("11")) return 11;
                    System.out.println("Invalid choice. Please enter 1 or 11.");
                }
            } else {
                return 11; // Dealer uses 11, will adjust if bust
            }
        }

        return Math.min(card, 10); // Face cards = 10
    }

    public static String cardName(int value) {
        switch (value) {
            case 1: return "Ace";
            case 11: return "Jack";
            case 12: return "Queen";
            case 13: return "King";
            default: return String.valueOf(value);
        }
    }

    public static String cardDisplay(List<Integer> cards) {
        List<String> names = new ArrayList<>();
        for (int card : cards) {
            names.add(cardName(card));
        }
        return names.toString();
    }

    public static boolean askYesNo(String message) {
        while (true) {
            System.out.print(message + " (1 = Yes, 2 = No): ");
            String input = sc.nextLine().trim();
            if (input.equals("1")) return true;
            if (input.equals("2")) return false;
            System.out.println("Invalid input. Please enter 1 for Yes or 2 for No.");
        }
    }

    public static void main(String[] args) {
        while (true) {
            int playerTotal = 0, dealerTotal = 0;
            List<Integer> playerCards = new ArrayList<>();
            List<Integer> dealerCards = new ArrayList<>();

            System.out.println("\n-- New Game Started --");

            for (int i = 0; i < 2; i++) {
                int card = drawCard(true);
                playerCards.add(card);
                playerTotal += card;
            }

            for (int i = 0; i < 2; i++) {
                int card = drawCard(false);
                dealerCards.add(card);
                dealerTotal += (card == 11 && dealerTotal + 11 > 21) ? 1 : card;
            }

            System.out.println("\nYour cards: " + cardDisplay(playerCards) + " | Total: " + playerTotal);
            System.out.println("Dealer cards: " + cardDisplay(dealerCards) + " | Total: " + dealerTotal);

            // Player's turn
            while (true) {
                if (playerTotal == 21) {
                    System.out.println("Blackjack! You win!");
                    break;
                }
                if (playerTotal > 21) {
                    System.out.println("Bust! You lose.");
                    break;
                }

                if (!askYesNo("Do you want to draw another card?")) break;

                int card = drawCard(true);
                playerCards.add(card);
                playerTotal += card;

                System.out.println("You drew a " + cardName(card));
                System.out.println("Your cards: " + cardDisplay(playerCards) + " | Total: " + playerTotal);
                System.out.println("Dealer cards: " + cardDisplay(dealerCards) + " | Total: " + dealerTotal);
            }

            // Dealer's turn
            if (playerTotal <= 21) {
                while (dealerTotal < 17) {
                    int card = drawCard(false);
                    dealerCards.add(card);
                    dealerTotal += (card == 11 && dealerTotal + 11 > 21) ? 1 : card;
                    System.out.println("Dealer drew a " + cardName(card));
                    System.out.println("Dealer cards: " + cardDisplay(dealerCards) + " | Total: " + dealerTotal);
                }

                // Final Result
                System.out.println("\n--- Final Result ---");
                System.out.println("Your total: " + playerTotal);
                System.out.println("Dealer total: " + dealerTotal);

                if (dealerTotal > 21 || playerTotal > dealerTotal) {
                    System.out.println("You win!");
                } else if (dealerTotal > playerTotal) {
                    System.out.println("Dealer wins.");
                } else {
                    System.out.println("It's a tie!");
                }
            }

            if (!askYesNo("\nDo you want to play again?")) {
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }
}
