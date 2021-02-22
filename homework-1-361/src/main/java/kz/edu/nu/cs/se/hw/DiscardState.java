package kz.edu.nu.cs.se.hw;

public class DiscardState extends StepsStates {

    private Rummy rummy;

    public DiscardState(Rummy rummy) {
        this.rummy = rummy;
    }

    @Override
    public void rearrange(String card) { throw new RummyException("Expected Waiting step", 3); }

    @Override
    public void shuffle(Long l) {
        throw new RummyException("Expected Waiting step", 3);
    }

    @Override
    public int isFinished() { return -1; }

    @Override
    public void initialDeal() {
        throw new RummyException("Expected Waiting step", 3);
    }

    @Override
    public void drawFromDiscard() { throw new RummyException("Expected Draw step", 4); }

    @Override
    public void drawFromDeck() { throw new RummyException("Expected Draw step", 4); }

    @Override
    public void meld(String... cards) {
        throw new RummyException("Expected Meld or Rummy step", 15);
    }

    @Override
    public void addToMeld(int meldIndex, String... cards) { throw new RummyException("Expected Meld or Rummy step", 15); }

    @Override
    public void declareRummy() { throw new RummyException("Expected Rummy step", 14); }

    @Override
    public void finishMeld() { throw new RummyException("Expected Meld or Rummy step", 15); }

    @Override
    public void discard(String card) {
        if (card == this.rummy.getLastDrawnCard()) {
            throw new RummyException("Not valid discard", 13);
        }
        if (this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer()).size() == 0) {
            throw new RummyException("", 7);
        }
        if (!this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer()).remove(card)) throw new RummyException("", 7);
        this.rummy.getDiscardPile().add(0, card);
        if (this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer()).size() == 0){
            this.rummy.setWinner(this.rummy.getCurrentPlayer());
            this.rummy.setCurrentStep(this.rummy.finished);
        } else {
            this.rummy.setCanDeclareRummy(true);
            if (this.rummy.getCurrentPlayer() == this.rummy.getPlayerHands().size()-1) {
                this.rummy.setCurrentPlayer(0);
            } else {
                this.rummy.setCurrentPlayer(this.rummy.getCurrentPlayer() + 1);
            }
            this.rummy.setCurrentStep(this.rummy.draw);
        }
    }

    @Override
    public Steps getStepName() { return Steps.DISCARD; }
}
