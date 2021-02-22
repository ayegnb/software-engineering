package kz.edu.nu.cs.se.hw;

import java.util.ArrayList;
import java.util.Arrays;

public class RummyState extends StepsStates {

    private Rummy rummy;

    public RummyState(Rummy rummy) {
        this.rummy = rummy;
    }

    @Override
    public void rearrange(String card) {
        throw new RummyException("Expected Waiting step", 3);
    }

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
        if(this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer()).size() <= 1) {
            this.rummy.setCurrentStep(this.rummy.finished);
        } else {
            if(cards.length < 3) throw new RummyException("Not enough cards", 7);
            if(!meldHelper(cards)) throw new RummyException("Not valid meld", 1);
            ArrayList<String> currentPlayerHand = this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer());
            for (String card : cards){
                if (!currentPlayerHand.remove(card)){
                    throw new RummyException("Not valid meld", 1);
                }
            }

            this.rummy.getMelds().add(new ArrayList<String>(Arrays.asList(cards)));
            if(this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer()).size() <= 1) {
                this.rummy.setWinner(this.rummy.getCurrentPlayer());
                this.rummy.setCurrentStep(this.rummy.finished);
            }
        }
    }

    private boolean meldHelper(String [] cards){
        ArrayList<String> suits = new ArrayList();
        ArrayList<String> ranks = new ArrayList();
        String suit = "";
        String rank = "";
        for(String card : cards){

            suit = card.substring(card.length() - 1);
            rank = card.substring(0,card.length() - 1);

            suits.add(suit);
            ranks.add(rank);
        }

        if(isEqual(ranks)){
            return true;
        }else if(isEqual(suits)){
            return isLegalRun(ranks);
        }else{
            throw new RummyException("Wrong card", 7);
        }
    }

    private boolean isLegalRun(ArrayList<String> cards){
        String check = "A2345678910JQK";
        String meld = "";
        for (String card : cards){
            meld += card;
        }

        return check.contains(meld);
    }

    private boolean isEqual(ArrayList<String> cards){
        for(String card : cards){
            if(!cards.get(0).equals(card)){
                throw new RummyException("wrong card", 7);
            }
        }
        return true;
    }

    @Override
    public void addToMeld(int meldIndex, String... cards) {
        String[] meld = this.rummy.getMeld(meldIndex);
        System.out.println(this.rummy.getMeld(meldIndex));

        String[] newMeld = combineStrings(meld, cards);

        if(!meldHelper(newMeld)){
            throw new RummyException();
        }

        ArrayList <String> currentPlayerHand = this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer());
        for (String card : cards){
            if (!currentPlayerHand.remove(card)){
                throw new RummyException();
            }
        }

        ArrayList<String> meldAsList = this.rummy.getMelds().get(meldIndex);
        meldAsList.clear();
        meldAsList.addAll(Arrays.asList(newMeld));

        if(this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer()).size() == 0){
            this.rummy.setCurrentStep(this.rummy.finished);
        }

    }

    public static String[] combineStrings(String[] a, String[] b){
        int length = a.length + b.length;
        String[] res = new String[length];
        System.arraycopy(a, 0, res, 0, a.length);
        System.arraycopy(b, 0, res, a.length, b.length);
        return res;
    }

    @Override
    public void declareRummy() {

    }

    @Override
    public void finishMeld() {
        this.rummy.setCurrentStep(this.rummy.discard);
        if (this.rummy.getPlayerHands().get(this.rummy.getCurrentPlayer()).size() > 1) {
            throw new RummyException("Rummy not demonstrated", RummyException.RUMMY_NOT_DEMONSTRATED);
        }
    }

    @Override
    public void discard(String card) { throw new RummyException("Expected Discard step", 6); }

    @Override
    public Steps getStepName() { return Steps.RUMMY; }
}
