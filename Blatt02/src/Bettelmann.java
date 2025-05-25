import java.util.*;

/**
 * The class {@code Bettelmann} simulated the card game 'Bettelmann'. You can construct objects
 * either by providing the piles of cards of the two players, or by requesting a shuffled
 * distribution of cards.
 */
public class Bettelmann {
    private Deque<Card> closedPile1;
    private Deque<Card> closedPile2;
    private int winner = -1;

    /**
     * Constructor which initializes both players with empty piles.
     */
    public Bettelmann() {
        closedPile1 = new LinkedList<>();
        closedPile2 = new LinkedList<>();
    }

    /**
     * Constructor which initializes both players with the provided piles of cards.
     *
     * @param pile1 pile of cards of player 1.
     * @param pile2 pile of cards of player 2.
     */
    public Bettelmann(Deque<Card> pile1, Deque<Card> pile2) {
        closedPile1 = pile1;
        closedPile2 = pile2;
    }

    /**
     * Returns the closed pile of player 1 (required for the tests).
     *
     * @return The closed pile of player 1.
     */
    public Deque<Card> getClosedPile1() {
        return closedPile1;
    }

    /**
     * Returns the closed pile of player 2 (required for the tests).
     *
     * @return The closed pile of player 2.
     */
    public Deque<Card> getClosedPile2() {
        return closedPile2;
    }

    /**
     * Play one round of the game. This includes drawing more cards, when both players
     * have drawn cards of the same rank. At the end of the round, the player with the
     * higher ranked card wins the trick, so all drawn cards from that round are added
     * to the bottom of her/his closed pile of cards.
     */
    public void playRound() {
        // Cozi pentru carti deschise (offene Stapel)
        Deque<Card> openPile1 = new ArrayDeque<>();
        Deque<Card> openPile2 = new ArrayDeque<>();

        // Verificare stive ascunse initiale
        if (closedPile1.isEmpty() || closedPile2.isEmpty()) {
            // Dacă unul nu mai are carti, setam castigatorul
            if (closedPile1.isEmpty() && closedPile2.isEmpty()) {
                winner = 0; // egalitate
            } else if (closedPile1.isEmpty()) {
                winner = 2;
            } else {
                winner = 1;
            }
            return;
        }

        // Extragem prima carte din fiecare stiva ascunsa (ultima adaugata, LIFO)
        openPile1.addLast(closedPile1.pollLast());
        openPile2.addLast(closedPile2.pollLast());

        // Continuam sa extragem si sa punem pe openPile daca cartile au acelasi rang
        while (openPile1.peekLast().compareTo(openPile2.peekLast()) == 0) {
            // Verificam daca mai au carti ascunse de extras
            if (closedPile1.isEmpty() || closedPile2.isEmpty()) {
                // Final joc, egalitate daca ambele nu au carti, altfel castiga cel care mai are
                if (closedPile1.isEmpty() && closedPile2.isEmpty()) {
                    winner = 0;
                } else if (closedPile1.isEmpty()) {
                    winner = 2;
                } else {
                    winner = 1;
                }
                return;
            }
            // Punem cate o carte in plus pe openPile din stivele ascunse
            openPile1.addLast(closedPile1.pollLast());
            openPile2.addLast(closedPile2.pollLast());
        }

        // Determinam castigatorul rundei dupa ultima carte pusa deschis
        int cmp = openPile1.peekLast().compareTo(openPile2.peekLast());

        Deque<Card> winnerOpenPile = new ArrayDeque<>();
        if (cmp > 0) {
            // Jucator 1 castiga
            // Ia cartile sale deschise + cele ale adversarului
            winnerOpenPile.addAll(openPile1);
            winnerOpenPile.addAll(openPile2);
            // Inversam pilea castigatoare (conform regulii)
            reverseDeque(winnerOpenPile);
            // Punem sub stiva ascunsa a jucatorului 1 (addFirst deoarece LIFO)
            for (Card c : winnerOpenPile) {
                closedPile1.addFirst(c);
            }
        } else {
            // Jucator 2 castiga
            winnerOpenPile.addAll(openPile2);
            winnerOpenPile.addAll(openPile1);
            reverseDeque(winnerOpenPile);
            for (Card c : winnerOpenPile) {
                closedPile2.addFirst(c);
            }
        }

        // Verificam daca s-a terminat jocul (daca vreunul nu mai are carti ascunse)
        if (closedPile1.isEmpty() && closedPile2.isEmpty()) {
            winner = 0; // egalitate
        } else if (closedPile1.isEmpty()) {
            winner = 2;
        } else if (closedPile2.isEmpty()) {
            winner = 1;
        } else {
            winner = -1; // joc continua
        }
    }

    // Functie auxiliara pentru inversarea unui Deque<Card>
    private void reverseDeque(Deque<Card> deque) {
        // metoda simpla: muta elementele intr-un stack apoi inapoi
        Stack<Card> stack = new Stack<>();
        while (!deque.isEmpty()) {
            stack.push(deque.pollFirst());
        }
        while (!stack.isEmpty()) {
            deque.addLast(stack.pop());
        }
    }


    private void collectWinnings(Deque<Card> winnerPile, Deque<Card> open1, Deque<Card> open2) {
        // Add winner's cards first (maintaining order)
        while (!open1.isEmpty()) {
            winnerPile.addLast(open1.pollFirst());
        }
        // Then add opponent's cards
        while (!open2.isEmpty()) {
            winnerPile.addLast(open2.pollFirst());
        }

    }

    /**
     * Returns the winner of the game after the end, or -1 during the game.
     *
     * @return the winner of game (1 or 2), or -1 while the game is ongoing.
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Deal the given deck of cards alternately to the two players.
     * Side effect: The deck is empty after calling this method.
     *
     * @param deck The deck of cards that is distributed to the players.
     */
    public void distributeCards(Stack<Card> deck) {
        closedPile1.clear();
        closedPile2.clear();
        // use addFirst() because the last distributed card should be drawn first
        while (!deck.isEmpty()) {
            Card card = deck.pop();
            closedPile1.addFirst(card);
            if (!deck.isEmpty()) {
                card = deck.pop();
                closedPile2.addFirst(card);
            }
        }
    }

    /**
     * Shuffle a deck of cards and distribute it evenly to the two players.
     */
    public void distributeCards() {
        Stack<Card> deck = new Stack<>();
        for (int i = 0; i < Card.nCards; i++){
            deck.add(new Card(i));
        }
        Collections.shuffle(deck);
        distributeCards(deck);
    }

    /**
     * Returns a String representation of closed piles of cards of the two players.
     *
     * @return String representation of the state of the game.
     */
    @Override
    public String toString() {
        return "Player 1: " + closedPile1 + "\nPlayer 2: " + closedPile2;
    }

    public static void main(String[] args) {
/*
        // Game with a complete, shuffled deck
        Bettelmann game = new Bettelmann();
        game.distributeCards();
*/

        // For testing, you may also use specific distribtions and a small number of cards like this:
        int[] deckArray = {28, 30, 6, 23, 17, 14};
        Stack<Card> deck = new Stack<>();
        for (int id : deckArray) {
            deck.push(new Card(id));
        }
        Bettelmann game = new Bettelmann();
        game.distributeCards(deck);

        // This part is the same for both of the above variants
        System.out.println("Initial situation (top card first):\n" + game);
        int round = 0;
        while (round < 1000000 && game.getWinner()<0) {
            round++;
            game.playRound();
            System.out.println("State after round " + round + ":\n" + game);
        }
    }
}

