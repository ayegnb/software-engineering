package kz.edu.nu.cs.se.hw;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Starter code for a class that implements the <code>PlayableRummy</code>
 * interface. A constructor signature has been added, and method stubs have been
 * generated automatically in eclipse.
 * 
 * Before coding you should verify that you are able to run the accompanying
 * JUnit test suite <code>TestRummyCode</code>. Most of the unit tests will fail
 * initially.
 * 
 * @see PlayableRummy
 * @see TestRummyCode
 *
 */
public class Rummy implements PlayableRummy {

    public final StepsStates draw = new DrawState(this);
    public final StepsStates meld = new MeldState(this);
    public final StepsStates discard = new DiscardState(this);
    public final StepsStates rummy = new RummyState(this);
    public final StepsStates waiting = new WaitingState(this);
    public final StepsStates finished = new FinishedState(this);

    private String[] players;
    private int currentPlayer;
    private StepsStates currentStep;
    private ArrayList<String> deck;
    private ArrayList<String> discardPile;
    private ArrayList<ArrayList<String>> playerHands;
    private String lastDrawnCard;
    private ArrayList<ArrayList<String>> melds;
    private int winner;
    private boolean canDeclareRummy;

    public Rummy(String... players) {
        if (players.length < 2) {
            throw new RummyException("Not enough players", 2);
        } else if (players.length > 6) {
            throw new RummyException("Too many players", 8);
        }
        this.players = players;
        this.playerHands = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < players.length; i++) {
            playerHands.add(new ArrayList<String>());
        }

        final String[] suits = new String[] { "C", "D", "H", "S", "M" };
        final String[] ranks = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
        this.deck = new ArrayList<String>(65);
        for (String suit : suits) {
            for (String rank : ranks) {
                this.deck.add(rank + suit);
            }
        }
        this.discardPile = new ArrayList<String>();
        this.currentStep = waiting;
        this.currentPlayer = 0;
        this.melds = new ArrayList<ArrayList<String>>();
        this.canDeclareRummy = true;
    }

    @Override
    public String[] getPlayers() {
        return this.players;
    }

    @Override
    public int getNumPlayers() {
        return this.players.length;
    }

    @Override
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public int getNumCardsInDeck() {
        return this.deck.size();
    }

    @Override
    public int getNumCardsInDiscardPile() {
        return this.discardPile.size();
    }

    @Override
    public String getTopCardOfDiscardPile() {
        String topCard = this.discardPile.remove(0);
        this.discardPile.add(0, topCard);
        return topCard;
    }

    public ArrayList<ArrayList<String>> getPlayerHands() {
        return this.playerHands;
    }

    public ArrayList<String> getDeck() {
        return this.deck;
    }

    @Override
    public String[] getHandOfPlayer(int player) {
        if (player > this.players.length-1) throw new RummyException("Not valid index of player", 10);
        return this.getPlayerHands().get(player).toArray(new String[this.getPlayerHands().get(player).size()]);
    }

    @Override
    public int getNumMelds() {
        return this.melds.size();
    }

    @Override
    public String[] getMeld(int i) {
        if (this.getMelds().size() <= i) throw new RummyException("Not valid index", 11);
        return this.melds.get(i).toArray(new String[melds.get(i).size()]);
    }

    @Override
    public void rearrange(String card) {
        this.currentStep.rearrange(card);
    }

    @Override
    public void shuffle(Long l) {
        this.currentStep.shuffle(l);
    }

    @Override
    public Steps getCurrentStep() {
        return this.currentStep.getStepName();
    }

    @Override
    public int isFinished() {
        return this.currentStep.isFinished();
    }

    @Override
    public void initialDeal() {
        this.currentStep.initialDeal();
    }

    @Override
    public void drawFromDiscard() {
        this.currentStep.drawFromDiscard();
    }

    @Override
    public void drawFromDeck() {
        this.currentStep.drawFromDeck();
    }

    @Override
    public void meld(String... cards) {
        this.currentStep.meld(cards);
    }

    @Override
    public void addToMeld(int meldIndex, String... cards) {
        System.out.println(cards);
        this.currentStep.addToMeld(meldIndex, cards);
    }

    @Override
    public void declareRummy() {
        this.currentStep.declareRummy();
    }

    @Override
    public void finishMeld() {
        this.currentStep.finishMeld();
    }

    @Override
    public void discard(String card) {
        this.currentStep.discard(card);
    }
    
    public void setCurrentStep(StepsStates step) {
        this.currentStep = step;
    }

    public ArrayList<String> getDiscardPile() {
        return this.discardPile;
    }

    public String getLastDrawnCard() {
        return this.lastDrawnCard;
    }

    public void setLastDrawnCard(String card) {
        this.lastDrawnCard = card;
    }

    public void setCurrentPlayer(int i) {
        this.currentPlayer = i;
    }

    public ArrayList<ArrayList<String>> getMelds() {
        return this.melds;
    }

    public int getWinner() {
        return this.winner;
    }

    public void setWinner(int i) {
        this.winner = i;
    }

    public boolean isCanDeclareRummy() { return this.canDeclareRummy; }

    public void setCanDeclareRummy(boolean permission) {
        this.canDeclareRummy = permission;
    }
}
