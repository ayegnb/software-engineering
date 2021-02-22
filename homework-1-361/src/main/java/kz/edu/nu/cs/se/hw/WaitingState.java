package kz.edu.nu.cs.se.hw;

import java.util.ArrayList;
import java.util.Collections;

public class WaitingState extends StepsStates {

    private Rummy rummy;

    public WaitingState(Rummy rummy) {
        this.rummy = rummy;
    }

    @Override
    public void rearrange(String card) {
        this.rummy.getDeck().remove(card);
        this.rummy.getDeck().add(0, card);
    }

    @Override
    public void shuffle(Long l) {
        Collections.shuffle(this.rummy.getDeck());
    }

    @Override
    public int isFinished() { return -1; }

    @Override
    public void initialDeal() {
        int numOfDeals = 0;
        if (this.rummy.getNumPlayers() == 2) {
            numOfDeals = 10;
        } else if (this.rummy.getNumPlayers() <= 4) {
            numOfDeals = 7;
        } else if (this.rummy.getNumPlayers() <= 6) {
            numOfDeals = 6;
        }

        for(int i = 0; i < numOfDeals; i++) {
            for(ArrayList<String> playerHand: this.rummy.getPlayerHands()) {
                playerHand.add(this.rummy.getDeck().remove(0));
            }
        }

        this.rummy.getDiscardPile().add(this.rummy.getDeck().remove(0));
        this.rummy.setCurrentStep(this.rummy.draw);
    }

    @Override
    public void drawFromDiscard() { throw new RummyException("Expected Draw step", 4); }

    @Override
    public void drawFromDeck() {
        throw new RummyException("Expected Draw step", 4);
    }

    @Override
    public void meld(String... cards) {
        throw new RummyException("Expected Meld or Rummy step", 15);
    }

    @Override
    public void addToMeld(int meldIndex, String... cards) { throw new RummyException("Expected Meld or Rummy step", 15); }

    @Override
    public void declareRummy() {
        throw new RummyException("Expected Meld step", 5);
    }

    @Override
    public void finishMeld() { throw new RummyException("Expected Meld or Rummy step", 15); }

    @Override
    public void discard(String card) { throw new RummyException("Expected Discard step", 6); }

    @Override
    public Steps getStepName() {
        return Steps.WAITING;
    }
}
