package kz.edu.nu.cs.se.hw;

public class MyIndexable implements Indexable {

    private String word;
    private int lineNum;

    public MyIndexable(String word, int lineNum) {
        this.lineNum = lineNum;
        this.word = word;
    }

    @Override
    public String getEntry() {
        return this.word;
    }

    @Override
    public int getLineNumber() {
        return this.lineNum;
    }

    @Override
    public String toString() {
        return this.word + ": " + this.lineNum;
    }
}
