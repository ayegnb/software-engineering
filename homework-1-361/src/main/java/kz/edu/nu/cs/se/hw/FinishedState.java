package kz.edu.nu.cs.se.hw;

public class FinishedState extends StepsStates {

    private Rummy rummy;

    public FinishedState(Rummy rummy) {
        this.rummy = rummy;
    }

    @Override
    public void rearrange(String card) { throw new RummyException("Expected Waiting step", 3); }

    @Override
    public void shuffle(Long l) {
        throw new RummyException("Expected Waiting step", 3);
    }

    @Override
    public int isFinished() { return this.rummy.getWinner(); }

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
    public void discard(String card) { throw new RummyException("Expected Discard step", 6); }

    @Override
    public Steps getStepName() {
        return Steps.FINISHED;
    }
}
