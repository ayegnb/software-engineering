package kz.edu.nu.cs.se.hw;

public class DrawState extends StepsStates {

    private Rummy rummy;

    public DrawState(Rummy rummy) {
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
    public void drawFromDiscard() {
        String drawnCard = this.rummy.getDiscardPile().remove(0);
        this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer()).add(drawnCard);
        this.rummy.setLastDrawnCard(drawnCard);
        this.rummy.setCurrentStep(this.rummy.meld);
    }

    @Override
    public void drawFromDeck() {
        if (this.rummy.getNumCardsInDeck() == 0) {
            String topCard = this.rummy.getDiscardPile().remove(0);
            while(this.rummy.getDiscardPile().size() != 0) {
                this.rummy.getDeck().add(this.rummy.getDiscardPile().remove(0));
            }
            this.rummy.getDiscardPile().add(topCard);
        }
        this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer()).add(this.rummy.getDeck().remove(0));
        this.rummy.setCurrentStep(this.rummy.meld);
    }

    @Override
    public void meld(String... cards) { throw new RummyException("Expected Meld or Rummy step", 15); }

    @Override
    public void addToMeld(int meldIndex, String... cards) { throw new RummyException("Expected Meld or Rummy step", 15); }

    @Override
    public void declareRummy() { throw new RummyException("Expected Rummy step", 14); }

    @Override
    public void finishMeld() { throw new RummyException("Expected Meld or Rummy step", 15); }

    @Override
    public void discard(String card) { throw new RummyException("Expected Discard step", 6); }

    @Override
    public Steps getStepName() { return Steps.DRAW; }
}
