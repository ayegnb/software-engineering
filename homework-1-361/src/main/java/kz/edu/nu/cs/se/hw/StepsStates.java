package kz.edu.nu.cs.se.hw;

public abstract class StepsStates {

    public abstract void rearrange(String card);

    public abstract void shuffle(Long l);

    public abstract int isFinished();

    public abstract void initialDeal();

    public abstract void drawFromDiscard();

    public abstract void drawFromDeck();

    public abstract void meld(String... cards);

    public abstract void addToMeld(int meldIndex, String... cards);

    public abstract void declareRummy();

    public abstract void finishMeld();

    public abstract void discard(String card);

    public abstract Steps getStepName();

}
